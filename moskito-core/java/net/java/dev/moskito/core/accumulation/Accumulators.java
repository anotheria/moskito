package net.java.dev.moskito.core.accumulation;
/**
 * Utility class for accumulator handling and factory methods.
 * @author lrosenberg
 *
 */
public final class Accumulators {
	/**
	 * Default interval for accumulator creation. Whenever a method doesn't specify the interval name explicitely this interval is used.
	 */
	public static final String DEFAULT_INTERVAL = "5m";
	/**
	 * Protect from instantiating.
	 */
	private Accumulators(){
		
	}
	/**
	 * Create a new memory pool value accumulator for 1m interval. 
	 * @param name name of the accumulator, for example OldGenFree.
	 * @param producerName Name of the producer to tie the accumulator to. For example MemoryPool-PS Old Gen-Heap.
	 * @param valueName Name of the value to tie the accumulator to. For example Free or Used.
	 * @return
	 */
	public static Accumulator createMemoryAccumulator1m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, "1m");
	}
	
	/**
	 * Create a new memory pool value accumulator for 5m interval. 
	 * @param name name of the accumulator, for example OldGenFree.
	 * @param producerName Name of the producer to tie the accumulator to. For example MemoryPool-PS Old Gen-Heap.
	 * @param valueName Name of the value to tie the accumulator to. For example Free or Used.
	 * @return
	 */
	public static Accumulator createMemoryAccumulator5m(String name, String producerName, String valueName) {
		return createAccumulator(name, producerName, producerName, valueName, DEFAULT_INTERVAL);
	}
	
	/**
	 * Create a new memory pool value accumulator. 
	 * @param name name of the accumulator, for example OldGenFree.
	 * @param producerName Name of the producer to tie the accumulator to. For example MemoryPool-PS Old Gen-Heap.
	 * @param valueName Name of the value to tie the accumulator to. For example Free or Used.
	 * @param interval interval to tie this accumulator to.
	 * @return
	 */
	public static Accumulator createMemoryAccumulator(String name, String producerName, String valueName, String interval) {
		return createAccumulator(name, producerName, producerName, valueName, interval);
	}
	
	/**
	 * Creates a new accumulator.
	 * @param name name of the accumulator.
	 * @param producerName name of the corresponding producer.
	 * @param statName name of the stat object (for example cumulated, or an url, or a service method).
	 * @param valueName name of the value, like AVG, REQ, Free etc.
	 * @param intervalName name of the interval.
	 * @return
	 */
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

	/**
	 * Creates a new accumulator for service average response time measurement.
	 * @param name name of the accumulator.
	 * @param producerName name of the producer.
	 */
	public static void createServiceAVGAccumulator(String name, String producerName) {
		createServiceAVGAccumulator(name, producerName, DEFAULT_INTERVAL);
	}
	/**
	 * Creates a new accumulator for service average response time measurement.
	 * @param name name of the accumulator.
	 * @param producerName name of the producer.
	 * @param interval name of the interval.
	 */
	public static void createServiceAVGAccumulator(String name, String producerName, String interval) {
		createAccumulator(name, producerName, "cumulated", "AVG", interval);
	}

	/**
	 * Creates a new accumulator for service req count.
	 * @param name name of the accumulator.
	 * @param producerName name of the producer.
	 * @param interval name of the interval.
	 */
	public static void createServiceREQAccumulator(String name, String producerName) {
		createServiceREQAccumulator(name, producerName, DEFAULT_INTERVAL);
	}
	/**
	 * Creates a new accumulator for service req count.
	 * @param name name of the accumulator.
	 * @param producerName name of the producer.
	 * @param interval name of the interval.
	 */
	public static void createServiceREQAccumulator(String name, String producerName, String interval) {
		createAccumulator(name, producerName, "cumulated", "REQ", interval);
	}

	/**
	 * Creates a new accumulator for avg response time of the url. Requires net.java.dev.moskito.web.filters.RequestURIFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 */
	public static void createUrlAVGAccumulator(String name, String url){
		createUrlAVGAccumulator(name, url, DEFAULT_INTERVAL);
	}
	
	/**
	 * Creates a new accumulator for avg response time of the url. Requires net.java.dev.moskito.web.filters.RequestURIFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 * @param interval name of the interval.
	 */
	public static void createUrlAVGAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURIFilter", url, "AVG", interval);
	}

	/**
	 * Creates a new accumulator for request count of the url. Requires net.java.dev.moskito.web.filters.RequestURIFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 */
	public static void createUrlREQAccumulator(String name, String url){
		createUrlREQAccumulator(name, url, DEFAULT_INTERVAL);
	}
	
	/**
	 * Creates a new accumulator for request count of the url. Requires net.java.dev.moskito.web.filters.RequestURIFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 * @param interval name of the interval.
	 */
	public static void createUrlREQAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURIFilter", url, "REQ", interval);
	}
}