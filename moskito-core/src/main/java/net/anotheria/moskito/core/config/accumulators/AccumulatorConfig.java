package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * This configuration class represents a single accumulator.
 *
 * @author lrosenberg
 * @since 03.12.12 10:58
 */
@ConfigureMe(allfields = true)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class AccumulatorConfig implements Serializable {
	/**
	 * Name of the accumulator.
	 */
	@Configure
	private String name;
	/**
	 * Name of the producer.
	 */
	@Configure
	private String producerName;
	/**
	 * Name of the stat of the producer.
	 */
	@Configure
	private String statName;
	/**
	 * Name of the value.
	 */
	@Configure
	private String valueName;
	/**
	 * Name of the interval. 5m is default.
	 */
	@Configure
	private String intervalName = "5m";
	/**
	 * Timeunit, default is millis.
	 */
	@Configure
	private String timeUnit = TimeUnit.MILLISECONDS.name();

	/**
	 * Accumulation amount for this accumulator. Not needed to be set, in case its 0, the default amount will be used.
	 */
	@Configure
	private int accumulationAmount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public int getAccumulationAmount() {
		return accumulationAmount;
	}

	public void setAccumulationAmount(int accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}

	@Override public String toString(){
		return getName()+" = "+getProducerName()+"."+getStatName()+"."+getValueName()+" "+getIntervalName()+" "+getTimeUnit();
	}
}
