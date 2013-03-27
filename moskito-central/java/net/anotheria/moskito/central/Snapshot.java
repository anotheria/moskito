package net.anotheria.moskito.central;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single snapshot.
 *
 * @author lrosenberg
 * @since 15.03.13 23:15
 */
public class Snapshot implements Serializable {
	/**
	 * The metadata. The metadata contains data about the snapshot like producerId or timestamp.
	 */
	private SnapshotMetaData metaData;

	/**
	 * Stat values.
	 */
	private Map<String, Map<String,String>> stats = new HashMap<String, Map<String, String>>();

	public Snapshot(){
	}

	public SnapshotMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(SnapshotMetaData metaData) {
		this.metaData = metaData;
	}

	public void addSnapshotData(String name, Map<String,String> values ){
		stats.put(name, values);
	}

	@Override public String toString(){
		return "{ MD: "+metaData.toString()+", Stats: "+stats+"}";
	}

	public Map<String, Map<String, String>> getStats() {
		return stats;
	}

	public void setStats(Map<String, Map<String, String>> stats) {
		this.stats = stats;
	}


}
