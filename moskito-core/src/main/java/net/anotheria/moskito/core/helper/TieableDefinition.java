package net.anotheria.moskito.core.helper;

import net.anotheria.moskito.core.stats.TimeUnit;

import java.io.Serializable;

/**
 * Class that defines moskito components which are tie-able, which means can serve as target for action.
 * Thresholds and accumulators are using tie-ables.
 * @author lrosenberg
 *
 */
public class TieableDefinition implements Serializable{
	/**
	 * Name of the tieable.
	 */
	private String name;
	/**
	 * Name of the producer.
	 */
	private String producerName;
	/**
	 * Name of the stat of the producer.
	 */
	private String statName;
	/**
	 * Name of the value.
	 */
	private String valueName;
	/**
	 * Name of the interval.
	 */
	private String intervalName;
	/**
	 * TimeUnit, default is millis.
	 */
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;


	/**
	 * Human readable description for the ui.

	 */
	private String description;

	
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
	public String getName() {
		return name == null ? describe() : name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String describe(){
		return getProducerName()+"."+getStatName()+"."+getValueName()+"/"+getIntervalName()+"/"+getTimeUnit();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
