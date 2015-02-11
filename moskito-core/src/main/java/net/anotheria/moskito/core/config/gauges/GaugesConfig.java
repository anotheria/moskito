package net.anotheria.moskito.core.config.gauges;

import org.configureme.annotations.Configure;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
public class GaugesConfig {
	@Configure
	private GaugeConfig[] gauges;

	public GaugeConfig[] getGauges() {
		return gauges;
	}

	public void setGauges(GaugeConfig[] gauges) {
		this.gauges = gauges;
	}
}
