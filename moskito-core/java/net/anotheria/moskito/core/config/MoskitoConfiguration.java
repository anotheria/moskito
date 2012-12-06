package net.anotheria.moskito.core.config;

import com.google.gson.annotations.SerializedName;
import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsAlertsConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * This class contains complete moskito configuration at runtime. It is configured with configureme, but can be altered
 * programmatically. Use MoskitoConfigurationHolder.getConfiguration/setConfiguration to access/alter this class.
 *
 * @author lrosenberg
 * @since 22.10.12 15:59
 */
@ConfigureMe(name="moskito")
public class MoskitoConfiguration {
	@Configure
	@SerializedName("@thresholdsAlertsConfig")
	private ThresholdsAlertsConfig thresholdsAlertsConfig = new ThresholdsAlertsConfig();

	@Configure
	@SerializedName("@thresholdsConfig")
	private ThresholdsConfig thresholdsConfig = new ThresholdsConfig();

	@Configure
	@SerializedName("@accumulatorsConfig")
	private AccumulatorsConfig accumulatorsConfig = new AccumulatorsConfig();

	public ThresholdsAlertsConfig getThresholdsAlertsConfig() {
		return thresholdsAlertsConfig;
	}

	public void setThresholdsAlertsConfig(ThresholdsAlertsConfig thresholdsAlertsConfig) {
		this.thresholdsAlertsConfig = thresholdsAlertsConfig;
	}

	public ThresholdsConfig getThresholdsConfig() {
		return thresholdsConfig;
	}

	public void setThresholdsConfig(ThresholdsConfig thresholds) {
		this.thresholdsConfig = thresholds;
	}

	@Override public String toString(){
		return "thresholdsAlertsConfig: "+thresholdsAlertsConfig+", thresholds: "+thresholdsConfig+", accumulators:" +accumulatorsConfig;
	}

	public AccumulatorsConfig getAccumulatorsConfig() {
		return accumulatorsConfig;
	}

	public void setAccumulatorsConfig(AccumulatorsConfig accumulatorsConfig) {
		this.accumulatorsConfig = accumulatorsConfig;
	}

}


