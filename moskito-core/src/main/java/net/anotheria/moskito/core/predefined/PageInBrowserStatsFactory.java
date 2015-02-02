package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates PageInBrowserStats objects for on demand producers.
 *
 * @author Illya Bogatyrchuk
 */
public class PageInBrowserStatsFactory implements IOnDemandStatsFactory<PageInBrowserStats> {
	/**
	 * Array of {@link Interval}.
	 */
	private Interval[] intervalSelection;

	/**
	 * Constructor.
	 */
	public PageInBrowserStatsFactory() {
		this(Constants.getDefaultIntervals());
	}

	/**
	 * Constructor.
	 *
	 * @param intervalSelection selected intervals
	 */
	public PageInBrowserStatsFactory(final Interval[] intervalSelection) {
		this.intervalSelection = intervalSelection;
	}

	@Override
	public PageInBrowserStats createStatsObject(final String url) {
		return new PageInBrowserStats(url, intervalSelection);
	}
}
