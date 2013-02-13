package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.predefined.ThreadCountStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * This decorator is used for presentation of the thread count producer.
 * @author lrosenberg.
 *
 */
public class ThreadCountDecorator extends AbstractDecorator {
	/**
	 * Captions for the values.
	 */
	private static final String CAPTIONS[] = {
		"Started",
		"Min Cur",
		"Current",
		"Max Cur",
		"Daemon",
	};
	
	/**
	 * Short explanations of the values.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Total number of started threads",
		"Min number of currently running threads",
		"Number of currently running threads",
		"Max number of currently running threads",
		"Total number of started daemon threads",
	};


	/**
	 * Explanations of the values.
	 */
	private static final String EXPLANATIONS[] = {
		"Total number of started threads",
		"Min number of currently running threads",
		"Number of currently running threads",
		"Max number of currently running threads",
		"Total number of started daemon threads",
	};

	
	/**
	 * Creates a new ThreadCountDecorator.
	 */
	public ThreadCountDecorator(){
		super("ThreadCount", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		ThreadCountStats stats = (ThreadCountStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		
		int i = 0;
		
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getStarted(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMinCurrent(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getCurrent(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMaxCurrent(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getDaemon(interval)));
		
		return ret;
	}
}
