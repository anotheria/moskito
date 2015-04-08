package net.anotheria.moskito.core.config.gauges;

import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
public class GaugesConfig implements Serializable {
	@Configure
	private GaugeConfig[] gauges;

	public GaugeConfig[] getGauges() {
		return gauges;
	}

	public void setGauges(GaugeConfig[] gauges) {
		this.gauges = gauges;
	}
}
