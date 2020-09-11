package net.anotheria.moskito.webui.nowrunning.api;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:23
 */
public class EntryPointAO {
	private String producerId;

	private long currentRequestCount;

	private long totalRequestCount;

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
				'}';
	}
}
