package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapStorageWrapper<K,V> implements StorageWrapper<K, V>{
	private Map<K, V> map;
	
	public MapStorageWrapper(Map<K,V> aMap){
		map = aMap;
	}

	public void putAll(Map<? extends K, ? extends V> aMap) {
		map.putAll(aMap);
	}

	public void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper) {
		if (anotherWrapper instanceof MapStorageWrapper<?,?>)
			map.putAll(((MapStorageWrapper<? extends K, ? extends V>)anotherWrapper).getMap());
		else
			throw new RuntimeException("Unsupported operation putAll on "+anotherWrapper+", class: "+anotherWrapper.getClass());
	}

	public void clear() {
		map.clear();
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public boolean containsValue(V value) {
		return map.containsValue(value);
	}

	public V get(K key) {
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Collection<K> keys() {
		return map.keySet();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put(K key, V value) {
		return map.put(key, value);
	}

	public V remove(K key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		return map.values();
	}
	
	private Map<K,V> getMap(){
		return map;
	}
	
	public String toString(){
		return "wrapped: "+getMap().toString();
	}
	
	public Map<K,V> toMap(){
		return fillMap(new HashMap<K, V>(size()));
	}

	public Map<K,V> fillMap(Map<K,V> toFill){
		toFill.putAll(map);
		return toFill;
	}
}
