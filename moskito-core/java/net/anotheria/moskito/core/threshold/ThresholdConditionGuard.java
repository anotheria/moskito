package net.anotheria.moskito.core.threshold;

/**
 * A ThresholdConditionGuard
 */
public interface ThresholdConditionGuard {
	/**
	 * Generate new status on threshold value change
	 * @param previousValue value before change.
	 * @param newValue value after change.
	 * @param currentStatus current status.
	 * @param threshold the threshold object.
	 * @return new thresholds status if applicable.
	 */
	ThresholdStatus getNewStatusOnUpdate(String previousValue, String newValue, ThresholdStatus currentStatus, Threshold threshold);
}
