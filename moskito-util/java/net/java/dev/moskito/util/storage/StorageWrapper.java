package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.Map;

public interface StorageWrapper<K,V> extends Map<K,V>{
/*	V get(K key);
	
	V put(K key, V value);
	
	int size();
	
	boolean isEmpty();
	
	boolean containsKey(K key);
	
	boolean containsValue(V value);
	
	V remove(K key);
	
	void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper);
	
	void putAll(Map<? extends K, ? extends V> aMap);

	Set<K> keySet();
	
	
	Collection<V> values();
	
	void clear();
*/
	void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper);

	Collection<K> keys();

	Map<K,V> toMap();
	
	Map<K,V> fillMap(Map<K,V> toFill);

}
