package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
		log.info("Configuring thread accumulators.");
		setupThreadAccumulators();
		log.info("Configuring session accumulators.");
		setupSessionCountAccumulators();
		log.info("Configuring url accumulators.");
		setupUrlAccumulators();
		setupCPUAccumulators();
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

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
 