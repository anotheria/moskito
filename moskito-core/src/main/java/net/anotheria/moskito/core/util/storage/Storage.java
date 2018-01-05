package net.anotheria.moskito.core.util.storage;

import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.core.inspection.Inspectable;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents a monitorable, map-like storage.
 * @author lrosenberg
 *
 * @param <K>
 * @param <V>
 */
public class Storage<K,V> implements IStatsProducer<StorageStats>, Inspectable, Map<K,V> {
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
	private List<StorageStats> statsList;
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
		name = aName + '-' + instanceCount.incrementAndGet();
		wrapper = aWrapper;
		stats = new StorageStats(name);
		
		statsList = new ArrayList<StorageStats>(1);
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

	/**
	 * Puts all elements from anotherStorage to this storage.
	 * @param anotherStorage
	 */
	public void putAll(Storage<? extends K, ? extends V> anotherStorage){
		wrapper.putAll(anotherStorage.getWrapper());
		stats.setSize(wrapper.size());
	}
	
	@Override public Set<K> keySet(){
		return wrapper.keySet();
	}
	
	/**
	 * Convenience method for old hashtable users.
	 * @return the collection of all keys.
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

	/**
	 * Returns the wrapper.
	 * @return
	 */
	private StorageWrapper<K, V> getWrapper(){
		return wrapper;
	}

	/**
	 * Creates a map copy of this storage with all contained elements.
	 * @return a map containing lal elements from the storage.
	 */
	public Map<K,V> toMap(){
		return wrapper.toMap();
	}

	/**
	 * Puts all elements into a given map.
	 * @param toFill map to fill to.
	 * @return the map.
	 */
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

	@Override public List<StorageStats> getStats() {
		return statsList;
	}

	@Override public String getSubsystem() {
		return subsystem;
	}
	
	//////////////////////// OTHER

	/**
	 * Sets the subsystem of this producer.
	 * @param aSubsystem subsystem to set.
	 */
	public void setSubsystem(String aSubsystem) {
		subsystem = aSubsystem;
	}

	/**
	 * Sets the category of this producer.
	 * @param aCategory category to set.
	 */
	public void setCategory(String aCategory) {
		category = aCategory;
	}

	/**
	 * Returns this storage's name.
	 * @return
	 */
	public String getName(){
		return name;
	}

	@Override public CreationInfo getCreationInfo(){
		return creationInfo;
	}

	/**
	 * Unregister storage from producer registry.
	 */
	public void unregister() {
		ProducerRegistryFactory.getProducerRegistryInstance().unregisterProducer(this);
	}

	
	////// static factory methods

	/**
	 * Factory method to create a new storage backed by a hashmap.
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a hashmap.
	 * @param initialSize
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	/**
	 * Factory method to create a new storage backed by a hashmap.
	 * @param name
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a concurrent hash map.
	 * @param name
	 * @param initialSize
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	/**
	 * Factory method to create a new storage backed by a concurrent hash map.
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createConcurrentHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a concurrent hash map.
 	 * @param initialSize initial size of the storage.
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createConcurrentHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	/**
	 * Factory method to create a new storage backed by a concurrent hash map.
	 * @param name
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createConcurrentHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a concurrent hash map.
	 * @param name
	 * @param initialSize
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createConcurrentHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	/**
	 * Factory method to create a new storage backed by a hashtable.
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashtableStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a hashtable.
	 * @param initialSize
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashtableStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	/**
	 * Factory method to create a new storage backed by a hashtable.
	 * @param name
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashtableStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	/**
	 * Factory method to create a new storage backed by a hashtable.
	 * @param name
	 * @param initialSize
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createHashtableStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	/**
	 * Creates a new TreeMap backed Storage.
	 * @param name name of the map
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createTreeMapStorage(String name){
		return new Storage<K, V>(name, new MapStorageWrapper<K, V>(new TreeMap<K, V>()));
	}

	/**
	 * Creates a new TreeMap backed Storage.
	 * @param name name
	 * @param comparator comparator
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K,V> Storage<K,V> createTreeMapStorage(String name, Comparator comparator){
		return new Storage<K, V>(name, new MapStorageWrapper<K, V>(new TreeMap<K, V>(comparator)));
	}

}
