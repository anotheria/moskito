package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for male/female stats.
 *
 * @author lrosenberg
 * @since 17.11.12 22:31
 */
public class MaleFemaleStatsFactory implements IOnDemandStatsFactory<MaleFemaleStats> {
	/**
	 * Selection of intervals for create MaleFemaleStatsObjects.
	 */
	private Interval[] intervalSelection;

	MaleFemaleStatsFactory(){
		this(Constants.getDefaultIntervals());
	}

	MaleFemaleStatsFactory(Interval[] myIntervals){
		intervalSelection = myIntervals;
	}

	@Override
	public MaleFemaleStats createStatsObject(String name) {
		return new MaleFemaleStats(name, intervalSelection);
	}


}
