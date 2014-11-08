package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.anotheria.moskito.core.predefined.Constants.getDefaultIntervals;

/**
 * Class description!!!!!! Change this!
 *
 * @author Illya Bogatyrchuk
 */
public class JSStats extends AbstractStats {
	private final String URL = "url";
	private final String DOM = "dom";
	private final String WINDOW = "window";

	/**
	 * All currently selected intervals.
	 */
	private transient Interval selectedIntervals[];

	private String url;
	private StatValue domLoadTime;
	private StatValue windowLoadTime;

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"URL",
			"DOM",
			"WINDOW"
	));

	public JSStats(String url/*, long dom, long window*/) {
		this(url/*, dom, window*/, getDefaultIntervals());
	}

	public JSStats(String url, /*long dom, long window,*/ Interval[] selectedIntervals) {
		this.url  = url;
		this.domLoadTime = StatValueFactory.createStatValue(0L, "domLoadTime", selectedIntervals );
		this.windowLoadTime = StatValueFactory.createStatValue(0L, "windowLoadTime", selectedIntervals);
	}

	public void setDomLoadTime(long domLoadTime) {
		this.domLoadTime.setValueAsLong(domLoadTime);
	}

	public void setWindowLoadTime(long windowLoadTime) {
		this.windowLoadTime.setValueAsLong(windowLoadTime);
	}

	//		@Override
	public String describeForWebUI() {
		return "JSStats";
	}

	public long getDomLoadTime(String intervalName){
		return domLoadTime.getValueAsLong(intervalName);
	}

	public long getWindowLoadTime(String intervalName){
		return windowLoadTime.getValueAsLong(intervalName);
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toStatsString(String aIntervalName, TimeUnit unit) {
		StringBuilder builder = new StringBuilder();
		builder.append("URK: ").append(url);
		builder.append("DOM: ").append(domLoadTime);
		builder.append("WINDOW: ").append(windowLoadTime);
		return builder.toString();
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}
}
