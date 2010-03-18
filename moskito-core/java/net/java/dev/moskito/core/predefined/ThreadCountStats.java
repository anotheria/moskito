package net.java.dev.moskito.core.predefined;

import static net.java.dev.moskito.core.predefined.Constants.DEFAULT_INTERVALS;
import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

public class ThreadCountStats extends AbstractStats{
	/**
	 * Current number of sessions.
	 */
	private StatValue started;
	/**
	 * Current number of sessions.
	 */
	private StatValue current;
	/**
	 * Current number of sessions.
	 */
	private StatValue daemon;
	/**
	 * Current number of sessions.
	 */
	private StatValue minCurrent;
	/**
	 * Current number of sessions.
	 */
	private StatValue maxCurrent;
	
	
	public ThreadCountStats() {
		this(DEFAULT_INTERVALS);
	}
	
	public ThreadCountStats(Interval[] intervals){
		super("Sessions");
		
		Long pattern = Long.valueOf(0);
		
		current = StatValueFactory.createStatValue(pattern, "current", intervals); 
		minCurrent = StatValueFactory.createStatValue(pattern, "minCurrent", intervals);
		minCurrent.setDefaultValueAsInt(Integer.MAX_VALUE);
		minCurrent.reset();
		maxCurrent = StatValueFactory.createStatValue(pattern, "maxCurrent", intervals); 
		maxCurrent.setDefaultValueAsInt(Integer.MIN_VALUE);
		maxCurrent.reset();

		started = StatValueFactory.createStatValue(pattern, "started", intervals); 
		daemon = StatValueFactory.createStatValue(pattern, "daemon", intervals); 

	}
	
	public void update(long aStarted, long aDaemon, long aCurrent){
		current.setValueAsLong(aCurrent);
		started.setValueAsLong(aStarted);
		daemon.setValueAsLong(aDaemon);
		
		minCurrent.setValueIfLesserThanCurrentAsLong(aCurrent);
		maxCurrent.setValueIfGreaterThanCurrentAsLong(aCurrent);
	}
	

	@Override public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder("Sessions ");
		ret.append(" Current: ").append(current.getValueAsInt(intervalName));
		ret.append(" Min: ").append(minCurrent.getValueAsInt(intervalName));
		ret.append(" Max: ").append(maxCurrent.getValueAsInt(intervalName));
		ret.append(" Started: ").append(started.getValueAsInt(intervalName));
		ret.append(" Daemon: ").append(daemon.getValueAsInt(intervalName));
		return ret.toString();
	}

	public long getStarted(String interval){
		return started.getValueAsLong(interval);
	}
	
	public long getDaemon(String interval){
		return daemon.getValueAsLong(interval);
	}

	public long getCurrent(String interval){
		return current.getValueAsLong(interval);
	}

	public long getMinCurrent(String interval){
		return minCurrent.getValueAsLong(interval);
	}

	public long getMaxCurrent(String interval){
		return maxCurrent.getValueAsLong(interval);
	}
}
