package net.java.dev.moskito.sql.util;

import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.sql.stats.QueryStringStats;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/30/11
 *         Time: 12:45 PM
 *         To change this template use File | Settings | File Templates.
 */
public class QueryStringStatsFactory {
    /**
	 * Intervals to create stats for.
	 */
	private Interval[] intervals;
	/**
	 * Creates a new FilterStatsFactory with custom intervals.
	 * @param configuredIntervals
	 */
	public QueryStringStatsFactory(Interval[] configuredIntervals){
		intervals = Arrays.copyOf(configuredIntervals, configuredIntervals.length);
	}
	/**
	 * Creates a new FilterStatsFactory with default intervals.
	 */
	public QueryStringStatsFactory(){
		this(Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new FilterStats object with the given name.
     * @param name
     * @return
     */
	public IStats createStatsObject(String name) {
		return new QueryStringStats(name, intervals);
	}
}
