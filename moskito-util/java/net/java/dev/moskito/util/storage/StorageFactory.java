package net.java.dev.moskito.util.storage;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;


public class StorageFactory<K,V> {
	
	public Storage<K,V> createHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	public Storage<K,V> createHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	public Storage<K,V> createHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>()));
	}

	public Storage<K,V> createHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new HashMap<K, V>(initialSize)));
	}

	public Storage<K,V> createConcurrentHashMapStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	public Storage<K,V> createConcurrentHashMapStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	public Storage<K,V> createConcurrentHashMapStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>()));
	}

	public Storage<K,V> createConcurrentHashMapStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new ConcurrentHashMap<K, V>(initialSize)));
	}

	public Storage<K,V> createHashtableStorage(){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new Hashtable<K, V>()));
	}

	public Storage<K,V> createHashtableStorage(int initialSize){
		return new Storage<K,V>(new MapStorageWrapper<K, V>(new Hashtable<K, V>(initialSize)));
	}

	public Storage<K,V> createHashtableStorage(String name){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new Hashtable<K, V>()));
	}

	public Storage<K,V> createHashtableStorage(String name, int initialSize){
		return new Storage<K,V>(name, new MapStorageWrapper<K, V>(new Hashtable<K, V>(initialSize)));
	}
}
