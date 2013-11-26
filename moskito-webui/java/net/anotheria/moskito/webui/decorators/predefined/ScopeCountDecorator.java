package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.integration.cdi.scopes.ScopeCountStats;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for session count stats.
 */
public class ScopeCountDecorator extends AbstractDecorator {
	/**
	 * Captions.
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
		"Current number of instances",
		"Min number of instances",
		"Max number of instances",
		"Number of created instances",
		"Number of deleted instances",
	};


	/**
	 * Explanations for the help page.
	 */
	private static final String EXPLANATIONS[] = {
		"Current number of instances (this feature is yet experimental)",
		"Min number of instances (this feature is yet experimental)",
		"Max number of instances (this feature is yet experimental)",
		"Number of created instances (this feature is yet experimental)",
		"Number of deleted instances (this feature is yet experimental)",
	};


	public ScopeCountDecorator(){
		super("CDI-ScopeInstanceCount", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		ScopeCountStats stats = (ScopeCountStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		
		int i = 0;
		
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getCurrentInstanceCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getMinInstanceCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getMaxInstanceCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getCreatedInstanceCount(interval))));
		ret.add(new LongValueBean(CAPTIONS[i++], mapToLong(stats.getDestroyedInstanceCount(interval))));
		
		return ret;
	}
	
	private static final long mapToLong(int value){
		return (value == Integer.MAX_VALUE) ? Long.MAX_VALUE : 
			   ((value == Integer.MIN_VALUE) ? Long.MIN_VALUE :
				   value);
	}
	
	
}
