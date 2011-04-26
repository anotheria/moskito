package net.java.dev.moskito.core.treshold;

/**
 * Utility class for working with thresholds.
 * @author lrosenberg
 *
 */
public final class Thresholds {
	//prevent initialization
	private Thresholds(){}

	public static void addThreshold(String name, String producerName, String statName, String valueName, String intervalName,
			ThresholdConditionGuard... guards) {
		ThresholdDefinition definition = new ThresholdDefinition();
		definition.setName(name);
		definition.setProducerName(producerName);
		definition.setStatName(statName);
		definition.setValueName(valueName);
		definition.setIntervalName(intervalName);

		Threshold threshold = ThresholdRepository.getInstance().createThreshold(definition);
		if (guards != null) {
			for (ThresholdConditionGuard g: guards) {
				threshold.addGuard(g);
			}
		}
	}
	
	public static void addServiceAVGThreshold(String name, String producerName, ThresholdConditionGuard... guards) {
		addServiceAVGThreshold(name, producerName, "5m", guards);
	}
	public static void addServiceAVGThreshold(String name, String producerName, String interval, ThresholdConditionGuard... guards) {
		addThreshold(name, producerName, "cumulated", "AVG", "interval", guards);
	}

	public static void addUrlAVGThreshold(String name, String url, ThresholdConditionGuard... guards) {
		addUrlAVGThreshold(name, url, "5m", guards);
	}

	public static void addUrlAVGThreshold(String name, String url, String interval, ThresholdConditionGuard... guards) {
		addThreshold(name, "RequestURIFilter", url, "AVG", interval, guards);
	}

	public static void addMemoryThreshold(String name, String producerName, String valueName, ThresholdConditionGuard... guards) {
		addMemoryThreshold(name, producerName, valueName, "1m", guards);
	}

	public static void addMemoryThreshold(String name, String producerName, String valueName, String intervalName, ThresholdConditionGuard... guards) {
		addThreshold(name, producerName, producerName, valueName, intervalName, guards);
	}
}
