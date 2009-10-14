package net.java.dev.moskito.core.util;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.predefined.MemoryStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.timing.timer.ITimerConsumer;
import net.java.dev.moskito.core.timing.timer.TimerServiceConstantsUtility;
import net.java.dev.moskito.core.timing.timer.TimerServiceFactory;

/**
 * A builtin memory producer for Runtime.get... memory methods. 
 * @author lrosenberg
 *
 */
public class BuiltInMemoryProducer implements IStatsProducer, ITimerConsumer{
	/**
	 * The id of the producer.
	 */
	private String producerId;
	/**
	 * Stats.
	 */
	private MemoryStats stats;
	/**
	 * Cached stats list.
	 */
	private List<IStats> statsList;
	/**
	 * The resolver for memory values reading.
	 */
	private RuntimeMemoryResolver resolver;
	
	public static final String FREE = "JavaRuntimeFree";
	public static final String MAX = "JavaRuntimeMax";
	public static final String TOTAL = "JavaRuntimeTotal";
	
	public BuiltInMemoryProducer(String aProducerId){
		statsList = new ArrayList<IStats>();
		stats = new MemoryStats(aProducerId);
		statsList.add(stats);
		
		producerId = aProducerId;
		if (FREE.equals(aProducerId))
			resolver = new FreeMemoryResolver();
		if (MAX.equals(aProducerId))
			resolver = new MaxMemoryResolver();
		if (TOTAL.equals(aProducerId))
			resolver = new TotalMemoryResolver();
		
		if (resolver==null)
			throw new IllegalArgumentException("Illegal producerId, expected: "+FREE+", "+TOTAL+" or "+MAX);
		
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
	
	/**
	 * Resolver interface for different subtypes of memory usage (free, total, max).
	 * @author another
	 *
	 */
	private interface RuntimeMemoryResolver {
		public long getMemoryValue();
	}

	
	private class FreeMemoryResolver implements RuntimeMemoryResolver{
		public long getMemoryValue(){
			return Runtime.getRuntime().freeMemory();
		}
	}
	private class MaxMemoryResolver implements RuntimeMemoryResolver{
		public long getMemoryValue(){
			return Runtime.getRuntime().maxMemory();
		}
	}
	private class TotalMemoryResolver implements RuntimeMemoryResolver{
		public long getMemoryValue(){
			return Runtime.getRuntime().totalMemory();
		}
	}
	@Override
	public void receiveTimerEvent(int ticks) {
		stats.updateMemoryValue(resolver.getMemoryValue());
	}
}

