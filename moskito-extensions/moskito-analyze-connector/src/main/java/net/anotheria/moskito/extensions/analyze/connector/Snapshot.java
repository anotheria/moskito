package net.anotheria.moskito.extensions.analyze.connector;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single snapshot.
 *
 * @author esmakula
 */
public class Snapshot implements Serializable {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = -3803823780824976489L;

	/**
	 * The metadata. The metadata contains data about the snapshot like
	 * producerId or timestamp.
	 */
	private SnapshotMetaData metaData;

	/**
	 * Stat values.
	 */
	private Map<String, Map<String, String>> stats = new HashMap<>();

	/**
	 * Default constructor.
	 */
	public Snapshot() {

	}

	public SnapshotMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(SnapshotMetaData metaData) {
		this.metaData = metaData;
	}

	/**
	 * Adds snapshot data to the stats map.
	 *
	 * @param name stat name
	 * @param values stat values
	 */
	public void addSnapshotData(String name, Map<String, String> values) {
		stats.put(name, values);
	}

	@Override
	public String toString() {
		return "Snapshot [metaData=" + metaData + ", stats=" + stats + "]";
	}

	/**
	 * Gets all statistics.
	 *
	 * @return {@link Map}
	 */
	public Map<String, Map<String, String>> getStats() {
		if (stats == null) {
			stats = new HashMap<>();
		}
		return stats;
	}

	public void setStats(Map<String, Map<String, String>> stats) {
		this.stats = stats;
	}

}
