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
public class JSStats extends AbstractStats {
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
	 * The number of total requests with DOM loads stats.
	 */
	private StatValue totalDomLoads;
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
	 * The number of total requests with page loads stats.
	 */
	private StatValue totalWindowLoads;
	/**
	 * Total web page load time.
	 */
	private StatValue totalWindowLoadTime;
	/**
	 * Available value names.
	 */
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(JSStatsValueName.getValueNames());

	/**
	 * Constructor.
	 *
	 * @param urlPath           page's url
	 * @param selectedIntervals array of {@link Interval}
	 */
	public JSStats(final String urlPath, final Interval[] selectedIntervals) {
		url = urlPath;

		final long pattern = 0L;
		final Interval[] intervals = Arrays.copyOf(selectedIntervals, selectedIntervals.length);

		domMinLoadTime = StatValueFactory.createStatValue(pattern, "domMinLoadTime", intervals);
		domMaxLoadTime = StatValueFactory.createStatValue(pattern, "domMaxLoadTime", intervals);
		domLastLoadTime = StatValueFactory.createStatValue(pattern, "domLastLoadTime", intervals);
		totalDomLoads = StatValueFactory.createStatValue(pattern, "totalDomLoads", intervals);
		totalDomLoadTime = StatValueFactory.createStatValue(pattern, "totalDomLoadTime", intervals);
		windowMinLoadTime = StatValueFactory.createStatValue(pattern, "windowMinLoadTime", intervals);
		windowMaxLoadTime = StatValueFactory.createStatValue(pattern, "windowMaxLoadTime", intervals);
		windowLastLoadTime = StatValueFactory.createStatValue(pattern, "windowLastLoadTime", intervals);
		totalWindowLoads = StatValueFactory.createStatValue(pattern, "totalWindowLoads", intervals);
		totalWindowLoadTime = StatValueFactory.createStatValue(pattern, "totalWindowLoadTime", intervals);

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
	 * Adds DOM load time to the stats.
	 *
	 * @param loadTime DOM load time
	 */
	public void addDOMLoadTime(final long loadTime) {
		totalDomLoadTime.increaseByLong(loadTime);
		domMinLoadTime.setValueIfLesserThanCurrentAsLong(loadTime);
		domMaxLoadTime.setValueIfGreaterThanCurrentAsLong(loadTime);
		domLastLoadTime.setValueAsLong(loadTime);
		totalDomLoads.increase();
	}

	/**
	 * Adds web page load time to the stats.
	 *
	 * @param loadTime web page load time
	 */
	public void addWindowLoadTime(final long loadTime) {
		totalWindowLoadTime.increaseByLong(loadTime);
		windowMinLoadTime.setValueIfLesserThanCurrentAsLong(loadTime);
		windowMaxLoadTime.setValueIfGreaterThanCurrentAsLong(loadTime);
		windowLastLoadTime.setValueAsLong(loadTime);
		totalWindowLoads.increase();
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
		return unit.transformMillis(totalDomLoadTime.getValueAsLong(intervalName)) / totalDomLoads.getValueAsDouble(intervalName);
	}

	/**
	 * Returns the average web page load time for given interval and {@link TimeUnit}.
	 *
	 * @param intervalName name of the interval
	 * @param unit         {@link TimeUnit}
	 * @return average web page load time
	 */
	public double getAverageWindowLoadTime(final String intervalName, final TimeUnit unit) {
		return unit.transformMillis(totalWindowLoadTime.getValueAsLong(intervalName)) / totalWindowLoads.getValueAsDouble(intervalName);
	}

	@Override
	public String toStatsString(final String intervalName, final TimeUnit unit) {
		StringBuilder builder = new StringBuilder();
		builder.append(url);

		if (getDomMinLoadTime(intervalName, unit) == Constants.MIN_TIME_DEFAULT)
			builder.append(toFormattedStatsString(JSStatsValueName.DOM_MIN, "NoR"));
		else
			builder.append(toFormattedStatsString(JSStatsValueName.DOM_MIN, String.valueOf(getDomMinLoadTime(intervalName, unit))));

		if (getDomMaxLoadTime(intervalName, unit) == Constants.MAX_TIME_DEFAULT)
			builder.append(toFormattedStatsString(JSStatsValueName.DOM_MAX, "NoR"));
		else
			builder.append(toFormattedStatsString(JSStatsValueName.DOM_MAX, String.valueOf(getDomMaxLoadTime(intervalName, unit))));

		builder.append(toFormattedStatsString(JSStatsValueName.DOM_AVG, String.valueOf(getAverageDOMLoadTime(intervalName, unit))));
		builder.append(toFormattedStatsString(JSStatsValueName.DOM_LAST, String.valueOf(getDomLastLoadTime(intervalName, unit))));

		if (getWindowMinLoadTime(intervalName, unit) == Constants.MIN_TIME_DEFAULT)
			builder.append(toFormattedStatsString(JSStatsValueName.WIN_MIN, "NoR"));
		else
			builder.append(toFormattedStatsString(JSStatsValueName.WIN_MIN, String.valueOf(getWindowMinLoadTime(intervalName, unit))));

		if (getWindowMaxLoadTime(intervalName, unit) == Constants.MAX_TIME_DEFAULT)
			builder.append(toFormattedStatsString(JSStatsValueName.WIN_MAX, "NoR"));
		else
			builder.append(toFormattedStatsString(JSStatsValueName.WIN_MAX, String.valueOf(getWindowMaxLoadTime(intervalName, unit))));

		builder.append(toFormattedStatsString(JSStatsValueName.WIN_AVG, String.valueOf(getAverageWindowLoadTime(intervalName, unit))));
		builder.append(toFormattedStatsString(JSStatsValueName.WIN_LAST, String.valueOf(getWindowLastLoadTime(intervalName, unit))));

		return builder.toString();
	}

	/**
	 * Utility for formatting stats string by incoming {@link JSStatsValueName} and value.
	 *
	 * @param valueName {@link JSStatsValueName}
	 * @param value     string representation of value
	 * @return formatted stats string
	 */
	private String toFormattedStatsString(final JSStatsValueName valueName, final String value) {
		return " " + valueName.getValueName() + ": " + value;
	}

	@Override
	public String getValueByNameAsString(final String valueName, final String intervalName, final TimeUnit timeUnit) {
		if (StringUtils.isEmpty(valueName))
			throw new AssertionError("Value name can not be empty");

		final JSStatsValueName jsStatsValueName = JSStatsValueName.getByName(valueName);
		switch (jsStatsValueName) {
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
			default:
				return super.getValueByNameAsString(valueName, intervalName, timeUnit);
		}
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

	/**
	 * Represents JSStat value name.
	 */
	public static enum JSStatsValueName {
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
		JSStatsValueName(final String valueName) {
			this.valueName = valueName;
		}

		public String getValueName() {
			return valueName;
		}

		/**
		 * Returns {@link JSStats.JSStatsValueName} by incoming value name.
		 * If value was not found, {@link #DEFAULT} will be returned.
		 *
		 * @param name name of the value
		 * @return {@link JSStats.JSStatsValueName}
		 */
		public static JSStatsValueName getByName(final String name) {
			for (JSStatsValueName valueName : JSStatsValueName.values())
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
			List<String> valueNames = new ArrayList<String>(JSStatsValueName.values().length);
			for (JSStatsValueName valueName : JSStatsValueName.values())
				valueNames.add(valueName.getValueName());
			return valueNames;
		}
	}
}
