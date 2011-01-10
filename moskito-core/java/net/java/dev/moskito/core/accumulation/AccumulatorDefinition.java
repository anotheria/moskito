package net.java.dev.moskito.core.accumulation;

import net.java.dev.moskito.core.stats.TimeUnit;

public class AccumulatorDefinition {
	/**
	 * Name of the defined accumulator.
	 */
	private String name;
	/**
	 * Name of the assigned producer.
	 */
	private String producerName;
	/**
	 * Name of the assigned stat.
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
	 * TimeUnit.
	 */
	private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
	
	private int accumulationAmount = 200;

	
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
	
	public int getMaxAmountOfAccumulatedItems(){
		return accumulationAmount + (accumulationAmount/10);
	}

	public int getAccumulationAmount() {
		return accumulationAmount;
	}

	public void setAccumulationAmount(int accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}

}
