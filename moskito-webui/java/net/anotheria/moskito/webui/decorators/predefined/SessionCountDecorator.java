package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.web.session.SessionCountStats;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for session count stats.
 */
public class SessionCountDecorator extends AbstractDecorator {
	/**
	 * Catpions.
	 */
	private static final String CAPTIONS[] = {
		"Cur",
		"Min",
		"Max",
		"New",
		"Del",
	};

	/**
	 * Short explanations for the tooltip.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Current number of sessions",
		"Min number of sessions",
		"Max number of sessions",
		"Number of created sessions",
		"Number of deleted sessions",
	};


	/**
	 * Explanations for the help page.
	 */
	private static final String EXPLANATIONS[] = {
		"Current number of sessions (this feature is yet experimental)",
		"Min number of sessions (this feature is yet experimental)",
		"Max number of sessions (this feature is yet experimental)",
		"Number of created sessions (this feature is yet experimental)",
		"Number of deleted sessions (this feature is yet experimental)",
	};

	
	public SessionCountDecorator(){
		super("HttpSession", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		SessionCountStats stats = (SessionCountStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		
		int i = 0;
		
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getCurrentSessionCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getMinSessionCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getMaxSessionCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getCreatedSessionCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getDestroyedSessionCount(interval))));
		
		return ret;
	}
	
	private static final long mapToLong(int value){
		return (value == Integer.MAX_VALUE) ? Long.MAX_VALUE : 
			   ((value == Integer.MIN_VALUE) ? Long.MIN_VALUE :
				   value);
	}
	
	
}
