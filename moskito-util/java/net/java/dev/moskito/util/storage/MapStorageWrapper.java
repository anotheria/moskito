package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This wrapper class is used to make an underlying map-like storage monitorable.
 * @author lrosenberg
 *
 * @param <K> map key.
 * @param <V> map value.
 */
public class MapStorageWrapper<K,V> implements StorageWrapper<K, V>{

	/**
	 * Underlying map.
	 */
	private Map<K, V> map;
	/**
	 * Creates a new map storage wrapper with a given underlying wrapper.
	 * @param aMap
	 */
	public MapStorageWrapper(Map<K,V> aMap){
		map = aMap;
	}

	@Override public void putAll(Map<? extends K, ? extends V> aMap) {
		map.putAll(aMap);
	}

	@Override public void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper) {
		if (anotherWrapper instanceof MapStorageWrapper<?,?>)
			map.putAll(((MapStorageWrapper<? extends K, ? extends V>)anotherWrapper).getMap());
		else
			throw new RuntimeException("Unsupported operation putAll on "+anotherWrapper+", class: "+anotherWrapper.getClass());
	}

	@Override public void clear() {
		map.clear();
	}

	@Override public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override public V get(Object key) {
		return map.get(key);
	}

	@Override public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override public Collection<K> keys() {
		return map.keySet();
	}

	@Override public Set<K> keySet() {
		return map.keySet();
	}

	@Override public V put(K key, V value) {
		return map.put(key, value);
	}

	@Override public V remove(Object key) {
		return map.remove(key);
	}

	@Override public int size() {
		return map.size();
	}

	@Override public Collection<V> values() {
		return map.values();
	}
	
	private Map<K,V> getMap(){
		return map;
	}
	
	@Override public String toString(){
		return "wrapped: "+getMap().toString();
	}
	
	@Override public Map<K,V> toMap(){
		return fillMap(new HashMap<K, V>(size()));
	}

	@Override public Map<K,V> fillMap(Map<K,V> toFill){
		toFill.putAll(map);
		return toFill;
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

}
