package net.anotheria.moskito.sql.stats;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

import java.util.Arrays;

/**
 * On demand stats factory for QueryStats object.
 *
 * @author lrosenberg
 * @since 10.08.14 23:34
 */
public class QueryStatsFactory implements IOnDemandStatsFactory<QueryStats> {

	/**
	 * Configured intervals that have to be passed to the created stats object.
	 */
	private Interval[] intervals;

	/**
	 * Default instance to spare additional object creation.
	 */
	public static final QueryStatsFactory DEFAULT_INSTANCE = new QueryStatsFactory();

	/**
	 * Creates a new factory with custom intervals.
	 * @param configuredIntervals
	 */
	public QueryStatsFactory(Interval[] configuredIntervals){
		intervals = Arrays.copyOf(configuredIntervals, configuredIntervals.length);
	}

	/**
	 * Createsa new factory with default intervals.
	 */
	public QueryStatsFactory(){
		this(Constants.getDefaultIntervals());
	}

	@Override public QueryStats createStatsObject(String name) {
		return new QueryStats(name, intervals);
	}
}
