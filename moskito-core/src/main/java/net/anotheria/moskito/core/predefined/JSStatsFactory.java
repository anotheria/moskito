package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates JSStats objects for on demand producers.
 *
 * @author Illya Bogatyrchuk
 */
public class JSStatsFactory implements IOnDemandStatsFactory<JSStats> {
	/**
	 * Array of {@link Interval}.
	 */
	private Interval[] intervalSelection;

	/**
	 * Constructor.
	 */
	public JSStatsFactory() {
		this(Constants.getDefaultIntervals());
	}

	/**
	 * Constructor.
	 *
	 * @param intervalSelection selected intervals
	 */
	public JSStatsFactory(final Interval[] intervalSelection) {
		this.intervalSelection = intervalSelection;
	}

	@Override
	public JSStats createStatsObject(final String url) {
		return new JSStats(url, intervalSelection);
	}
}
