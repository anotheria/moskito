package net.anotheria.moskito.core.util.session;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.13 23:55
 */
public class SessionCountFactory implements IOnDemandStatsFactory<SessionCountStats>{

	/**
	 * Configured intervals that have to be passed to the created stats object.
	 */
	private Interval[] intervals;

	/**
	 * Default instance to spare additional object creation.
	 */
	public static final SessionCountFactory DEFAULT_INSTANCE = new SessionCountFactory();

	/**
	 * Creates a new factory with custom intervals.
	 * @param configuredIntervals
	 */
	public SessionCountFactory(Interval[] configuredIntervals){
		intervals = Arrays.copyOf(configuredIntervals, configuredIntervals.length);
	}

	/**
	 * Createsa new factory with default intervals.
	 */
	public SessionCountFactory(){
		this(Constants.getDefaultIntervals());
	}

	@Override public SessionCountStats createStatsObject(String name) {
		return new SessionCountStats(name, intervals);
	}
}