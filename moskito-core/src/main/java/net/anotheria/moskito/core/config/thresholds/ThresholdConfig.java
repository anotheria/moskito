package net.anotheria.moskito.core.config.thresholds;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This configuration class represents a single threshold.
 *
 * @author lrosenberg
 * @since 25.10.12 10:29
 */
@ConfigureMe(allfields = true)
public class ThresholdConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * Name of the threshold.
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
	 * Configuration of the guards.
	 */
	@Configure
	private GuardConfig[] guards;

	public GuardConfig[] getGuards() {
		//the effort below is to make findbugs happy.
		if (guards==null ||guards.length==0)
			return new GuardConfig[0];
		GuardConfig[] ret = new GuardConfig[guards.length];
		System.arraycopy(guards, 0, ret, 0, guards.length);
		return ret;
	}

	public void setGuards(GuardConfig[] guards) {
		if (guards==null || guards.length==0)
			return;
		this.guards = new GuardConfig[guards.length];
		System.arraycopy(guards, 0, this.guards, 0 , guards.length);
	}

	@Override
	public String toString() {
		return "ThresholdConfig{" +
				"name='" + name + '\'' +
				", producerName='" + producerName + '\'' +
				", statName='" + statName + '\'' +
				", valueName='" + valueName + '\'' +
				", intervalName='" + intervalName + '\'' +
				", timeUnit='" + timeUnit + '\'' +
				", guards=" + (guards == null ? null : Arrays.toString(guards)) +
				'}';
	}

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
}
