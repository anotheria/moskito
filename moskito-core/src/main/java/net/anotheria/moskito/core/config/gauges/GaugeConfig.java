package net.anotheria.moskito.core.config.gauges;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration object for a single gauge..
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class GaugeConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the gauge.
	 */
	@Configure
	private String name;
	/**
	 * Min value of the gauge.
	 */
	@Configure
	private GaugeValueConfig minValue;
	/**
	 * Current value of the gauge.
	 */
	@Configure
	private GaugeValueConfig currentValue;
	/**
	 * Max value of the gauge.
	 */
	@Configure
	private GaugeValueConfig maxValue;
	/**
	 * Caption of the gauge.
	 */
	@Configure
	private String caption;
	/**
	 * Optional zones.
	 */
	@Configure
	private GaugeZoneConfig[] zones;

	public GaugeValueConfig getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(GaugeValueConfig currentValue) {
		this.currentValue = currentValue;
	}

	public GaugeValueConfig getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(GaugeValueConfig maxValue) {
		this.maxValue = maxValue;
	}

	public GaugeValueConfig getMinValue() {
		return minValue;
	}

	public void setMinValue(GaugeValueConfig minValue) {
		this.minValue = minValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
        return caption == null ? name : caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public GaugeZoneConfig[] getZones() {
		return zones;
	}

	public void setZones(GaugeZoneConfig[] zones) {
		this.zones = zones;
	}

	@Override public String toString(){
        return "Name: "+ name +", Min: "+ minValue +", Cur: "+ currentValue +", Max: "+ maxValue +", Zones: "+ Arrays.toString(zones);
	}
}
