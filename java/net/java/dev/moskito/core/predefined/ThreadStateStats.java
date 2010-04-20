package net.java.dev.moskito.core.predefined;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

public class ThreadStateStats extends AbstractStats{
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
	
	public ThreadStateStats(){
		this("unnamed", Constants.DEFAULT_INTERVALS);
	} 
	
	public ThreadStateStats(String aName){
		this(aName, Constants.DEFAULT_INTERVALS);
	} 

	public ThreadStateStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		current = StatValueFactory.createStatValue(0L, "current", selectedIntervals);
		min = StatValueFactory.createStatValue(0L, "min", selectedIntervals);
		max = StatValueFactory.createStatValue(0L, "max", selectedIntervals);
		
		
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



}
