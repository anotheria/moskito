package net.anotheria.moskito.core.logging;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Small utility class to provide common logging methods.
 *
 * @author lrosenberg
 * @since 2019-03-08 10:32
 */
public final class LoggerUtil {

	/**
	 * Private constructor, prevent initialization.
	 */
	private LoggerUtil(){

	}

	/**
	 * Creates interval stats loggers for all configured intervals. As logger name prefix this method uses 'Moskito'.
	 * @param producer producer to attach loggers to.
	 */
	public static final void createSLF4JIntervalStatsLoggerForAllConfiguredIntervals(IStatsProducer producer){
		createSLF4JIntervalStatsLoggerForAllConfiguredIntervals(producer, "Moskito");

	}

	/**
	 * Creates interval stats loggers for all configured intervals. Every logger is attached to a logger with name
	 * loggerNamePrefix&lt;intervalName&gt;. If loggerNamePrefix is for example 'foo' then all loggers are attached to
	 * foo1m, foo5m, foo15m etc, for all configured intervals.
	 * @param producer producer to attach loggers to.
	 * @param loggerNamePrefix loggerNamePrefix prefix.
	 */
	public static final void createSLF4JIntervalStatsLoggerForAllConfiguredIntervals(IStatsProducer producer, String loggerNamePrefix){
		List<String> configuredIntervals = MoskitoConfigurationHolder.getConfiguration().getConfiguredIntervalNames();
		for (String intervalName : configuredIntervals){
			new IntervalStatsLogger(producer,
					IntervalRegistry.getInstance().getInterval(intervalName),
					new SLF4JLogOutput(LoggerFactory.getLogger(loggerNamePrefix+intervalName)));
		}
	}


	public static final void createSLF4JDefaultStatsLogger(IStatsProducer producer){
		createSLF4JDefaultStatsLogger(producer, "MoskitoDefault");
	}

	public static final void createSLF4JDefaultStatsLogger(IStatsProducer producer, String loggerName ){
		new DefaultStatsLogger(producer, new SLF4JLogOutput(LoggerFactory.getLogger(loggerName)));

	}

	public static final void createSLF4JDefaultAndIntervalStatsLogger(IStatsProducer producer){
		createSLF4JDefaultAndIntervalStatsLogger(producer, "Moskito");
	}

	public static final void createSLF4JDefaultAndIntervalStatsLogger(IStatsProducer producer, String loggerNamePrefix){
		createSLF4JDefaultStatsLogger(producer, loggerNamePrefix+"Default");
		createSLF4JIntervalStatsLoggerForAllConfiguredIntervals(producer, loggerNamePrefix);
	}
}
