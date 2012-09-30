package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.predefined.RuntimeStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.bean.LongValueBean;
import net.anotheria.moskito.webui.bean.StatValueBean;
import net.anotheria.moskito.webui.bean.StringValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;
import net.anotheria.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for the memory pool stats provided by the VM (young, survivor, oldgen etc).
 * @author another
 *
 */
public class RuntimeStatsDecorator extends AbstractDecorator {
	
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"Name",
		"StartTime",
		"Uptime",
		"StartDate",
		"Uphours",
		"Updays",
	};
	/**
	 * Short explanations (mouse-over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Name of the process",
		"StartTime in ms",
		"Uptime in ms",
		"StartDate as ISO8601",
		"Uphours",
		"Updays",
	};

	/**
	 * Detailed explanations.
	 */
	private static final String EXPLANATIONS[] = {
		"Name of the process",
		"Starttime of the process in ms since 1970",
		"Uptime in ms since start",
		"StartDate as ISO8601",
		"Uphours",
		"Updays",
	};

	public RuntimeStatsDecorator(){
		this("Runtime");
	}
	
	public RuntimeStatsDecorator(String aName){
		super(aName, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		RuntimeStats stats = (RuntimeStats)statsObject;

		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		long starttime = stats.getStartTime(interval);
		long uptime = stats.getUptime(interval);
		int i = 0;
		ret.add(new StringValueBean(CAPTIONS[i++], stats.getProcessName(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], starttime));
		ret.add(new LongValueBean(CAPTIONS[i++], uptime));
		ret.add(new StringValueBean(CAPTIONS[i++], NumberUtils.makeISO8601TimestampString(starttime)));
		ret.add(new LongValueBean(CAPTIONS[i++], uptime/1000/60/60));
		ret.add(new StringValueBean(CAPTIONS[i++], ""+(float)(uptime*100/1000/60/60/24)/100));
		
		return ret;
	}

}
