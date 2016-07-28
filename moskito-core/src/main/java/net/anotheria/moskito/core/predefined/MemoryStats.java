package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

/**
 * Stat for a memory amount (used, heap, free etc).
 * @author lrosenberg
 *
 */
public class MemoryStats extends AbstractStats {
	/**
	 * Current value.
	 */
	private StatValue current;
	/**
	 * Min value.
	 */
	private StatValue min;
	/**
	 * Max value.
	 */
	private StatValue max;

	private static final long MB = 1024L*1024;
	
	public MemoryStats(){
		this("unnamed", Constants.getDefaultIntervals());
	} 
	
	public MemoryStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	} 

	public MemoryStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		current = StatValueFactory.createStatValue(0L, "current", selectedIntervals);
		min = StatValueFactory.createStatValue(0L, "min", selectedIntervals);
		max = StatValueFactory.createStatValue(0L, "max", selectedIntervals);

		addStatValues(current, min, max);
		
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
        String b = getName() + ' ' +
                " CUR: " + current.getValueAsLong(intervalName) +
                " MIN: " + min.getValueAsLong(intervalName) +
                " MAX: " + max.getValueAsLong(intervalName);
        return b;
	}

	public void updateMemoryValue(long value){
		current.setValueAsLong(value);
		min.setValueIfLesserThanCurrentAsLong(value);
		max.setValueIfGreaterThanCurrentAsLong(value);
	}
	
	public long getCurrent(String intervalName){
		return current.getValueAsLong(intervalName);
	}
	public long getMin(String intervalName){
		return min.getValueAsLong(intervalName);
	}
	public long getMax(String intervalName){
		return max.getValueAsLong(intervalName);
	}

	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();

		if (valueName.equals("current") || valueName.equals("cur"))
			return String.valueOf(getCurrent(intervalName));
		if (valueName.equals("current mb") || valueName.equals("cur mb"))
			return String.valueOf(getCurrent(intervalName) / MB);

		if (valueName.equals("min") )
			return String.valueOf(getMin(intervalName));
		if (valueName.equals("min mb"))
			return String.valueOf(getMin(intervalName) / MB);

		if (valueName.equals("max") )
			return String.valueOf(getMax(intervalName));
		if (valueName.equals("max mb"))
			return String.valueOf(getMax(intervalName) / MB);

		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}
}
