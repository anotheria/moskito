package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for GuestBasicPremiumStats.
 *
 * @author lrosenberg
 * @since 17.11.12 22:31
 */
public class GuestBasicPremiumStatsFactory extends AbstractStatsFactory<GuestBasicPremiumStats> {
	/**
	 * Constructor.
	 */
	GuestBasicPremiumStatsFactory(){
		super();
	}
	/**
	 * Constructor.
	 * @param myIntervals predefined {@link Interval} array
	 */
	GuestBasicPremiumStatsFactory(final Interval[] myIntervals){
		super(myIntervals == null || myIntervals.length <= 0 ? Constants.getDefaultIntervals() : myIntervals);
	}

	@Override
	public GuestBasicPremiumStats createStatsObject(final String name) {
		return new GuestBasicPremiumStats(name, getIntervals());
	}


}
