package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for male/female stats.
 *
 * @author lrosenberg
 * @since 17.11.12 22:31
 */
public class MaleFemaleStatsFactory  extends AbstractStatsFactory<MaleFemaleStats> {
	/**
	 * Constructor.
	 */
	MaleFemaleStatsFactory(){
		super();
	}

	/**
	 * Constructor.
	 * @param myIntervals predefined {@link Interval} array
	 */
	MaleFemaleStatsFactory(Interval[] myIntervals){
		super(myIntervals==null || myIntervals.length<=0 ? Constants.getDefaultIntervals() : myIntervals);
	}

	@Override
	public MaleFemaleStats createStatsObject(String name) {
		return new MaleFemaleStats(name, getIntervals());
	}


}
