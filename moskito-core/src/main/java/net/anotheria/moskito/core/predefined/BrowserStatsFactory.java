package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates BrowserStats objects for on demand producers.
 *
 * @author Illya Bogatyrchuk
 */
public class BrowserStatsFactory implements IOnDemandStatsFactory<BrowserStats> {
	/**
	 * Array of {@link Interval}.
	 */
	private Interval[] intervalSelection;

	/**
	 * Constructor.
	 */
	public BrowserStatsFactory() {
		this(Constants.getDefaultIntervals());
	}

	/**
	 * Constructor.
	 *
	 * @param intervalSelection selected intervals
	 */
	public BrowserStatsFactory(final Interval[] intervalSelection) {
		this.intervalSelection = intervalSelection;
	}

	@Override
	public BrowserStats createStatsObject(final String url) {
		return new BrowserStats(url, intervalSelection);
	}
}
