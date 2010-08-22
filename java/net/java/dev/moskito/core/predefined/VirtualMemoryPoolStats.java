package net.java.dev.moskito.core.predefined;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.TimeUnit;

/**
 * Stats for virtual pools, i.e. Heap and Non-Heap memory, which consists of multiple underlying pools.
 * The VirtualMemoryPoolStats do not measure theirself, but just aggregate.
 * @author another
 *
 */
public class VirtualMemoryPoolStats extends AbstractStats implements IMemoryPoolStats{
	
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
		realStats = new ArrayList<MemoryPoolStats>();
	}
	
	/**
	 * Adds an underlying 'real' stats object.
	 * @param stats
	 */
	public void addStats(MemoryPoolStats stats){
		realStats.add(stats);
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" INIT: ").append(getInit(intervalName));
		b.append(" MIN USED: ").append(getMinUsed(intervalName));
		b.append(" USED: ").append(getUsed(intervalName));
		b.append(" MAX USED: ").append(getMaxUsed(intervalName));
		b.append(" MIN COMMITED: ").append(getMinCommited(intervalName));
		b.append(" COMMITED: ").append(getCommited(intervalName));
		b.append(" MAX COMMITED: ").append(getMaxCommited(intervalName));
		b.append(" MAX: ").append(getMax(intervalName));
		return b.toString();
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


