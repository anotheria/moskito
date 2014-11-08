package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.predefined.JSStats;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;
import net.anotheria.moskito.webui.producers.api.LongValueAO;
import net.anotheria.moskito.webui.producers.api.StatValueAO;
import net.anotheria.moskito.webui.producers.api.StringValueAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description!!!!!! Change this!
 *
 * @author Illya Bogatyrchuk
 */
public class JSStatsDecorator extends AbstractDecorator {
	private static final String CAPTIONS[] = {
			"URL",
			"DOM",
			"WINDOW"
	};
	private static final String SHORT_EXPLANATIONS[] = {
			"Url",
			"Dom",
			"Window"
	};

	private static final String EXPLANATIONS[] = {
			"Url path",
			"Dom load time",
			"Window load time"
	};
	public JSStatsDecorator() {
		super("JsStats", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override
	public List<StatValueAO> getValues(IStats stats, String interval, TimeUnit unit) {
		JSStats jsStats = (JSStats) stats;
		long domLoadTime = jsStats.getDomLoadTime(interval);
		long windowLoadTime =jsStats.getWindowLoadTime(interval);
		String url = jsStats.getUrl();
		List<StatValueAO> result = new ArrayList<StatValueAO>();
		result.add(new StringValueAO(CAPTIONS[0], url));
		result.add(new LongValueAO(CAPTIONS[1], domLoadTime));
		result.add(new LongValueAO(CAPTIONS[2], windowLoadTime));
		return result;
	}
}
