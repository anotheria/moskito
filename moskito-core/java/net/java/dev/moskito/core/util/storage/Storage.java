package net.java.dev.moskito.core.util.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.inspection.Inspectable;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
/**
 * This class represents a monitorable, map-like storage.
 * @author lrosenberg
 *
 * @param <K>
 * @param <V>
 */
public class Storage<K,V> implements IStatsProducer, Inspectable, Map<K,V>{
	/**
	 * Default category used for producer registration.
	 */
	public static final String DEF_CATEGORY = "storage";
	/**
	 * Default subsystem used for producer registration.
	 */
	public static final String DEF_SUBSYSTEM = "default";
	/**
	 * The underlying wrapper.
	 */
	private StorageWrapper<K, V> wrapper;
	/**
	 * Instance counter for better distinction in webui. It also helps to find out the order storages are instantiated.
	 */
	private static AtomicInteger instanceCount = new AtomicInteger(0);
	/**
	 * Name of the storage for webui.
	 */
	private String name;
	/**
	 * Category for webui.
	 */
	private String category;
	/**
	 * Subsystem for webui.
	 */
	private String subsystem;
	/**
	 * Stats object.
	 */
	private StorageStats stats;
	/**
	 * Stats list for getStats method.
	 */
	private List<IStats> statsList;
	/**
	 * Info about storage creation.
	 */
	private CreationInfo creationInfo;
	/**
	 * Creates a new anonymous storage with given wrapper.
	 * @param aWrapper
	 */
	public Storage(StorageWrapper<K, V> aWrapper){
		this("storage", aWrapper);
	}
	/**
	 * Creates a new storage with given wrapper.
	 * @param aName storage name
	 * @param aWrapper wrapper name
	 */
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

	@Override public V get(Object key){
		V v = wrapper.get(key);
		stats.addGet();
		if (v == null)
			stats.addMissedGet();
		return v;
	}
	
	@Override public V put(K key, V value){
		V v = wrapper.put(key, value);
		stats.addPut();
		if (v == null)
			stats.increaseSize();
		else
			stats.addOverwritePut();
		return v;
	}
	
	@Override public int size(){
		int size = wrapper.size();
		//use this call to update the stats-size value.
		stats.setSize(size);
		return size;
	}
	
	@Override public boolean isEmpty(){
		return wrapper.isEmpty();
	}
	
	@Override public boolean containsKey(Object key){
		boolean ret = wrapper.containsKey(key);
		if (ret)
			stats.addContainsKeyHit();
		else
			stats.addContainsKeyMiss();
		return ret;
	}
	
	@Override public boolean containsValue(Object value){
		boolean ret = wrapper.containsValue(value);
		if (ret)
			stats.addContainsValueHit();
		else
			stats.addContainsValueMiss();
		return ret;
	}
	
	@Override public V remove(Object key){
		V v = wrapper.remove(key);
		stats.addRemove();
		if (v==null)
			stats.addNoopRemove();
		else
			stats.decreaseSize();
		return v;
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return wrapper.entrySet();
	}

	public void putAll(Storage<? extends K, ? extends V> anotherStorage){
		wrapper.putAll(anotherStorage.getWrapper());
		stats.setSize(wrapper.size());
	}
	
	@Override public Set<K> keySet(){
		return wrapper.keySet();
	}
	
	/**
	 * Convinience method for old hashtable users.
	 * @return
	 */
	public Collection<K> keys(){
		return wrapper.keys();
	}
	
	@Override public Collection<V> values(){
		return wrapper.values();
	}
	
	@Override public void clear(){
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

	@Override public void putAll(Map<? extends K, ? extends V> anotherMap){
		wrapper.putAll(anotherMap);
		stats.setSize(wrapper.size());
	}

	////////////// IStatsProducer Method ////////////
	@Override public String getCategory() {
		return category;
	}

	@Override public String getProducerId() {
		return getName();
	}

	@Override public List<IStats> getStats() {
		return statsList;
	}

	@Override public String getSubsystem() {
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

	@Override public CreationInfo getCreationInfo(){
		return creationInfo;
	}
	
	////// static factory methods
	public static <K,V>Storage<K,V> createHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	public static <K,V>Storage<K,V> createHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	public static <K,V>Storage<K,V> createHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	public static <K,V>Storage<K,V> createHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	public static <K,V>Storage<K,V> createConcurrentHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	public static <K,V>Storage<K,V> createConcurrentHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	public static <K,V>Storage<K,V> createConcurrentHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	public static <K,V>Storage<K,V> createConcurrentHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	public static <K,V>Storage<K,V> createHashtableStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new Hashtable<K, V>()));
	}

	public static <K,V>Storage<K,V> createHashtableStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new Hashtable<K, V>(initialSize)));
	}

	public static <K,V>Storage<K,V> createHashtableStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new Hashtable<K, V>()));
	}

	public static <K,V>Storage<K,V> createHashtableStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new Hashtable<K, V>(initialSize)));
	}
	
	
}
