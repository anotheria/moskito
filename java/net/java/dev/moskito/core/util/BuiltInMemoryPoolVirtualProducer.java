package net.java.dev.moskito.core.util;

import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.predefined.VirtualMemoryPoolStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * Built in producer for monitoring of virtual memory pools, which are combination of pools based on memory type, like: all heap pools, all non-heap pools.
 * @author another
 */
public class BuiltInMemoryPoolVirtualProducer implements IStatsProducer{
	/**
	 * The id of the producer. Usually its the name of the memory type.
	 */
	private String producerId;
	/**
	 * Internal stats object.
	 */
	private VirtualMemoryPoolStats stats;
	/**
	 * Stats container / collection.
	 */
	private List<IStats> statsList;

	/**
	 * Creates a new producer for given memory type and a list of producers the monitor pools of that types.
	 * @param type
	 * @param producers
	 */
	public BuiltInMemoryPoolVirtualProducer(MemoryType type, List<BuiltInMemoryPoolProducer> producers){
		producerId = type.toString();
		statsList = new ArrayList<IStats>();
		stats = new VirtualMemoryPoolStats(producerId);
		for (BuiltInMemoryPoolProducer producer : producers)
			stats.addStats(producer.getMemoryPoolStats());
		statsList.add(stats);
		
		
	}
	
	@Override
	public String getCategory() {
		return "memory";
	}

	@Override
	public String getProducerId() {
		return producerId;
	}

	@Override
	public List<IStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}

}

