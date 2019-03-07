package net.anotheria.moskito.core.config.gauges;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Gauges section of the config.
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class GaugesConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2611901466315038880L;

	/**
	 * Configured gauges.
	 */
	@Configure
	@SerializedName("@gauges")
	private GaugeConfig[] gauges;

	/**
	 * Default zones if no zones are configured in a gauge.
	 */
	@Configure
	@SerializedName("@defaultZones")
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
		return "GaugesConfig with gauges: "+ Arrays.toString(gauges)+", defaultZones: "+Arrays.toString(defaultZones);
	}
}
