package net.java.dev.moskito.core.predefined;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.TimeUnit;

public class VirtualMemoryPoolStats extends AbstractStats implements IMemoryPoolStats{
	
	private List<MemoryPoolStats> realStats;
	
	public VirtualMemoryPoolStats(){
		this("unnamed", Constants.DEFAULT_INTERVALS);
	} 
	
	public VirtualMemoryPoolStats(String aName){
		this(aName, Constants.DEFAULT_INTERVALS);
	} 

	public VirtualMemoryPoolStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		realStats = new ArrayList<MemoryPoolStats>();
	}
	
	public void addStats(MemoryPoolStats stats){
		realStats.add(stats);
	}
	
	public String toStatsString(String intervalName, TimeUnit timeUnit) {
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

	public long getInit(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getInit(intervalName);
		}
		return ret;
	}
	
	public long getUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getUsed(intervalName);
		}
		return ret;
	}
	public long getMinUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMinUsed(intervalName);
		}
		return ret;
	}
	public long getMaxUsed(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMaxUsed(intervalName);
		}
		return ret;
	}

	
	
	public long getCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getCommited(intervalName);
		}
		return ret;
	}
	
	public long getMinCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMinCommited(intervalName);
		}
		return ret;
	}
	public long getMaxCommited(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMaxCommited(intervalName);
		}
		return ret;
	}
	
	
	public long getMax(String intervalName){
		long ret = 0L;
		for (MemoryPoolStats s : realStats){
			ret += s.getMax(intervalName);
		}
		return ret;
	}
	
	public long getFree(String intervalName){
		return getCommited(intervalName) - getUsed(intervalName);
	}

}


