package net.anotheria.moskito.core.config.gauges;

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
public class GaugeConfig implements Serializable{
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
		return caption == null ? getName() : caption;
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
		return "Name: "+getName()+", Min: "+getMinValue()+", Cur: "+getCurrentValue()+", Max: "+getMaxValue()+", Zones: "+ Arrays.toString(zones);
	}
}
