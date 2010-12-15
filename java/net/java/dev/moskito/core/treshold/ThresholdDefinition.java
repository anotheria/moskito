package net.java.dev.moskito.core.treshold;

import net.java.dev.moskito.core.stats.TimeUnit;

public class ThresholdDefinition {
	private String producerName;
	private String statName;
	private String valueName;
	private String intervalName;
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

	
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	@Override public String toString(){
		return getProducerName()+"."+getStatName()+"."+getValueName();
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
}
