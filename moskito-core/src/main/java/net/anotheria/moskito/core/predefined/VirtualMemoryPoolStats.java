package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Stats for virtual pools, i.e. Heap and Non-Heap memory, which consists of multiple underlying pools.
 * The VirtualMemoryPoolStats do not measure themself, they just aggregate values provided by JMX Beans.
 * @author lrosenberg
 *
 */
public class VirtualMemoryPoolStats extends AbstractMemoryPoolStats implements IMemoryPoolStats{
	
	/**
	 * Underlying 'real' stats.
	 */
	private List<MemoryPoolStats> realStats;
	
	/**
	 * Creates a new VirtualMemoryPoolStats object.
	 */
	public VirtualMemoryPoolStats(){
		this("unnamed", Constants.getDefaultIntervals());
	} 
	
	/**
	 * Creates a new VirtualMemoryPoolStats object with given name.
	 */
	public VirtualMemoryPoolStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	} 

	/**
	 * Creates a new VirtualMemoryPoolStats object with given name and special intervals.
	 */
	public VirtualMemoryPoolStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		realStats = new ArrayList<>();
	}
	
	/**
	 * Adds an underlying 'real' stats object.
	 * @param stats
	 */
	public void addStats(MemoryPoolStats stats){
		realStats.add(stats);
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
        String b = getName() + ' ' +
                " INIT: " + getInit(intervalName) +
                " MIN USED: " + getMinUsed(intervalName) +
                " USED: " + getUsed(intervalName) +
                " MAX USED: " + getMaxUsed(intervalName) +
                " MIN COMMITED: " + getMinCommited(intervalName) +
                " COMMITED: " + getCommited(intervalName) +
                " MAX COMMITED: " + getMaxCommited(intervalName) +
                " MAX: " + getMax(intervalName);
        return b;
	}

	@Override public long getInit(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getInit(intervalName);
		}
		return ret;
	}
	
	@Override public long getUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getUsed(intervalName);
		}
		return ret;
	}
	@Override public long getMinUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMinUsed(intervalName);
		}
		return ret;
	}
	@Override public long getMaxUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMaxUsed(intervalName);
		}
		return ret;
	}

	
	
	@Override public long getCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getCommited(intervalName);
		}
		return ret;
	}
	
	@Override public long getMinCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMinCommited(intervalName);
		}
		return ret;
	}
	@Override public long getMaxCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMaxCommited(intervalName);
		}
		return ret;
	}
	
	
	@Override public long getMax(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMax(intervalName);
		}
		return ret;
	}
	
	@Override public long getFree(String intervalName){
		return getCommited(intervalName) - getUsed(intervalName);
	}

}


