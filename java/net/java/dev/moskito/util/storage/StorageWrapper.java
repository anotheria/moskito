package net.java.dev.moskito.util.storage;

import java.util.Collection;
import java.util.Map;
/**
 * This interface contains some additional convenience methods in addition to the map interface.
 * @author another
 *
 * @param <K> the type of keys maintained by this storage wrapper.
 * @param <V> the type of mapped values.
 */
public interface StorageWrapper<K,V> extends Map<K,V>{

	/**
	 * Puts all data from another wrapper into this wrapper.
	 * @param anotherWrapper
	 */
	void putAll(StorageWrapper<? extends K, ? extends V> anotherWrapper);
	/**
	 * Returns the collection with all keys.
	 * @return
	 */
	Collection<K> keys();
	/**
	 * Creates a new map and fill it with the content of this map.
	 * @return
	 */
	Map<K,V> toMap();
	/**
	 * Fills the parameter map with contents of this map and returns it.
	 * @param toFill
	 * @return
	 */
	Map<K,V> fillMap(Map<K,V> toFill);

}
