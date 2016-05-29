package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.PageInBrowserStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.DOM_AVG;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.DOM_LAST;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.DOM_MAX;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.DOM_MIN;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.DOM_TOTAL;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.NUMBER_OF_LOADS;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.WIN_AVG;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.WIN_LAST;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.WIN_MAX;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.WIN_MIN;
import static net.anotheria.moskito.core.decorators.predefined.PageInBrowserStatsDecorator.PageInBrowserStatsDecoratorValueName.WIN_TOTAL;

/**
 * Decorator for {@link PageInBrowserStats}.
 *
 * @author Illya Bogatyrchuk
 */
public class PageInBrowserStatsDecorator extends AbstractDecorator {
	/**
	 * Captions.
	 */
	private static final String[] CAPTIONS = PageInBrowserStatsDecoratorValueName.getCaptions();
	/**
	 * Short explanations.
	 */
	private static final String[] SHORT_EXPLANATIONS = PageInBrowserStatsDecoratorValueName.getShortExplanations();
	/**
	 * Detailed explanations.
	 */
	private static final String[] EXPLANATIONS = PageInBrowserStatsDecoratorValueName.getExplanations();

	/**
	 * Constructor.
	 */
	public PageInBrowserStatsDecorator() {
		super("PageInBrowserStats", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override
	public List<StatValueAO> getValues(final IStats stats, final String interval, final TimeUnit unit) {
		PageInBrowserStats pageInBrowserStats = (PageInBrowserStats) stats;

		long domMinLoadTime = pageInBrowserStats.getDomMinLoadTime(interval, unit);
		long domMaxLoadTime = pageInBrowserStats.getDomMaxLoadTime(interval, unit);
		double domAverageLoadTime = pageInBrowserStats.getAverageDOMLoadTime(interval, unit);
		long domLastLoadTime = pageInBrowserStats.getDomLastLoadTime(interval, unit);
		long winMinLoadTime = pageInBrowserStats.getWindowMinLoadTime(interval, unit);
		long winMaxLoadTime = pageInBrowserStats.getWindowMaxLoadTime(interval, unit);
		double winAverageLoadTime = pageInBrowserStats.getAverageWindowLoadTime(interval, unit);
		long winLastLoadTime = pageInBrowserStats.getWindowLastLoadTime(interval, unit);
		long totalDomLoadTime = pageInBrowserStats.getTotalDomLoadTime(interval, unit);
		long totalWinLoadTime = pageInBrowserStats.getTotalWindowLoadTime(interval, unit);
		long numberOfLoads = pageInBrowserStats.getNumberOfLoads();

		List<StatValueAO> result = new ArrayList<>(11);
		result.add(new LongValueAO(DOM_MIN.getCaption(), domMinLoadTime));
		result.add(new LongValueAO(DOM_MAX.getCaption(), domMaxLoadTime));
		result.add(new DoubleValueAO(DOM_AVG.getCaption(), domAverageLoadTime));
		result.add(new LongValueAO(DOM_LAST.getCaption(), domLastLoadTime));
		result.add(new LongValueAO(DOM_TOTAL.getCaption(), totalDomLoadTime));
		result.add(new LongValueAO(WIN_MIN.getCaption(), winMinLoadTime));
		result.add(new LongValueAO(WIN_MAX.getCaption(), winMaxLoadTime));
		result.add(new DoubleValueAO(WIN_AVG.getCaption(), winAverageLoadTime));
		result.add(new LongValueAO(WIN_LAST.getCaption(), winLastLoadTime));
		result.add(new LongValueAO(WIN_TOTAL.getCaption(), totalWinLoadTime));
		result.add(new LongValueAO(NUMBER_OF_LOADS.getCaption(), numberOfLoads));
		return result;
	}

	/**
	 * Represents PageInBrowserStats value name.
	 */
	public static enum PageInBrowserStatsDecoratorValueName {
		/**
		 * DOM minimum load time.
		 */
		DOM_MIN(
				"DOMMin",
				"DOM minimum load time",
				"DOM minimum load time"
		),
		/**
		 * DOM maximum load time.
		 */
		DOM_MAX(
				"DOMMax",
				"DOM maximum load time",
				"DOM maximum load time"
		),
		/**
		 * DOM average load time.
		 */
		DOM_AVG(
				"DOMAvg",
				"DOM average load time",
				"DOM average load time"
		),
		/**
		 * DOM last load time.
		 */
		DOM_LAST(
				"DOMLast",
				"DOM last load time",
				"DOM last load time"
		),
		/**
		 * DOM total loads time.
		 */
		DOM_TOTAL(
				"DOMTotal",
				"DOM total loads time",
				"DOM total loads time"
		),
		/**
		 * Web page minimum load time.
		 */
		WIN_MIN(
				"WindowMin",
				"Window minimum load time",
				"Web page minimum load time - including DOM, images, scripts and sub-frames."
		),
		/**
		 * Web page maximum load time.
		 */
		WIN_MAX(
				"WindowMax",
				"Window maximum load time",
				"Web page maximum load time - including DOM, images, scripts and sub-frames."
		),
		/**
		 * Web page average load time.
		 */
		WIN_AVG(
				"WindowAvg",
				"Window average load time",
				"Web page average load time - including DOM, images, scripts and sub-frames."
		),
		/**
		 * Web page last load time.
		 */
		WIN_LAST(
				"WindowLast",
				"Window last load time",
				"Web page average load time - including DOM, images, scripts and sub-frames."
		),
		/**
		 * Web page total loads time.
		 */
		WIN_TOTAL(
				"WindowTotal",
				"Window total loads time",
				"Web page total loads time"
		),
		/**
		 * Total number of page loads.
		 */
		NUMBER_OF_LOADS(
				"Count",
				"Total number of loads",
				"Total number of web page loads"
		);

		/**
		 * JS stat decorator caption name.
		 */
		private String caption;
		/**
		 * JS stat decorator short explanation.
		 */
		private String shortExplanation;
		/**
		 * JS stat decorator full explanation.
		 */
		private String explanation;

		/**
		 * Constructor.
		 *
		 * @param caption          stat caption name
		 * @param shortExplanation stat short explanation
		 * @param explanation      stat full explanation
		 */
		PageInBrowserStatsDecoratorValueName(final String caption, final String shortExplanation, final String explanation) {
			this.caption = caption;
			this.shortExplanation = shortExplanation;
			this.explanation = explanation;
		}

		public String getCaption() {
			return caption;
		}

		public String getShortExplanation() {
			return shortExplanation;
		}

		public String getExplanation() {
			return explanation;
		}

		/**
		 * Return array of captions.
		 *
		 * @return array of captions
		 */
		public static String[] getCaptions() {
			List<String> result = new ArrayList<String>(PageInBrowserStatsDecoratorValueName.values().length);
			for (PageInBrowserStatsDecoratorValueName valueName : PageInBrowserStatsDecoratorValueName.values())
				result.add(valueName.getCaption());
			return result.toArray(new String[result.size()]);
		}

		/**
		 * Return array of short explanations.
		 *
		 * @return array of short explanations
		 */
		public static String[] getShortExplanations() {
			List<String> result = new ArrayList<String>(PageInBrowserStatsDecoratorValueName.values().length);
			for (PageInBrowserStatsDecoratorValueName valueName : PageInBrowserStatsDecoratorValueName.values())
				result.add(valueName.getShortExplanation());
			return result.toArray(new String[result.size()]);
		}

		/**
		 * Return array of explanations.
		 *
		 * @return array of explanations
		 */
		public static String[] getExplanations() {
			List<String> result = new ArrayList<String>(PageInBrowserStatsDecoratorValueName.values().length);
			for (PageInBrowserStatsDecoratorValueName valueName : PageInBrowserStatsDecoratorValueName.values())
				result.add(valueName.getExplanation());
			return result.toArray(new String[result.size()]);
		}
	}
}
