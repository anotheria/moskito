package net.anotheria.moskito.central;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.03.13 14:07
 */
public class SnapshotMetaData implements Serializable {
	/**
	 * Id of the producer.
	 */
	private String producerId;

	/**
	 * Name of the component.
	 */
	private String componentName;

	/**
	 * Hostname.
	 */
	private String hostName;

	/**
	 * Intervalname.
	 */
	private String intervalName;

	/**
	 * Timestamp when the snapshot was created.
	 */
	private long creationTimestamp;

	/**
	 * Timestamp when the snapshot arrived in central.
	 */
	private long arrivalTimestamp;

	/**
	 * Category of the producer.
	 */
	private String category;
	/**
	 * Subsystem of the producer.
	 */
	private String subsystem;

	public SnapshotMetaData(){
		arrivalTimestamp = System.currentTimeMillis();
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public long getArrivalTimestamp() {
		return arrivalTimestamp;
	}

	public void setArrivalTimestamp(long arrivalTimestamp) {
		this.arrivalTimestamp = arrivalTimestamp;
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

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
}
