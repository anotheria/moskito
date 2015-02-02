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
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" CUR: ").append(current.getValueAsLong(intervalName));
		b.append(" MIN: ").append(min.getValueAsLong(intervalName));
		b.append(" MAX: ").append(max.getValueAsLong(intervalName));
		return b.toString();
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
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();

		if (valueName.equals("current") || valueName.equals("cur"))
			return ""+getCurrent(intervalName);
		if (valueName.equals("current mb") || valueName.equals("cur mb"))
			return ""+(getCurrent(intervalName)/MB);

		if (valueName.equals("min") )
			return ""+getMin(intervalName);
		if (valueName.equals("min mb"))
			return ""+(getMin(intervalName)/MB);

		if (valueName.equals("max") )
			return ""+getMax(intervalName);
		if (valueName.equals("max mb"))
			return ""+(getMax(intervalName)/MB);

		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}
}
