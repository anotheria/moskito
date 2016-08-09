package net.anotheria.moskito.core.threshold;

import java.util.LinkedList;
import java.util.List;

/**
 * The extended ThresholdStatus returns information about all producers in one status.
 *
 * @author lrosenberg
 * @since 14.06.13 10:53
 */
public class ExtendedThresholdStatus {
	/**
	 * Status of the producers.
	 */
	private ThresholdStatus status;

	/**
	 * Thresholds in the status.
	 */
	private List<ThresholdInStatus> thresholds = new LinkedList<>();

	public ExtendedThresholdStatus(ThresholdStatus aStatus){
		status = aStatus;
	}

	public ThresholdStatus getStatus() {
		return status;
	}

	public void setStatus(ThresholdStatus status) {
		this.status = status;
	}

	public List<ThresholdInStatus> getThresholds() {
		return thresholds;
	}

	public void setProducers(List<ThresholdInStatus> thresholds) {
		this.thresholds = thresholds;
	}

	@Override public String toString(){
        return status +": "+ thresholds;
	}

	public void add(ThresholdInStatus tis) {
		thresholds.add(tis);
	}
}
