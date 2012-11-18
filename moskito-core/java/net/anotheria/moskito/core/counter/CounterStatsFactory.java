package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class CounterStatsFactory implements IOnDemandStatsFactory<CounterStats> {

	private Interval[] intervalSelection;

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
