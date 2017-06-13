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
	 * @return
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
		for (String name : gcNames) {
			String currentCountAccName = String.format("GC %s current collection count 1m", name);
			String totalCountAccName = String.format("GC %s total collection count 1m", name);
			String currentTimeAccName = String.format("GC %s current collection time 1m", name);
			String totalTimeAccName = String.format("GC %s total collection time 1m", name);
			accumulators.addAll(Arrays.asList(currentCountAccName, totalCountAccName, currentTimeAccName, totalTimeAccName));
			Accumulators.createAccumulator(currentCountAccName, "GC", name, "CurrentCollectionCount", "1m");
			Accumulators.createAccumulator(totalCountAccName, "GC", name, "TotalCollectionCount", "1m");
			Accumulators.createAccumulator(currentTimeAccName, "GC", name, "CurrentCollectionTime", "1m");
			Accumulators.createAccumulator(totalTimeAccName, "GC", name, "TotalCollectionTime", "1m");
		}
		AccumulatorSetConfig[] accumulatorSets = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().getAccumulatorSets();
		if (accumulatorSets==null)
			accumulatorSets = new AccumulatorSetConfig[0];
		List<AccumulatorSetConfig> setConfig = new ArrayList<>(
				Arrays.asList(accumulatorSets)
		);
		AccumulatorSetConfig gcSet = new AccumulatorSetConfig();
		gcSet.setName("GC 1 minute");
		gcSet.setMode(AccumulatorSetMode.MULTIPLE);
		String[] accNames = new String[accumulators.size()];
		gcSet.setAccumulatorNames(accumulators.toArray(accNames));
		setConfig.add(gcSet);
		AccumulatorSetConfig[] accSets = new AccumulatorSetConfig[setConfig.size()];
		MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().setAccumulatorSets(setConfig.toArray(accSets));
	}
}
