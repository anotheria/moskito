package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for the memory pool stats provided by the VM (young, survivor, oldgen etc).
 * @author another
 *
 */
public class OSStatsDecorator extends AbstractDecorator {
	
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"OpenFiles",
		"MinOpenFiles",
		"MaxOpenFiles",
		"AvailableOpenFiles",
		
		"CPU Time",
		"Total CPU Time",
		"Free Memory",
		"Free Memory MB",
		"Total Memory",
		"Total Memory MB",

		"Processors",
	};
	/**
	 * Short explanations (mouse-over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Open files",
		"Min open files",
		"Max open files",
		"Available open files",
		
		"CPU time in nanos",
		"Total CPU time in nanos",
		"Free Memory",
		"Free Memory MB",
		"Total Memory",
		"Total Memory MB",

		"Processors",
	};

	/**
	 * Detailed explanations.
	 */
	private static final String EXPLANATIONS[] = {
		"Currently open file descriptors by the application",
		"Min open file descriptors by the application",
		"Max open file descriptors by the application",
		"Max available file descriptors to be opened by the application",
		
		"cpu time in nanoseconds used by the system",
		"total cpu time in nanoseconds used by the system",
		"Free physical memory in the system",
		"Free physical memory in the system in MB",
		"Total physical memory in the system (constant)",
		"Total physical memory in the system in MB (constant)",

		"Number of processors in the system  (constant)",
	};

	public OSStatsDecorator(){
		this("OS");
	}
	
	public OSStatsDecorator(String aName){
		super(aName, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	@Override public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
		OSStats stats = (OSStats)statsObject;

		List<StatValueAO> ret = new ArrayList<StatValueAO>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getOpenFiles(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getMinOpenFiles(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getMaxOpenFiles(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getMaxSupportedOpenFiles(interval)));

		ret.add(new LongValueAO(CAPTIONS[i++], unit.transformNanos(stats.getProcessCPUTime(interval))));
		ret.add(new LongValueAO(CAPTIONS[i++], unit.transformNanos(stats.getProcessTotalCPUTime(interval))));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getFreePhysicalMemory(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getFreePhysicalMemory(interval)/MB));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getTotalPhysicalMemory(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getTotalPhysicalMemory(interval)/MB));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getProcessors(interval)));
		
		return ret;
	}

}
