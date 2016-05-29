package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats collection for HTML DOM related information.
 *
 * @author Illya Bogatyrchuk
 */
public class PageInBrowserStats extends AbstractStats {
	/**
	 * Url of the page.
	 */
	private String url;
	/**
	 * DOM minimum load time.
	 */
	private StatValue domMinLoadTime;
	/**
	 * DOM maximum load time.
	 */
	private StatValue domMaxLoadTime;
	/**
	 * DOM last load time.
	 */
	private StatValue domLastLoadTime;
	/**
	 * Total DOM load time.
	 */
	private StatValue totalDomLoadTime;
	/**
	 * Web page minimum load time.
	 */
	private StatValue windowMinLoadTime;
	/**
	 * Web page maximum load time.
	 */
	private StatValue windowMaxLoadTime;
	/**
	 * Web page last load time.
	 */
	private StatValue windowLastLoadTime;
	/**
	 * Total web page load time.
	 */
	private StatValue totalWindowLoadTime;
	/**
	 * The number of total requests with loads stats.
	 */
	private StatValue numberOfLoads;
	/**
	 * Available value names.
	 */
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(PageInBrowserStatsValueName.getValueNames());

	/**
	 * Constructor.
	 *
	 * @param urlPath           page's url
	 * @param selectedIntervals array of {@link Interval}
	 */
	public PageInBrowserStats(final String urlPath, final Interval[] selectedIntervals) {
		url = urlPath;

		final long pattern = 0L;
		final Interval[] intervals = Arrays.copyOf(selectedIntervals, selectedIntervals.length);

		domMinLoadTime = StatValueFactory.createStatValue(pattern, "domMinLoadTime", intervals);
		domMaxLoadTime = StatValueFactory.createStatValue(pattern, "domMaxLoadTime", intervals);
		domLastLoadTime = StatValueFactory.createStatValue(pattern, "domLastLoadTime", intervals);
		totalDomLoadTime = StatValueFactory.createStatValue(pattern, "totalDomLoadTime", intervals);
		windowMinLoadTime = StatValueFactory.createStatValue(pattern, "windowMinLoadTime", intervals);
		windowMaxLoadTime = StatValueFactory.createStatValue(pattern, "windowMaxLoadTime", intervals);
		windowLastLoadTime = StatValueFactory.createStatValue(pattern, "windowLastLoadTime", intervals);
		totalWindowLoadTime = StatValueFactory.createStatValue(pattern, "totalWindowLoadTime", intervals);
		numberOfLoads = StatValueFactory.createStatValue(pattern, "numberOfLoads", intervals);

		domMinLoadTime.setDefaultValueAsLong(Constants.MIN_TIME_DEFAULT);
		domMinLoadTime.reset();
		domMaxLoadTime.setDefaultValueAsLong(Constants.MAX_TIME_DEFAULT);
		domMaxLoadTime.reset();

		windowMinLoadTime.setDefaultValueAsLong(Constants.MIN_TIME_DEFAULT);
		windowMinLoadTime.reset();
		windowMaxLoadTime.setDefaultValueAsLong(Constants.MAX_TIME_DEFAULT);
		windowMaxLoadTime.reset();
	}

	/**
	 * Adds DOM load time and page load time to the stats.
	 *
	 * @param domLoadTime    DOM load time
	 * @param windowLoadTime web page load time
	 */
	public void addLoadTime(final long domLoadTime, final long windowLoadTime) {
		totalDomLoadTime.increaseByLong(domLoadTime);
		domMinLoadTime.setValueIfLesserThanCurrentAsLong(domLoadTime);
		domMaxLoadTime.setValueIfGreaterThanCurrentAsLong(domLoadTime);
		domLastLoadTime.setValueAsLong(domLoadTime);
		totalWindowLoadTime.increaseByLong(windowLoadTime);
		windowMinLoadTime.setValueIfLesserThanCurrentAsLong(windowLoadTime);
		windowMaxLoadTime.setValueIfGreaterThanCurrentAsLong(windowLoadTime);
		windowLastLoadTime.setValueAsLong(windowLoadTime);
		numberOfLoads.increase();
	}

	@Override
	public String getName() {
		return url;
	}

	/**
	 * Returns DOM minimum load time for given interval and time unit.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return DOM minimum load time
	 */
	public long getDomMinLoadTime(final String intervalName, final TimeUnit unit) {
		final long min = domMinLoadTime.getValueAsLong(intervalName);
		return min == Constants.MIN_TIME_DEFAULT ? min : unit.transformMillis(min);
	}

	/**
	 * Returns DOM maximum load time for given interval and time unit.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return DOM maximum load time
	 */
	public long getDomMaxLoadTime(final String intervalName, final TimeUnit unit) {
		final long max = domMaxLoadTime.getValueAsLong(intervalName);
		return max == Constants.MAX_TIME_DEFAULT ? max : unit.transformMillis(max);
	}

	/**
	 * Returns DOM last load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return DOM last load time
	 */
	public long getDomLastLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(domLastLoadTime.getValueAsLong(intervalName));
	}

	/**
	 * Returns total DOM loads time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return total DOM loads time
	 */
	public long getTotalDomLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(totalDomLoadTime.getValueAsLong(intervalName));
	}

	/**
	 * Returns web page minimum load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return web page minimum load time
	 */
	public long getWindowMinLoadTime(final String intervalName, final TimeUnit unit) {
		final long min = windowMinLoadTime.getValueAsLong(intervalName);
		return min == Constants.MIN_TIME_DEFAULT ? min : unit.transformMillis(min);
	}

	/**
	 * Returns web page maximum load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return web page maximum load time
	 */
	public long getWindowMaxLoadTime(final String intervalName, final TimeUnit unit) {
		final long max = windowMaxLoadTime.getValueAsLong(intervalName);
		return max == Constants.MAX_TIME_DEFAULT ? max : unit.transformMillis(max);
	}

	/**
	 * Returns web page last load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return web page last load time
	 */
	public long getWindowLastLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(windowLastLoadTime.getValueAsLong(intervalName));
	}

	/**
	 * Returns the average DOM load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return average DOM load time
	 */
	public double getAverageDOMLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(totalDomLoadTime.getValueAsLong(intervalName)) / numberOfLoads.getValueAsDouble(intervalName);
	}

	/**
	 * Returns the average web page load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return average web page load time
	 */
	public double getAverageWindowLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(totalWindowLoadTime.getValueAsLong(intervalName)) / numberOfLoads.getValueAsDouble(intervalName);
	}

	/**
	 * Returns total page loads time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return total page loads time
	 */
	public long getTotalWindowLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(totalWindowLoadTime.getValueAsLong(intervalName));
	}

	/**
	 * Returns the number of total requests with loads stats.
	 *
	 * @return the number of total requests with loads stats
	 */
	public long getNumberOfLoads() {
		return numberOfLoads.getValueAsLong();
	}

	@Override
	public String toStatsString(final String intervalName, final TimeUnit unit) {
		StringBuilder builder = new StringBuilder();
		builder.append(url);

		if (getDomMinLoadTime(intervalName, unit) == Constants.MIN_TIME_DEFAULT)
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_MIN, "NoR"));
		else
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_MIN, String.valueOf(getDomMinLoadTime(intervalName, unit))));

		if (getDomMaxLoadTime(intervalName, unit) == Constants.MAX_TIME_DEFAULT)
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_MAX, "NoR"));
		else
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_MAX, String.valueOf(getDomMaxLoadTime(intervalName, unit))));

		builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_AVG, String.valueOf(getAverageDOMLoadTime(intervalName, unit))));
		builder.append(toFormattedStatsString(PageInBrowserStatsValueName.DOM_LAST, String.valueOf(getDomLastLoadTime(intervalName, unit))));

		if (getWindowMinLoadTime(intervalName, unit) == Constants.MIN_TIME_DEFAULT)
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_MIN, "NoR"));
		else
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_MIN, String.valueOf(getWindowMinLoadTime(intervalName, unit))));

		if (getWindowMaxLoadTime(intervalName, unit) == Constants.MAX_TIME_DEFAULT)
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_MAX, "NoR"));
		else
			builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_MAX, String.valueOf(getWindowMaxLoadTime(intervalName, unit))));

		builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_AVG, String.valueOf(getAverageWindowLoadTime(intervalName, unit))));
		builder.append(toFormattedStatsString(PageInBrowserStatsValueName.WIN_LAST, String.valueOf(getWindowLastLoadTime(intervalName, unit))));

		return builder.toString();
	}

	/**
	 * Utility for formatting stats string by incoming {@link PageInBrowserStats.PageInBrowserStatsValueName} and value.
	 *
	 * @param valueName {@link PageInBrowserStats.PageInBrowserStatsValueName}
	 * @param value     string representation of value
	 * @return formatted stats string
	 */
	private String toFormattedStatsString(final PageInBrowserStatsValueName valueName, final String value) {
		return ' ' + valueName.getValueName() + ": " + value;
	}

	@Override
	public String getValueByNameAsString(final String valueName, final String intervalName, final TimeUnit timeUnit) {
		if (StringUtils.isEmpty(valueName))
			throw new AssertionError("Value name can not be empty");

		final PageInBrowserStatsValueName statsValueName = PageInBrowserStatsValueName.getByName(valueName);
		switch (statsValueName) {
			case DOM_MIN:
				return String.valueOf(getDomMinLoadTime(intervalName, timeUnit));
			case DOM_MAX:
				return String.valueOf(getDomMaxLoadTime(intervalName, timeUnit));
			case DOM_AVG:
				return String.valueOf(getAverageDOMLoadTime(intervalName, timeUnit));
			case DOM_LAST:
				return String.valueOf(getDomLastLoadTime(intervalName, timeUnit));
			case WIN_MIN:
				return String.valueOf(getWindowMinLoadTime(intervalName, timeUnit));
			case WIN_MAX:
				return String.valueOf(getWindowMaxLoadTime(intervalName, timeUnit));
			case WIN_AVG:
				return String.valueOf(getAverageWindowLoadTime(intervalName, timeUnit));
			case WIN_LAST:
				return String.valueOf(getWindowLastLoadTime(intervalName, timeUnit));
			case NUMBER_OF_LOADS:
				return String.valueOf(getNumberOfLoads());
			case TOTAL_DOM:
				return String.valueOf(getTotalDomLoadTime(intervalName, timeUnit));
			case TOTAL_WIN:
				return String.valueOf(getTotalWindowLoadTime(intervalName, timeUnit));
			default:
				return super.getValueByNameAsString(valueName, intervalName, timeUnit);
		}
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

	/**
	 * Represents PageInBrowserStats value name.
	 */
	public static enum PageInBrowserStatsValueName {
		/**
		 * DOM minimum load time.
		 */
		DOM_MIN("DomMin"),
		/**
		 * DOM maximum load time.
		 */
		DOM_MAX("DomMax"),
		/**
		 * DOM average load time.
		 */
		DOM_AVG("DOMAvg"),
		/**
		 * DOM last load time.
		 */
		DOM_LAST("DOMLast"),
		/**
		 * Web page minimum load time.
		 */
		WIN_MIN("WinMin"),
		/**
		 * Web page maximum load time.
		 */
		WIN_MAX("WinMax"),
		/**
		 * Web page average load time.
		 */
		WIN_AVG("WinAvg"),
		/**
		 * Web page last load time.
		 */
		WIN_LAST("WinLast"),
		/**
		 * Total DOM loads time.
		 */
		TOTAL_DOM("TotalDOM"),
		/**
		 * Total window loads time.
		 */
		TOTAL_WIN("TotalWindow"),
		/**
		 * Number of requests with loads stats.
		 */
		NUMBER_OF_LOADS("Count"),
		/**
		 * Default value.
		 */
		DEFAULT("default");

		/**
		 * JS stat value name.
		 */
		private String valueName;

		/**
		 * Constructor.
		 *
		 * @param valueName stats value name
		 */
		PageInBrowserStatsValueName(final String valueName) {
			this.valueName = valueName;
		}

		public String getValueName() {
			return valueName;
		}

		/**
		 * Returns {@link PageInBrowserStats.PageInBrowserStatsValueName} by incoming value name.
		 * If value was not found, {@link #DEFAULT} will be returned.
		 *
		 * @param name name of the value
		 * @return {@link PageInBrowserStats.PageInBrowserStatsValueName}
		 */
		public static PageInBrowserStatsValueName getByName(final String name) {
			for (PageInBrowserStatsValueName valueName : PageInBrowserStatsValueName.values())
				if (valueName.name().equalsIgnoreCase(name))
					return valueName;

			return DEFAULT;
		}

		/**
		 * Return collection of value names.
		 *
		 * @return collection of value names
		 */
		public static List<String> getValueNames() {
			List<String> valueNames = new ArrayList<String>(PageInBrowserStatsValueName.values().length);
			for (PageInBrowserStatsValueName valueName : PageInBrowserStatsValueName.values())
				valueNames.add(valueName.getValueName());
			return valueNames;
		}
	}
}
