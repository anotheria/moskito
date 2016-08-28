package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for the counter stats.
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class CounterStatsFactory extends AbstractStatsFactory<CounterStats> {
	/**
	 * Singleton instance for reducing number of classes.
	 */
	public static final AbstractStatsFactory<CounterStats> DEFAULT_INSTANCE = new CounterStatsFactory();

	/**
	 * Constructor.
	 */
	public CounterStatsFactory(){
		super();
	}
	/**
	 * Constructor.
	 * @param myIntervals predefined {@link Interval} array
	 */
	public CounterStatsFactory(final Interval[] myIntervals){
		super(myIntervals==null || myIntervals.length<=0 ? Constants.getDefaultIntervals() : myIntervals);
	}

	@Override
	public CounterStats createStatsObject(String name) {
		return new CounterStats(name, getIntervals());
	}
}
