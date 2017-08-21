package net.anotheria.moskito.extensions.analyze.connector;

import java.io.Serializable;

/**
 * Contains meta data about the snapshot like producerId, creation timestamp and
 * so on.
 *
 * @author esmakula
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
	 * Host name.
	 */
	private String hostName;

	/**
	 * Interval name.
	 */
	private String intervalName;

	/**
	 * Timestamp when the snapshot was created.
	 */
	private long creationTimestamp;

	/**
	 * Timestamp when the snapshot arrived.
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

	/**
	 *
	 */
	private String statClassName;

	/**
	 * Default constructor.
	 */
	public SnapshotMetaData() {
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

    public String getStatClassName() {
        return statClassName;
    }

    public void setStatClassName(String statClassName) {
        this.statClassName = statClassName;
    }

    @Override
	public String toString() {
		return "SnapshotMetaData [producerId=" + producerId + ", componentName=" + componentName + ", hostName=" + hostName + ", intervalName="
				+ intervalName + ", creationTimestamp=" + creationTimestamp + ", arrivalTimestamp=" + arrivalTimestamp + ", category=" + category
				+ ", subsystem=" + subsystem + ", statClassName=" + statClassName + "]";
	}
}
