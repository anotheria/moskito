package net.anotheria.moskito.core.config;

import net.anotheria.moskito.core.config.thresholds.ThresholdsAlertsConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 15:59
 */
@ConfigureMe(name="moskito")
public class MoskitoConfiguration {
	@Configure
	private ThresholdsAlertsConfig thresholdsAlertsConfig = new ThresholdsAlertsConfig();

	@Configure
	private ThresholdsConfig thresholdsConfig = new ThresholdsConfig();

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
		return "thresholdsAlertsConfig: "+thresholdsAlertsConfig+", thresholds: "+thresholdsConfig;
	}

}


