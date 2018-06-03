package net.anotheria.moskito.webui.producers.api;

/**
 * Used as parameter object for the ProducerAPI to retrieve values. 
 *
 * @author lrosenberg
 * @since 01.06.18 23:20
 */
public class ValueRequestPO {
	/**
	 * Name/id of the producer.
	 */
	private String producerName;
	/**
	 * Name of the stat.
	 */
	private String statName;
	/**
	 * Name of the value.
	 */
	private String valueName;
	/**
	 * Intervalname.
	 */
	private String intervalName;
	/**
	 * Name of the time unit. One of
	 * NANOSECONDS, MICROSECONDS, MILLISECONDS and SECONDS.
	 */
	private String timeUnit;

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

	@Override
	public String toString() {
		return "ValueRequestPO{" +
				"producerName='" + producerName + '\'' +
				", statName='" + statName + '\'' +
				", valueName='" + valueName + '\'' +
				", intervalName='" + intervalName + '\'' +
				", timeUnit='" + timeUnit + '\'' +
				'}';
	}
}
