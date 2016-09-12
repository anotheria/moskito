package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates PageInBrowserStats objects for on demand producers.
 *
 * @author Illya Bogatyrchuk
 */
public class PageInBrowserStatsFactory extends AbstractStatsFactory<PageInBrowserStats> {
	/**
	 * Constructor.
	 */
	public PageInBrowserStatsFactory() {
		super();
	}

	/**
	 * Constructor.
	 *
	 * @param intervalSelection selected intervals
	 */
	public PageInBrowserStatsFactory(final Interval[] intervalSelection) {
		super(intervalSelection == null || intervalSelection.length <= 0 ? Constants.getDefaultIntervals() : intervalSelection);
	}

	@Override
	public PageInBrowserStats createStatsObject(final String url) {
		return new PageInBrowserStats(url, getIntervals());
	}
}
