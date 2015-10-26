package net.anotheria.moskito.core.config.thresholds;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class contains configuration of thresholds.
 *
 * @author lrosenberg
 * @since 25.10.12 10:29
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class ThresholdsConfig implements Serializable {


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
