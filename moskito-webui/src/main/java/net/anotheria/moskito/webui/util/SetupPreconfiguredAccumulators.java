package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetConfig;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configures preconfigured accumulators and adds them to the running app.
 * Since this is a ServletContextListener, it can be simply added as listener to any webapp:
 * &lt;listener-class&gt;net.anotheria.moskito.webui.util.SetupPreconfiguredAccumulators&lt;/listener-class&gt;.
 */
public class SetupPreconfiguredAccumulators implements ServletContextListener{

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(SetupPreconfiguredAccumulators.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Configuring memory accumulators.");
		setupMemoryAccumulators();
		log.info("Configuring thread accumulators.");
		setupThreadAccumulators();
		log.info("Configuring session accumulators.");
		setupSessionCountAccumulators();
		log.info("Configuring url accumulators.");
		setupUrlAccumulators();
		setupCPUAccumulators();
		setupGCAccumulators();
	}

	/**
	 * Adds accumulator for thread count.
	 */
	public static void setupThreadAccumulators(){
		Accumulators.createAccumulator("ThreadCount", "ThreadCount", "ThreadCount", "current", "default");
		Accumulators.createAccumulator("ThreadStateRunnable-1m", "ThreadStates", "RUNNABLE", "current", "1m");
		Accumulators.createAccumulator("ThreadStateWaiting-1m", "ThreadStates", "WAITING", "current", "1m");
		Accumulators.createAccumulator("ThreadStateBlocked-1m", "ThreadStates", "BLOCKED", "current", "1m");
		Accumulators.createAccumulator("ThreadStateTimedWaiting-1m", "ThreadStates", "TIMED_WAITING", "current", "1m");

		Accumulators.createAccumulator("ThreadStateRunnable-5m", "ThreadStates", "RUNNABLE", "current", "5m");
		Accumulators.createAccumulator("ThreadStateWaiting-5m", "ThreadStates", "WAITING", "current", "5m");
		Accumulators.createAccumulator("ThreadStateBlocked-5m", "ThreadStates", "BLOCKED", "current", "5m");
		Accumulators.createAccumulator("ThreadStateTimedWaiting-5m", "ThreadStates", "TIMED_WAITING", "current", "5m");
	}

	/**
	 * Adds accumulators for session count, new and deleted sessions.
	 */
	public static void setupSessionCountAccumulators(){
		Accumulators.createAccumulator("SessionCount Cur Absolute", "SessionCount", "Sessions", "cur", "default");
		Accumulators.createAccumulator("SessionCount Cur 1h", "SessionCount", "Sessions", "cur", "1h");
		Accumulators.createAccumulator("SessionCount New 1h", "SessionCount", "Sessions", "new", "1h");
		Accumulators.createAccumulator("SessionCount Del 1h", "SessionCount", "Sessions", "del", "1h");
	}
	
	
	public static void setupMemoryAccumulators(){
		Accumulators.createMemoryAccumulator1m("PermGenFree 1m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator1m("PermGenFree MB 1m", "MemoryPool-PS Perm Gen-NonHeap", "Free MB"); 
		Accumulators.createMemoryAccumulator1m("OldGenFree 1m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator1m("OldGenFree MB 1m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator1m("OldGenUsed 1m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator1m("OldGenUsed MB 1m", "MemoryPool-PS Old Gen-Heap", "Used MB");

		Accumulators.createMemoryAccumulator5m("PermGenFree 5m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator5m("PermGenFree MB 5m", "MemoryPool-PS Perm Gen-NonHeap", "Free MB"); 
		Accumulators.createMemoryAccumulator5m("OldGenFree 5m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator5m("OldGenFree MB 5m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator5m("OldGenUsed 5m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator5m("OldGenUsed MB 5m", "MemoryPool-PS Old Gen-Heap", "Used MB");
	
		Accumulators.createMemoryAccumulator("PermGenFree 1h", "MemoryPool-PS Perm Gen-NonHeap", "Free", "1h"); 
		Accumulators.createMemoryAccumulator("PermGenFree MB 1h", "MemoryPool-PS Perm Gen-NonHeap", "Free MB", "1h"); 
		Accumulators.createMemoryAccumulator("OldGenFree 1h", "MemoryPool-PS Old Gen-Heap", "Free", "1h");
		Accumulators.createMemoryAccumulator("OldGenFree MB 1h", "MemoryPool-PS Old Gen-Heap", "Free MB", "1h");
		Accumulators.createMemoryAccumulator("OldGenUsed 1h", "MemoryPool-PS Old Gen-Heap", "Used", "1h");
		Accumulators.createMemoryAccumulator("OldGenUsed MB 1h", "MemoryPool-PS Old Gen-Heap", "Used MB", "1h");
	
	}

	public static void setupUrlAccumulators(){
		Accumulators.createUrlREQAccumulator("URL REQ 1m", "cumulated", "1m");
		Accumulators.createUrlREQAccumulator("URL REQ 5m", "cumulated", "5m");
		Accumulators.createUrlREQAccumulator("URL REQ 1h", "cumulated", "1h");

		Accumulators.createUrlAVGAccumulator("URL AVG 1m", "cumulated", "1m");
		Accumulators.createUrlAVGAccumulator("URL AVG 5m", "cumulated", "5m");
		Accumulators.createUrlAVGAccumulator("URL AVG 1h", "cumulated", "1h");

		Accumulators.createUrlTotalTimeAccumulator("URL Time 1m", "cumulated", "1m");
		Accumulators.createUrlTotalTimeAccumulator("URL Time 5m", "cumulated", "5m");
		Accumulators.createUrlTotalTimeAccumulator("URL Time 1h", "cumulated", "1h");

	}

	public static void setupCPUAccumulators(){
		Accumulators.createAccumulator("CPU Time 1m", "OS", "OS", "CPU Time", "1m", TimeUnit.SECONDS);
		Accumulators.createAccumulator("CPU Time 5m", "OS", "OS", "CPU Time", "5m", TimeUnit.SECONDS);
		Accumulators.createAccumulator("CPU Time 1h", "OS", "OS", "CPU Time", "1h", TimeUnit.SECONDS);
		//OS.OS.CPU Time/default/NANOSECONDS
	}

	/**
	 * Adds accumulators for garbage collectors and creates set for them.
	 */
	public static void setupGCAccumulators(){
		//setup only if gc producer configured
		if (!MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isGcProducer())
			return;
		List<String> accumulators = new ArrayList<>();
		for (GarbageCollectorMXBean bean : ManagementFactory.getGarbageCollectorMXBeans()) {
			accumulators.addAll(setupGCAccumulators(bean.getName()));
		}
		setupGCAccumulatorsSet(accumulators);

	}

	/**
	 * Adds accumulators for given garbage collector.
	 * @param gcName collector name
	 * @return list of accumulators names
     */
	private static List<String> setupGCAccumulators(String gcName){
		List<String> accumulators = new ArrayList<>();
		String currentCountAccName = String.format("GC %s current collection count 1m", gcName);
		String totalCountAccName = String.format("GC %s total collection count 1m", gcName);
		String currentTimeAccName = String.format("GC %s current collection time 1m", gcName);
		String totalTimeAccName = String.format("GC %s total collection time 1m", gcName);
		accumulators.addAll(Arrays.asList(currentCountAccName, totalCountAccName, currentTimeAccName, totalTimeAccName));
		Accumulators.createAccumulator(currentCountAccName, "GC", gcName, "CurrentCollectionCount", "1m");
		Accumulators.createAccumulator(totalCountAccName, "GC", gcName, "TotalCollectionCount", "1m");
		Accumulators.createAccumulator(currentTimeAccName, "GC", gcName, "CurrentCollectionTime", "1m");
		Accumulators.createAccumulator(totalTimeAccName, "GC", gcName, "TotalCollectionTime", "1m");
		return accumulators;
	}

	/**
	 * Adds GC accumulators set.
	 * @param accNames accumulators names
	 */
	private static void setupGCAccumulatorsSet(List<String> accNames){
		AccumulatorSetConfig[] accumulatorSets = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().getAccumulatorSets();
		if (accumulatorSets==null)
			accumulatorSets = new AccumulatorSetConfig[0];
		List<AccumulatorSetConfig> setConfigs = new ArrayList<>(
				Arrays.asList(accumulatorSets)
		);
		AccumulatorSetConfig gcSet = new AccumulatorSetConfig();
		gcSet.setName("GC 1 minute");
		gcSet.setMode(AccumulatorSetMode.MULTIPLE);
		String[] accNamesArray = new String[accNames.size()];
		gcSet.setAccumulatorNames(accNames.toArray(accNamesArray));
		setConfigs.add(gcSet);
		AccumulatorSetConfig[] setConfigsArray = new AccumulatorSetConfig[setConfigs.size()];
		MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().setAccumulatorSets(setConfigs.toArray(setConfigsArray));
	}



	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
 