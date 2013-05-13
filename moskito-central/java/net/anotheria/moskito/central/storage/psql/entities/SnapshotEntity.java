package net.anotheria.moskito.central.storage.psql.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Snapshot entity class for string data to DB.
 * 
 * @author dagafonov
 * 
 */
@Entity
@Table(name = "snapshots")
public class SnapshotEntity implements Serializable {

	/**
	 * Generated serialVersionUID.
	 */
	private static final long serialVersionUID = 2299490896666103726L;

	/**
	 * Id field.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long snapshotId;

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

	/**
	 * Statistics map.
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Map<String, StatisticsEntity> statistics = new HashMap<String, StatisticsEntity>();
	
	public void setStatistics(Map<String, StatisticsEntity> statistics) {
		this.statistics = statistics;
	}

	public Map<String, StatisticsEntity> getStatistics() {
		return statistics;
	}

	public void addStatistics(String key, StatisticsEntity value) {
		getStatistics().put(key, value);
	}

	public long getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(long snapshotId) {
		this.snapshotId = snapshotId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (snapshotId ^ (snapshotId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnapshotEntity other = (SnapshotEntity) obj;
		if (snapshotId != other.snapshotId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SnapshotEntity [snapshotId=" + snapshotId + ", producerId=" + producerId + ", componentName=" + componentName + ", hostName="
				+ hostName + ", intervalName=" + intervalName + ", creationTimestamp=" + creationTimestamp + ", arrivalTimestamp=" + arrivalTimestamp
				+ ", category=" + category + ", subsystem=" + subsystem + ", statistics=" + statistics + "]";
	}

}
