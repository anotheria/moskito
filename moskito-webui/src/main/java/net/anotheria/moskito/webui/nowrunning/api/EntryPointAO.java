package net.anotheria.moskito.webui.nowrunning.api;

import java.io.Serializable;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:23
 */
public class EntryPointAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String producerId;

	private long currentRequestCount;

	private long totalRequestCount;

	private List<MeasurementAO> currentMeasurements;

	private List<MeasurementAO> pastMeasurements;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public long getCurrentRequestCount() {
		return currentRequestCount;
	}

	public void setCurrentRequestCount(long currentRequestCount) {
		this.currentRequestCount = currentRequestCount;
	}

	public long getTotalRequestCount() {
		return totalRequestCount;
	}

	public void setTotalRequestCount(long totalRequestCount) {
		this.totalRequestCount = totalRequestCount;
	}

	@Override
	public String toString() {
		return "EntryPointAO{" +
				"producerId='" + producerId + '\'' +
				", currentRequestCount=" + currentRequestCount +
				", totalRequestCount=" + totalRequestCount +
				", CM: "+currentMeasurements+", " +
				", PM: "+pastMeasurements+
				'}';
	}

	public boolean isCurrentlyRunning(){
		return currentMeasurements!=null && currentMeasurements.size()>0;
	}

	public List<MeasurementAO> getCurrentMeasurements() {
		return currentMeasurements;
	}

	public void setCurrentMeasurements(List<MeasurementAO> currentMeasurements) {
		this.currentMeasurements = currentMeasurements;
	}

	public List<MeasurementAO> getPastMeasurements() {
		return pastMeasurements;
	}

	public void setPastMeasurements(List<MeasurementAO> pastMeasurements) {
		this.pastMeasurements = pastMeasurements;
	}
}
