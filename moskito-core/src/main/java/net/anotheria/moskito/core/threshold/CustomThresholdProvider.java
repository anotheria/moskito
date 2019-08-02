package net.anotheria.moskito.core.threshold;

import java.util.List;

/**
 * If a producer is a CustomThresholdProvider it will be asked for its threshold status instead of the actual stats by the threshold.
 *
 * @author lrosenberg
 * @since 26.02.18 18:04
 */
public interface CustomThresholdProvider {
	List<String> getCustomThresholdNames();

	CustomThresholdStatus getCustomThresholdStatus(String thresholdName);
}
