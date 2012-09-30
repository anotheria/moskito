package net.anotheria.moskito.core.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 30.09.12 15:23
 */
public class ProducerSnapshot {
	private String producerId;
	private String category;
	private String subsystem;
	private String intervalName;

	private String component;
	private String host;

	private Map<String, StatSnapshot> stats = new HashMap<String, StatSnapshot>();

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
		return "Snapshot Producer: (pId:"+getProducerId()+", Cat:"+getCategory()+", Sub:"+getSubsystem()+") Int: "+getIntervalName()+", Loc: "+getComponent()+"@"+getHost()+", Stats: "+getStatSnapshots();
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public StatSnapshot getStatSnapshot(String statName) {
		return stats.get(statName);
	}
}
