package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.Configure;

import java.util.Arrays;

/**
 * This class contains configuration of thresholds.
 *
 * @author lrosenberg
 * @since 25.10.12 10:29
 */
public class ThresholdsConfig {


	/**
	 * Configured thresholds.
	 */
	@Configure
	private ThresholdConfig[] thresholds;

	public ThresholdConfig[] getThresholds() {
		return thresholds;
	}

	public void setThresholds(ThresholdConfig[] thresholds) {
		this.thresholds = thresholds;
	}

	@Override public String toString(){
		return Arrays.toString(thresholds);
	}
}
