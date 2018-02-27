package net.anotheria.moskito.core.threshold;

/**
 * If a producer is a CustomThresholdProvider it will be asked for its threshold status instead of the actual stats by the threshold.
 *
 * @author lrosenberg
 * @since 26.02.18 18:04
 */
public interface CustomThresholdProvider {
	/**
	 * Returns threshold status.
	 * @return current status.
	 */
	ThresholdStatus getStatus();

	/**
	 * Returns current threshold value.
	 * @return current value.
	 */
	String getCurrentValue();
}
