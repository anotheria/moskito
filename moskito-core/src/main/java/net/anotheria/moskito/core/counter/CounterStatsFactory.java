package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for the counter stats.
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class CounterStatsFactory implements IOnDemandStatsFactory<CounterStats> {

	/**
	 * Selected intervals for new object creation.
	 */
	private Interval[] intervalSelection;

	/**
	 * Singleton instance for reducing number of classes.
	 */
	public static final IOnDemandStatsFactory DEFAULT_INSTANCE = new CounterStatsFactory();

	public CounterStatsFactory(){
		this(Constants.getDefaultIntervals());
	}

	public CounterStatsFactory(Interval[] myIntervals){
		intervalSelection = myIntervals;
	}

	@Override
	public CounterStats createStatsObject(String name) {
		return new CounterStats(name, intervalSelection);
	}
}
