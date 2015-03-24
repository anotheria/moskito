package net.anotheria.moskito.core.config.gauges;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
@ConfigureMe
public class GaugeConfig implements Serializable{
	@Configure
	private String name;
	@Configure
	private GaugeValueConfig minValue;
	@Configure
	private GaugeValueConfig currentValue;
	@Configure
	private GaugeValueConfig maxValue;

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

	@Override public String toString(){
		return "Name: "+getName()+", Min: "+getMinValue()+", Cur: "+getCurrentValue()+", Max: "+getMaxValue();
	}
}
