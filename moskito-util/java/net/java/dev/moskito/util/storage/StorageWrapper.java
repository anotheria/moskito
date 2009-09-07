package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface StorageWrapper<K,V> {
	V get(K key);
	
	V put(K key, V value);
	
	int size();
	
	boolean isEmpty();
	
	boolean containsKey(K key);
	
	boolean containsValue(V value);
	
	V remove(K key);
	
	void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper);
	
	void putAll(Map<? extends K, ? extends V> aMap);

	Set<K> keySet();
	
	Collection<K> keys();
	
	Collection<V> values();
	
	void clear();
	
	Map<K,V> toMap();
	
	Map<K,V> fillMap(Map<K,V> toFill);
}
