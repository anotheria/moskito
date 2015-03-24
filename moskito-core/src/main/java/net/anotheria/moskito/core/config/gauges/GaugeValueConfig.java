package net.anotheria.moskito.core.config.gauges;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * A gauge value represents a min/max/current value for a gauge. It can be a constant or it can be a reference to a producer.
 * In later case it only contains the interval name.
 *
 * @author lrosenberg
 * @since 23.03.15 21:32
 */
@ConfigureMe
public class GaugeValueConfig implements Serializable {
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
	 * Alternative way to setup a GaugeValue is to provide a constant.
	 * If a constant is not null other values are ignored.
	 */
	@Configure
	private Integer constant;

	public Integer getConstant() {
		return constant;
	}

	public void setConstant(Integer constant) {
		this.constant = constant;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
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

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	@Override
	public String toString() {
		return "GaugeValue{" +
				"constant=" + constant +
				", producerName='" + producerName + '\'' +
				", statName='" + statName + '\'' +
				", valueName='" + valueName + '\'' +
				", intervalName='" + intervalName + '\'' +
				", timeUnit='" + timeUnit + '\'' +
				'}';
	}
}
