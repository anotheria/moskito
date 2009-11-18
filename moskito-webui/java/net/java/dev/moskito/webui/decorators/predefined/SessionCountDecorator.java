package net.java.dev.moskito.webui.decorators.predefined;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.web.session.SessionCountStats;
import net.java.dev.moskito.webui.bean.LongValueBean;
import net.java.dev.moskito.webui.bean.StatValueBean;
import net.java.dev.moskito.webui.decorators.AbstractDecorator;

public class SessionCountDecorator extends AbstractDecorator{
	private static final String CAPTIONS[] = {
		"Cur",
		"Min",
		"Max",
		"New",
		"Del",
	};
	
	private static final String SHORT_EXPLANATIONS[] = {
		"Current number of sessions",
		"Min number of sessions",
		"Max number of sessions",
		"Number of created sessions",
		"Number of deleted sessions",
	};


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
