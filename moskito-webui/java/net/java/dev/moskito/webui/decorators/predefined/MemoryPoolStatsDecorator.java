package net.java.dev.moskito.webui.decorators.predefined;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.predefined.IMemoryPoolStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.LongValueBean;
import net.java.dev.moskito.webui.bean.StatValueBean;
import net.java.dev.moskito.webui.decorators.AbstractDecorator;

/**
 * A decorator for the memory pool stats provided by the VM (young, survivor, oldgen etc).
 * @author another
 *
 */
public class MemoryPoolStatsDecorator extends AbstractDecorator{
	
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"Free",
		"Free MB",
		"Init",
		"Init MB",
		
		"Min Used",
		"Min Used MB",
		"Used",
		"Used MB",
		"Max Used",
		"Max Used MB",

		"Min Commited",
		"Min Commited MB",
		"Commited",
		"Commited MB",
		"Max Commited",
		"Max Commited MB",

		"Max",
		"Max MB",
	};
	/**
	 * Short explanations (mouse-over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Free memory",
		"Free memory in Mb",
		"Initial amount of memory",
		"Initial amount of memory in Mb",
		"Minimum amount of used memory",
		"Minimum amount of used memory in Mb",
		"Amount of used memory",
		"Amount of used memory in Mb",
		"Maximum amount of used memory",
		"Maximum amount of used memory in Mb",
		"Minimum amount of commited memory",
		"Minimum amount of commited memory in Mb",
		"Amount of commited memory",
		"Amount of commited memory in Mb",
		"Maximum amount of commited memory",
		"Maximum amount of commited memory in Mb",
		"Amount of max memory",
		"Amount of max memory in Mb",
	};

	/**
	 * Detailed explanations.
	 */
	private static final String EXPLANATIONS[] = {
		"free memory (commited - used)",
		"free memory (commited - used) in mb",
		"represents the initial amount of memory (in bytes) that the Java virtual machine requests from the operating system for memory management during startup. The Java virtual machine may request additional memory from the operating system and may also release memory to the system over time. The value of init may be undefined. ",
		"represents the initial amount of memory (in megabytes) that the Java virtual machine requests from the operating system for memory management during startup. The Java virtual machine may request additional memory from the operating system and may also release memory to the system over time. The value of init may be undefined. ",
		"Minimum amount of memory ever used (in bytes)",
		"Minimum amount of memory ever used (in megabytes)",
		"The amount of memory currently used (in bytes). ",
		"The amount of memory currently used (in megabytes). ",
		"Maximum amount of memory ever used (in bytes)",
		"Maximum amount of memory ever used (in megabytes)",
		"Minimum amount of memory ever commited (in bytes)",
		"Minimum amount of memory ever commited (in megabytes)",
		"Represents the amount of memory (in bytes) that is guaranteed to be available for use by the Java virtual machine. The amount of committed memory may change over time (increase or decrease). The Java virtual machine may release memory to the system and committed could be less than init. committed will always be greater than or equal to used.  ",
		"Represents the amount of memory (in megabytes) that is guaranteed to be available for use by the Java virtual machine. The amount of committed memory may change over time (increase or decrease). The Java virtual machine may release memory to the system and committed could be less than init. committed will always be greater than or equal to used.  ",
		"Maximum amount of memory ever commited (in bytes)",
		"Maximum amount of memory ever commited (in megabytes)",
		"Represents the amount of memory (in bytes) that is guaranteed to be available for use by the Java virtual machine. The amount of committed memory may change over time (increase or decrease). The Java virtual machine may release memory to the system and committed could be less than init. committed will always be greater than or equal to used.  ",
		"Represents the maximum amount of memory (in megabytes) that can be used for memory management. Its value may be undefined. The maximum amount of memory may change over time if defined. The amount of used and committed memory will always be less than or equal to max if max is defined. A memory allocation may fail if it attempts to increase the used memory such that used > committed even if used <= max would still be true (for example, when the system is low on virtual memory)",
	};

	public MemoryPoolStatsDecorator(){
		this("MemoryPool");
	}
	
	public MemoryPoolStatsDecorator(String aName){
		super(aName, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		IMemoryPoolStats stats = (IMemoryPoolStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getFree(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getFree(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getInit(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getInit(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMinUsed(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMinUsed(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getUsed(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getUsed(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMaxUsed(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMaxUsed(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMinCommited(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMinCommited(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getCommited(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getCommited(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMaxCommited(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMaxCommited(interval)/MB));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMax(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMax(interval)/MB));
		return ret;
	}

}
