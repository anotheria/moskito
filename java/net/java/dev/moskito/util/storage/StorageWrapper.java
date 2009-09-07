package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface StorageWrapper<K,V> {
	public V get(K key);
	
	public V put(K key, V value);
	
	public int size();
	
	public boolean isEmpty();
	
	public boolean containsKey(K key);
	
	public boolean containsValue(V value);
	
	public V remove(K key);
	
	public void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper);
	
	public void putAll(Map<? extends K, ? extends V> aMap);

	public Set<K> keySet();
	
	public Collection<K> keys();
	
	public Collection<V> values();
	
	public void clear();
	
	public Map<K,V> toMap();
	
	public Map<K,V> fillMap(Map<K,V> toFill);
}
