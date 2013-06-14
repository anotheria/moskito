package net.anotheria.moskito.core.threshold;

/**
 * Represents a producer in a status.
 *
 * @author lrosenberg
 * @since 14.06.13 10:53
 */
public class ThresholdInStatus {
	/**
	 * Name of the Threshold.
	 */
	private String thresholdName;

	/**
	 * Value of the producer.
	 */
	private String value;

	/**
	 * Additional message if this producer supports additional messages.
	 */
	private String additionalMessage;

	public String getThresholdName() {
		return thresholdName;
	}

	public void setThresholdName(String thresholdName) {
		this.thresholdName = thresholdName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}
}
