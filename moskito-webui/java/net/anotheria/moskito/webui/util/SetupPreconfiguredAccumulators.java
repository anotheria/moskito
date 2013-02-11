package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.accumulation.Accumulators;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetupPreconfiguredAccumulators implements ServletContextListener{
	
	private static Logger log = Logger.getLogger(SetupPreconfiguredAccumulators.class);

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
	}

	/**
	 * Adds accumulator for thread count.
	 */
	private static void setupThreadAccumulators(){
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
	private static void setupSessionCountAccumulators(){
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

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
 