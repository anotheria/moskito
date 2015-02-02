package net.anotheria.moskito.core.config.gauges;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 29.12.14 01:53
 */
public class GaugeConfig {
	private String minValue;
	private String currentValue;
	private String maxValue;

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	@Override public String toString(){
		return "Min: "+getMinValue()+", Cur: "+getCurrentValue()+", Max: "+getMaxValue();
	}
}
