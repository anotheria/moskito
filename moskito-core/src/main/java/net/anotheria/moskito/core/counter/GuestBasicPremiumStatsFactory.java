package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 17.11.12 22:31
 */
public class GuestBasicPremiumStatsFactory implements IOnDemandStatsFactory<GuestBasicPremiumStats> {
	/**
	 * Interval selection.
	 */
	private Interval[] intervalSelection;

	GuestBasicPremiumStatsFactory(){
		this(Constants.getDefaultIntervals());
	}

	GuestBasicPremiumStatsFactory(Interval[] myIntervals){
		intervalSelection = myIntervals;
	}

	@Override
	public GuestBasicPremiumStats createStatsObject(String name) {
		return new GuestBasicPremiumStats(name, intervalSelection);
	}


}
