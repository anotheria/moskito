package net.java.dev.moskito.core.util;

import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.predefined.MemoryPoolStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.timing.timer.ITimerConsumer;
import net.java.dev.moskito.core.timing.timer.TimerServiceConstantsUtility;
import net.java.dev.moskito.core.timing.timer.TimerServiceFactory;

/**
 * Builtin producer for memory pool monitoring. Each producer monitors one memory pool. Memory pools are garbage collector dependent and are determined via jmx on start.
 * @author dvayanu
 */
public class BuiltInMemoryPoolProducer implements IStatsProducer, ITimerConsumer{
	/**
	 * The id of the producers. Usually its the name of the pool.
	 */
	private String producerId;
	/**
	 * Associated stats.
	 */
	private MemoryPoolStats stats;
	/**
	 * Stats container
	 */
	private List<IStats> statsList;

	/**
	 * The monitored pool.
	 */
	private MemoryPoolMXBean pool;
	
	/**
	 * Creates a new producers object for a given pool.
	 * @param aPool
	 */
	public BuiltInMemoryPoolProducer(MemoryPoolMXBean aPool){
		pool = aPool;
		producerId = "MemoryPool-"+pool.getName()+"-"+(pool.getType()==MemoryType.HEAP? "Heap" : "NonHeap");
		statsList = new ArrayList<IStats>();
		stats = new MemoryPoolStats(producerId);
		statsList.add(stats);
		
		
		TimerServiceFactory.createTimerService().addConsumer(this, 1000/TimerServiceConstantsUtility.getSleepingUnit()*60);
		//force initial memory reading
		receiveTimerEvent(0);
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
	
	@Override
	public void receiveTimerEvent(int ticks) {
		MemoryUsage usage = pool.getUsage();
		stats.setCommited(usage.getCommitted());
		stats.setUsed(usage.getUsed());
		stats.setInit(usage.getInit());
		stats.setMax(usage.getMax());
	}
	
	/**
	 * This method is used internally for virtual producers / stats.
	 * @return
	 */
	MemoryPoolStats getMemoryPoolStats(){
		return stats;
	}
}

