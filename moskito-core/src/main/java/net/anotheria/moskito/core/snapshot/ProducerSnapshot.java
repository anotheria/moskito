package net.anotheria.moskito.core.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * A snapshot of producer's data after an interval passed.
 *
 * @author lrosenberg
 * @since 30.09.12 15:23
 */
public class ProducerSnapshot {
	/**
	 * Associated producer id.
	 */
	private String producerId;
	/**
	 * Category of the associated producer.
	 */
	private String category;
	/**
	 * Subsystem of the associated producer.
	 */
	private String subsystem;
	/**
	 * Name of the interval.
	 */
	private String intervalName;

	/**
	 * Name of the class of the used stat.
	 */
	private String statClassName;

	private long timestamp = System.currentTimeMillis();

	private Map<String, StatSnapshot> stats = new HashMap<>();

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void addSnapshot(StatSnapshot snapshot){
		stats.put(snapshot.getName(), snapshot);
	}

	public Map<String, StatSnapshot> getStatSnapshots() {
		return stats;
	}

	public String getIntervalName() {

		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	@Override public String toString(){
		return "Snapshot Producer: (pId:"+getProducerId()+", Cat:"+getCategory()+", Sub:"+getSubsystem()+") Int: "+getIntervalName()+"Class: "+statClassName+", Stats: "+getStatSnapshots();
	}

	public StatSnapshot getStatSnapshot(String statName) {
		return stats.get(statName);
	}
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatClassName() {
		return statClassName;
	}

	public void setStatClassName(String statClassName) {
		this.statClassName = statClassName;
	}



}
