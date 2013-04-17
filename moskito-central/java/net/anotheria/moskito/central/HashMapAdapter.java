package net.anotheria.moskito.central;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.anotheria.moskito.central.HashMapAdapter.MyMapType;

/**
 * 
 * @author dagafonov
 * 
 * @param <K>
 * @param <V>
 */
public class HashMapAdapter<K, V> extends XmlAdapter<MyMapType<K, V>, HashMap<K, V>> {

	/**
	 * Default construcotr.
	 */
	public HashMapAdapter() {

	}

	@Override
	public MyMapType<K, V> marshal(HashMap<K, V> arg0) {
		MyMapType<K, V> myMapType = new MyMapType<K, V>();
		for (Entry<K, V> entry : arg0.entrySet()) {
			MyMapEntryType<K, V> myMapEntryType = new MyMapEntryType<K, V>();
			myMapEntryType.key = entry.getKey();
			myMapEntryType.value = entry.getValue();
			myMapType.entries.add(myMapEntryType);
		}
		return myMapType;
	}

	@Override
	public HashMap<K, V> unmarshal(MyMapType<K, V> arg0) {
		HashMap<K, V> hashMap = new HashMap<K, V>();
		for (MyMapEntryType<K, V> myEntryType : arg0.entries) {
			hashMap.put(myEntryType.key, myEntryType.value);
		}
		return hashMap;
	}

	/**
	 * Wrapper for all entries that stored in hash map.
	 * 
	 * @author dagafonov
	 * 
	 * @param <K>
	 * @param <V>
	 */
	@XmlRootElement
	public static class MyMapType<K, V> {

		/**
		 * All keys and values will be transformed into {@link MyMapEntryType}.
		 */
		private List<MyMapEntryType<K, V>> entries = new ArrayList<MyMapEntryType<K, V>>();

		public List<MyMapEntryType<K, V>> getEntries() {
			return entries;
		}

		public void setEntries(List<MyMapEntryType<K, V>> entries) {
			this.entries = entries;
		}

		@Override
		public String toString() {
			return "MyMapType [entries=" + entries + "]";
		}

	}

	/**
	 * Wrapper for key - value pair.
	 * 
	 * @author dagafonov
	 * 
	 * @param <K>
	 * @param <V>
	 */
	@XmlRootElement
	public static class MyMapEntryType<K, V> {

		/**
		 * Entry key.
		 */
		private K key;

		/**
		 * Entry value.
		 */
		private V value;

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "MyMapEntryType [key=" + key + ", value=" + value + "]";
		}

	}

}