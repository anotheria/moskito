package net.java.dev.moskito.util.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.inspection.Inspectable;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class Storage<K,V> implements IStatsProducer, Inspectable{
	
	public static final String DEF_CATEGORY = "storage";
	public static final String DEF_SUBSYSTEM = "default";
	
	private StorageWrapper<K, V> wrapper;
	private static AtomicInteger instanceCount = new AtomicInteger(0);
	
	private String name;
	
	private String category;
	private String subsystem;
	
	private StorageStats stats;
	private List<IStats> statsList;
	
	private CreationInfo creationInfo;
	
	public Storage(StorageWrapper<K, V> aWrapper){
		this("storage", aWrapper);
	}
	
	public Storage(String aName, StorageWrapper<K, V> aWrapper){
		name = aName + "-"+ instanceCount.incrementAndGet();
		wrapper = aWrapper;
		stats = new StorageStats(name);
		
		statsList = new ArrayList<IStats>(1);
		statsList.add(stats);
		
		category = DEF_CATEGORY;
		subsystem = DEF_SUBSYSTEM;
		
		IProducerRegistry reg = ProducerRegistryFactory.getProducerRegistryInstance();
		reg.registerProducer(this);
		
		RuntimeException e = new RuntimeException();
		e.fillInStackTrace();
		creationInfo = new CreationInfo(e.getStackTrace());

	}

	public V get(K key){
		V v = wrapper.get(key);
		stats.addGet();
		if (v == null)
			stats.addMissedGet();
		return v;
	}
	
	public V put(K key, V value){
		V v = wrapper.put(key, value);
		stats.addPut();
		if (v == null)
			stats.increaseSize();
		else
			stats.addOverwritePut();
		return v;
	}
	
	public int size(){
		int size = wrapper.size();
		//use this call to update the stats-size value.
		stats.setSize(size);
		return size;
	}
	
	public boolean isEmpty(){
		return wrapper.isEmpty();
	}
	
	public boolean containsKey(K key){
		boolean ret = wrapper.containsKey(key);
		if (ret)
			stats.addContainsKeyHit();
		else
			stats.addContainsKeyMiss();
		return ret;
	}
	
	public boolean containsValue(V value){
		boolean ret = wrapper.containsValue(value);
		if (ret)
			stats.addContainsValueHit();
		else
			stats.addContainsValueMiss();
		return ret;
	}
	
	public V remove(K key){
		V v = wrapper.remove(key);
		stats.addRemove();
		if (v==null)
			stats.addNoopRemove();
		else
			stats.decreaseSize();
		return v;
	}
	
	public void putAll(Storage<? extends K, ? extends V> anotherStorage){
		wrapper.putAll(anotherStorage.getWrapper());
		stats.setSize(wrapper.size());
	}
	
	public Set<K> keySet(){
		return wrapper.keySet();
	}
	
	public Collection<K> keys(){
		return wrapper.keys();
	}
	
	public Collection<V> values(){
		return wrapper.values();
	}
	
	public void clear(){
		wrapper.clear();
	}
	
	private StorageWrapper<K, V> getWrapper(){
		return wrapper;
	}
	
	public Map<K,V> toMap(){
		return wrapper.toMap();
	}
	
	public Map<K,V> fillMap(Map<K,V> toFill){
		return wrapper.fillMap(toFill);
	}

	public void putAll(Map<? extends K, ? extends V> anotherMap){
		wrapper.putAll(anotherMap);
		stats.setSize(wrapper.size());
	}

	////////////// IStatsProducer Method ////////////
	public String getCategory() {
		return category;
	}

	public String getProducerId() {
		return getName();
	}

	public List<IStats> getStats() {
		return statsList;
	}

	public String getSubsystem() {
		return subsystem;
	}
	
	//////////////////////// OTHER
	public void setSubsystem(String aSubsystem) {
		subsystem = aSubsystem;
	}
	
	public void setCategory(String aCategory) {
		category = aCategory;
	}

	public String getName(){
		return name;
	}

	public CreationInfo getCreationInfo(){
		return creationInfo;
	}
}
