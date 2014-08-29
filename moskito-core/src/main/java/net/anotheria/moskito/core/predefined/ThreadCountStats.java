package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats for thread creation.
 * @author lrosenberg
 */
public class ThreadCountStats extends AbstractStats {

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"Started",
			"Min",
			"Max",
			"Daemon",
			"Cur"
	));

	/**
	 * Number of started threads.
	 */
	private StatValue started;
	/**
	 * Number of currently running threads.
	 */
	private StatValue current;
	/**
	 * Number of daemon threads.
	 */
	private StatValue daemon;
	/**
	 * Min number of running threads.
	 */
	private StatValue minCurrent;
	/**
	 * Max number of running threads.
	 */
	private StatValue maxCurrent;
	
	
	/**
	 * Creates new ThreadCountStats.
	 */
	public ThreadCountStats() {
		this(Constants.getDefaultIntervals());
	}
	
	/**
	 * Creates new ThreadCountStats.
	 */
	public ThreadCountStats(Interval[] intervals){
		super("ThreadCount");
		
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
	
	/**
	 * Called regularly by a timer, updates the internal stats.
	 * @param aStarted
	 * @param aDaemon
	 * @param aCurrent
	 */
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

	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		
		if (valueName==null)
			return null;
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("cur") || valueName.equals("current"))
			return ""+getCurrent(intervalName);
		if (valueName.equals("min") || valueName.equals("mincurrent") || valueName.equals("min cur"))
			return ""+getMinCurrent(intervalName);
		if (valueName.equals("max") || valueName.equals("maxcurrent") || valueName.equals("max cur"))
			return ""+getMaxCurrent(intervalName);
		if (valueName.equals("started"))
			return ""+getStarted(intervalName);
		if (valueName.equals("daemon"))
			return ""+getDaemon(intervalName);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}
}
