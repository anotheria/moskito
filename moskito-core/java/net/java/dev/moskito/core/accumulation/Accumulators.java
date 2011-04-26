package net.java.dev.moskito.core.accumulation;

public final class Accumulators {
	
	private Accumulators(){
		
	}
	
	public static Accumulator setupMemoryAccumulator1m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, "1m");
	}
	
	public static Accumulator setupMemoryAccumulator5m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, "5m");
	}
	
	public static Accumulator setupMemoryAccumulator(String name, String producerName, String valueName, String interval) {
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
}