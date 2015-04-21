package net.anotheria.moskito.core.config.gauges;

import org.configureme.annotations.Configure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
public class GaugesConfig implements Serializable {
	@Configure
	private GaugeConfig[] gauges;

	@Configure
	private GaugeZoneConfig[] defaultZones;


	public GaugeConfig[] getGauges() {
		return gauges;
	}

	public void setGauges(GaugeConfig[] gauges) {
		this.gauges = gauges;
	}

	public GaugeZoneConfig[] getDefaultZones() {
		return defaultZones;
	}

	public void setDefaultZones(GaugeZoneConfig[] defaultZones) {
		this.defaultZones = defaultZones;
	}

	@Override public String toString(){
		return "GaugesConfig with gauges: "+ Arrays.toString(gauges)+", defaultZones: "+defaultZones;
	}
}
