package net.anotheria.moskito.core.accumulation;


import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetConfig;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	 * Creates a new accumulator.
	 * @param name name of the accumulator.
	 * @param producerName name of the corresponding producer.
	 * @param statName name of the stat object (for example cumulated, or an url, or a service method).
	 * @param valueName name of the value, like AVG, REQ, Free etc.
	 * @param intervalName name of the interval.
	 * @param timeUnit time unit in which the accumulator should be managed and the values recalculated.
	 * @return {@link Accumulator}
	 */
	public static Accumulator createAccumulator(String name, String producerName, String statName, String valueName, String intervalName, TimeUnit timeUnit) {
		AccumulatorDefinition definition = new AccumulatorDefinition();
		definition.setName(name);
		definition.setProducerName(producerName);
		definition.setStatName(statName);
		definition.setValueName(valueName);
		definition.setIntervalName(intervalName);

		Accumulator acc = AccumulatorRepository.getInstance().createAccumulator(definition);
		return acc;
	}

	public static Accumulator createAccumulator(String name, String producerName, String statName, String valueName, String intervalName) {
		return createAccumulator(name, producerName, statName, valueName, intervalName, TimeUnit.MILLISECONDS);
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
	 * Creates a new accumulator for avg response time of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 */
	public static void createUrlAVGAccumulator(String name, String url){
		createUrlAVGAccumulator(name, url, DEFAULT_INTERVAL);
	}
	
	/**
	 * Creates a new accumulator for avg response time of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 * @param interval name of the interval.
	 */
	public static void createUrlAVGAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURI", url, "AVG", interval);
	}

	/**
	 * Creates a new accumulator for request count of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 */
	public static void createUrlREQAccumulator(String name, String url){
		createUrlREQAccumulator(name, url, DEFAULT_INTERVAL);
	}
	
	/**
	 * Creates a new accumulator for request count of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 * @param interval name of the interval.
	 */
	public static void createUrlREQAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURI", url, "REQ", interval);
	}

	/**
	 * Creates a new accumulator for total time of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 */
	public static void createUrlTotalTimeAccumulator(String name, String url){
		createUrlTotalTimeAccumulator(name, url, DEFAULT_INTERVAL);
	}

	/**
	 * Creates a new accumulator for total time of the url. Requires net.anotheria.moskito.web.filters.GenericMonitoringFilter to be present in the web.xml map to the uri.
	 * @param name name of the accumulator.
	 * @param url url to accumulate
	 * @param interval name of the interval.
	 */
	public static void createUrlTotalTimeAccumulator(String name, String url, String interval) {
		createAccumulator(name, "RequestURI", url, "time", interval);
	}


	/**
	 * Creates new memory pool accumulators for memory pool producer.
	 * Creates 9 accumulators for "Free", "Free MB", "Used", "Used MB" values
	 * and "1m", "5m", "1h" intervals.
	 *
	 * @param poolName memory pool name
	 * @param producerName producer name
	 */
	public static void createMemoryPoolAccumulator(String poolName, String producerName){

		final String[] intervals   = new String[]{"1m", "5m", "1h"};
		final String[] valuesNames = new String[]{"Free", "Free MB", "Used", "Used MB"};

		for(String interval : intervals)
			for (String valueName : valuesNames)
				createAccumulator(
							"Mem "+poolName.replaceAll("\\s+","") // Remove whitespaces
								.replaceAll("PS", "") // Remove PS prefix
								+ valueName + " " + interval,

								producerName, producerName, valueName, interval);

	}

	/**
	 * Creates garbage collectors accumulators and gc set.
	 *
	 * @param gcNames garbage collectors names
	 */
	public static void createGCAccumulators(List<String> gcNames) {
		List<String> accumulators = new ArrayList<>();
		String producerName = "GC";
		for (String name : gcNames) {
			String collectionCountAccName = String.format("GC %s collection count 1m", name);
			String totalCollectionCountAccName = String.format("GC %s total collection count", name);
			String collectionTimeAccName = String.format("GC %s collection time 1m", name);
			String totalCollectionTimeAccName = String.format("GC %s total collection time", name);
			accumulators.addAll(Arrays.asList(collectionCountAccName, collectionTimeAccName));
			Accumulators.createAccumulator(collectionCountAccName, producerName, name, "CollectionCount", "1m");
			Accumulators.createAccumulator(totalCollectionCountAccName, producerName, name, "CollectionCount", "default");
			Accumulators.createAccumulator(collectionTimeAccName, producerName, name, "CollectionTime", "1m");
			Accumulators.createAccumulator(totalCollectionTimeAccName, producerName, name, "CollectionTime", "default");
		}
		createAccumulatorsSet("GC 1 minute", accumulators);
	}

	/**
	 * Creates tomcat global request processor accumulators and set for them.
	 *
	 * @param beanNames bean names
	 */
	public static void createGlobalRequestProcessorAccumulators(List<String> beanNames) {
		List<String> accumulators = new ArrayList<>();
		String producerName = "GlobalRequestProcessor";
		String oneMinuteInterval = "1m";
		for (String name : beanNames) {
			String requestCountAccName = String.format("%s request count 1m", name);
			String bytesSentAccName = String.format("%s bytes sent 1m", name);
			String bytesReceivedAccName = String.format("%s bytes received 1m", name);
			String processingTimeAccName = String.format("%s processing time 1m", name);
			String errorCountAccName = String.format("%s error count 1m", name);
			accumulators.addAll(Arrays.asList(requestCountAccName, bytesSentAccName, bytesReceivedAccName, processingTimeAccName, errorCountAccName));
			Accumulators.createAccumulator(requestCountAccName, producerName, name, "RequestCount", oneMinuteInterval);
			Accumulators.createAccumulator(bytesSentAccName, producerName, name, "BytesSent", oneMinuteInterval);
			Accumulators.createAccumulator(bytesReceivedAccName, producerName, name, "BytesReceived", oneMinuteInterval);
			Accumulators.createAccumulator(processingTimeAccName, producerName, name, "ProcessingTime", oneMinuteInterval);
			Accumulators.createAccumulator(errorCountAccName, producerName, name, "ErrorCount", oneMinuteInterval);
		}
		createAccumulatorsSet("Tomcat 1 minute", accumulators);
	}

	public static void setupCPUAccumulators(){
		Accumulators.createAccumulator("CPU Time 1m", "OS", "OS", "CPU Time", "1m", TimeUnit.SECONDS);
		Accumulators.createAccumulator("CPU Time 5m", "OS", "OS", "CPU Time", "5m", TimeUnit.SECONDS);
		Accumulators.createAccumulator("CPU Time 1h", "OS", "OS", "CPU Time", "1h", TimeUnit.SECONDS);
		//OS.OS.CPU Time/default/NANOSECONDS
	}

	private static void createAccumulatorsSet(String name, List<String> accumulators){
		AccumulatorSetConfig[] accumulatorSets = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().getAccumulatorSets();
		if (accumulatorSets==null)
			accumulatorSets = new AccumulatorSetConfig[0];
		List<AccumulatorSetConfig> setConfig = new ArrayList<>(
				Arrays.asList(accumulatorSets)
		);
		AccumulatorSetConfig gcSet = new AccumulatorSetConfig();
		gcSet.setName(name);
		gcSet.setMode(AccumulatorSetMode.MULTIPLE);
		String[] accNames = new String[accumulators.size()];
		gcSet.setAccumulatorNames(accumulators.toArray(accNames));
		setConfig.add(gcSet);
		AccumulatorSetConfig[] accSets = new AccumulatorSetConfig[setConfig.size()];
		MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().setAccumulatorSets(setConfig.toArray(accSets));
	}

}
