package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.predefined.BrowserStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;
import net.anotheria.moskito.webui.producers.api.DoubleValueAO;
import net.anotheria.moskito.webui.producers.api.LongValueAO;
import net.anotheria.moskito.webui.producers.api.StatValueAO;

import java.util.ArrayList;
import java.util.List;

import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.DOM_AVG;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.DOM_LAST;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.DOM_MAX;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.DOM_MIN;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.WIN_AVG;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.WIN_LAST;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.WIN_MAX;
import static net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator.BrowserStatsDecoratorValueName.WIN_MIN;

/**
 * Decorator for {@link BrowserStats}.
 *
 * @author Illya Bogatyrchuk
 */
public class BrowserStatsDecorator extends AbstractDecorator {
	/**
	 * Captions.
	 */
	private static final String[] CAPTIONS = BrowserStatsDecoratorValueName.getCaptions();
	/**
	 * Short explanations.
	 */
	private static final String[] SHORT_EXPLANATIONS = BrowserStatsDecoratorValueName.getShortExplanations();
	/**
	 * Detailed explanations.
	 */
	private static final String[] EXPLANATIONS = BrowserStatsDecoratorValueName.getExplanations();

	/**
	 * Constructor.
	 */
	public BrowserStatsDecorator() {
		super("BrowserStats", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override
	public List<StatValueAO> getValues(final IStats stats, final String interval, final TimeUnit unit) {
		BrowserStats browserStats = (BrowserStats) stats;

		long domMinLoadTime = browserStats.getDomMinLoadTime(interval, unit);
		long domMaxLoadTime = browserStats.getDomMaxLoadTime(interval, unit);
		double domAverageLoadTime = browserStats.getAverageDOMLoadTime(interval, unit);
		long domLastLoadTime = browserStats.getDomLastLoadTime(interval, unit);
		long winMinLoadTime = browserStats.getWindowMinLoadTime(interval, unit);
		long winMaxLoadTime = browserStats.getWindowMaxLoadTime(interval, unit);
		double winAverageLoadTime = browserStats.getAverageWindowLoadTime(interval, unit);
		long winLastLoadTime = browserStats.getWindowLastLoadTime(interval, unit);

		List<StatValueAO> result = new ArrayList<StatValueAO>();
		result.add(new LongValueAO(DOM_MIN.getCaption(), domMinLoadTime));
		result.add(new LongValueAO(DOM_MAX.getCaption(), domMaxLoadTime));
		result.add(new DoubleValueAO(DOM_AVG.getCaption(), domAverageLoadTime));
		result.add(new LongValueAO(DOM_LAST.getCaption(), domLastLoadTime));
		result.add(new LongValueAO(WIN_MIN.getCaption(), winMinLoadTime));
		result.add(new LongValueAO(WIN_MAX.getCaption(), winMaxLoadTime));
		result.add(new DoubleValueAO(WIN_AVG.getCaption(), winAverageLoadTime));
		result.add(new LongValueAO(WIN_LAST.getCaption(), winLastLoadTime));
		return result;
	}

	/**
	 * Represents BrowserStats value name.
	 */
	public static enum BrowserStatsDecoratorValueName {
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
		BrowserStatsDecoratorValueName(final String caption, final String shortExplanation, final String explanation) {
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
			List<String> result = new ArrayList<String>(BrowserStatsDecoratorValueName.values().length);
			for (BrowserStatsDecoratorValueName valueName : BrowserStatsDecoratorValueName.values())
				result.add(valueName.getCaption());
			return result.toArray(new String[result.size()]);
		}

		/**
		 * Return array of short explanations.
		 *
		 * @return array of short explanations
		 */
		public static String[] getShortExplanations() {
			List<String> result = new ArrayList<String>(BrowserStatsDecoratorValueName.values().length);
			for (BrowserStatsDecoratorValueName valueName : BrowserStatsDecoratorValueName.values())
				result.add(valueName.getShortExplanation());
			return result.toArray(new String[result.size()]);
		}

		/**
		 * Return array of explanations.
		 *
		 * @return array of explanations
		 */
		public static String[] getExplanations() {
			List<String> result = new ArrayList<String>(BrowserStatsDecoratorValueName.values().length);
			for (BrowserStatsDecoratorValueName valueName : BrowserStatsDecoratorValueName.values())
				result.add(valueName.getExplanation());
			return result.toArray(new String[result.size()]);
		}
	}
}
