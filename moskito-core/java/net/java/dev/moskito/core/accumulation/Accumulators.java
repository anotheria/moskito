package net.java.dev.moskito.core.accumulation;



public final class Accumulators {
	
	public static final String DEFAULT_INTERVAL = "5m";
	
	private Accumulators(){
		
	}
	
	public static Accumulator createMemoryAccumulator1m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, "1m");
	}
	
	public static Accumulator createMemoryAccumulator5m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, DEFAULT_INTERVAL);
	}
	
	public static Accumulator createMemoryAccumulator(String name, String producerName, String valueName, String interval) {
		return createAccumulator(name, producerName, producerName, valueName, interval);
	}
	
	public static Accumulator createAccumulator(String name, String producerName, String statName, String valueName, String intervalName) {
		AccumulatorDefinition definition = new AccumulatorDefinition();
		definition.setName(name);
		definition.setProducerName(producerName);
		definition.setStatName(statName);
		definition.setValueName(valueName);
		definition.setIntervalName(intervalName);
	
		Accumulator acc = AccumulatorRepository.getInstance().createAccumulator(definition);
		return acc;
	}

	public static void createServiceAVGAccumulator(String name, String producerName) {
		createServiceAVGAccumulator(name, producerName, DEFAULT_INTERVAL);
	}
	public static void createServiceAVGAccumulator(String name, String producerName, String interval) {
		createAccumulator(name, producerName, "cumulated", "AVG", interval);
	}

	public static void createServiceREQAccumulator(String name, String producerName) {
		createServiceREQAccumulator(name, producerName, DEFAULT_INTERVAL);
	}
	public static void createServiceREQAccumulator(String name, String producerName, String interval) {
		createAccumulator(name, producerName, "cumulated", "REQ", interval);
	}

	public static void createUrlAVGAccumulator(String name, String url){
		createUrlAVGAccumulator(name, url, DEFAULT_INTERVAL);
	}
	
	public static void createUrlAVGAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURIFilter", url, "AVG", interval);
	}

}