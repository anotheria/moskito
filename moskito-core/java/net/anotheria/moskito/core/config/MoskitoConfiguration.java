package net.anotheria.moskito.core.config;

import net.anotheria.moskito.core.config.thresholds.ThresholdsAlertsConfig;
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

	public ThresholdsAlertsConfig getThresholdsAlertsConfig() {
		return thresholdsAlertsConfig;
	}

	public void setThresholdsAlertsConfig(ThresholdsAlertsConfig thresholdsAlertsConfig) {
		this.thresholdsAlertsConfig = thresholdsAlertsConfig;
	}

}


