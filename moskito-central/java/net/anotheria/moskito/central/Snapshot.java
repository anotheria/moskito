package net.anotheria.moskito.central;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Represents a single snapshot.
 * 
 * @author lrosenberg
 * @since 15.03.13 23:15
 */
@XmlRootElement(name = "snsh")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ HashMap.class })
public class Snapshot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1452811059878773956L;

	/**
	 * The metadata. The metadata contains data about the snapshot like
	 * producerId or timestamp.
	 */
	@XmlElement(name = "snshmd")
	private SnapshotMetaData metaData;

	/**
	 * Stat values.
	 */
	@XmlJavaTypeAdapter(MapAdapter2.class)
	private HashMap<String, HashMap<String, String>> stats = new HashMap<String, HashMap<String, String>>();

	public Snapshot() {

	}

	public SnapshotMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(SnapshotMetaData metaData) {
		this.metaData = metaData;
	}

	public void addSnapshotData(String name, HashMap<String, String> values) {
		stats.put(name, values);
	}

	@Override
	public String toString() {
		return "Snapshot [metaData=" + metaData + ", stats=" + stats + "]";
	}

	public Map<String, String> getStatistics(String stat) {
		return stats.get(stat);
	}

	public Set<Entry<String, HashMap<String, String>>> getEntrySet() {
		return stats.entrySet();
	}

	public Set<String> getKeySet() {
		return stats.keySet();
	}

	public HashMap<String, HashMap<String, String>> getStats() {
		if (stats == null) {
			stats = new HashMap<String, HashMap<String, String>>();
		}
		return stats;
	}

	public void setStats(HashMap<String, HashMap<String, String>> stats) {
		this.stats = stats;
	}

	public static class MapAdapter1 extends HashMapAdapter<String, String> {

	}

	public static class MapAdapter2 extends HashMapAdapter<String, HashMap<String, String>> {

	}

}
