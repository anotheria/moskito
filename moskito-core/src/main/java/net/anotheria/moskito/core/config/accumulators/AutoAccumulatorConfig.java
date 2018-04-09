package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Auto-accumulator config is a class that defines that in case a new producer is registered, that has a specific pattern a new accumulator should be automatically created.
 * Auto-accumulators are useful if you want to create a standard accumulator to every instance of specific pattern, for example all services should accumululate number of request.
 *
 * @author lrosenberg
 * @since 08.04.18 20:21
 */
@ConfigureMe(allfields = true)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class AutoAccumulatorConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the resulting accumulator. If it contains a $PRODUCERNAME or $PRODUCERID constant it will be replaced with the actual producer id.
	 */
	@Configure
	private String namePattern;

	/**
	 * Pattern to match when defining a producer. Whenever a producer with name (or id) matching this pattern registers a new accumulator will be created.
	 */
	@Configure
	private String producerNamePattern;
	/**
	 * Name of the stat of the producer to be used in auto-accumulator.
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

	public String getNamePattern() {
		return namePattern;
	}

	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}

	public String getProducerNamePattern() {
		return producerNamePattern;
	}

	public void setProducerNamePattern(String producerNamePattern) {
		this.producerNamePattern = producerNamePattern;
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
		return getNamePattern()+" = "+getProducerNamePattern()+ '.' +getStatName()+ '.' +getValueName()+ ' ' +getIntervalName()+ ' ' +getTimeUnit();
	}
}
