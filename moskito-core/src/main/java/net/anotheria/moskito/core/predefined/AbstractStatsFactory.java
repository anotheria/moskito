package net.anotheria.moskito.core.predefined;

import java.util.Arrays;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Common {@link IOnDemandStatsFactory} impl.
 *
 * @author sshscp
 */
public abstract class AbstractStatsFactory<Stats extends IStats> implements IOnDemandStatsFactory<Stats> {
	/**
	 * Configured intervals that have to be passed to the created stats object.
	 */
	private final Interval[] intervals;

	/**
	 * Creates a new factory with custom intervals.
	 *
	 * @param configuredIntervals
	 * 		{@link Interval} array
	 */
	public AbstractStatsFactory(final Interval[] configuredIntervals) {
		intervals = Arrays.copyOf(configuredIntervals, configuredIntervals.length);
	}

	/**
	 * Creates a new factory with default intervals.
	 */
	public AbstractStatsFactory() {
		this(Constants.getDefaultIntervals());
	}


	public Interval[] getIntervals() {
		return intervals;
	}
}
