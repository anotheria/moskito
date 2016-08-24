package net.anotheria.moskito.sql.stats;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * On demand stats factory for QueryStats object.
 *
 * @author lrosenberg
 * @since 10.08.14 23:34
 */
public class QueryStatsFactory extends AbstractStatsFactory<QueryStats> {

	/**
	 * Default instance to spare additional object creation.
	 */
	public static final AbstractStatsFactory<QueryStats> DEFAULT_INSTANCE = new QueryStatsFactory();

	/**
	 * Creates a new factory with custom intervals.
	 * @param configuredIntervals {@link Interval} array
	 */
	public QueryStatsFactory(final Interval[] configuredIntervals){
		super(configuredIntervals == null || configuredIntervals.length <= 0 ? Constants.getDefaultIntervals() : configuredIntervals);
	}

	/**
	 * Createsa new factory with default intervals.
	 */
	public QueryStatsFactory(){
		super();
	}

	@Override public QueryStats createStatsObject(String name) {
		return new QueryStats(name, getIntervals());
	}
}
