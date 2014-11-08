package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Class description!!!!!! Change this!
 *
 * @author Illya Bogatyrchuk
 */
public class JSStatsFactory implements IOnDemandStatsFactory<JSStats> {
	private Interval[] intervalSelection;

	JSStatsFactory() {
		this(Constants.getDefaultIntervals());
	}

	public JSStatsFactory(Interval[] intervalSelection) {
		this.intervalSelection = intervalSelection;
	}

	@Override
	public JSStats createStatsObject(String name) {
		return new JSStats(name, intervalSelection);
	}
}
