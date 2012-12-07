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
 * Stats object for thread states.
 * @author another
 *
 */
public class ThreadStateStats extends AbstractStats {

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"CUR",
			"MIN",
			"MAX"
	));


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
		this("unnamed", Constants.getDefaultIntervals());
	} 
	
	public ThreadStateStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	} 

	public ThreadStateStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		current = StatValueFactory.createStatValue(0L, "current", selectedIntervals);
		min = StatValueFactory.createStatValue(0L, "min", selectedIntervals);
		min.setDefaultValueAsLong(Long.MAX_VALUE);
		min.reset();

		max = StatValueFactory.createStatValue(0L, "max", selectedIntervals);
		max.setDefaultValueAsLong(Long.MIN_VALUE);
		max.reset();


	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" CUR: ").append(current.getValueAsLong(intervalName));
		b.append(" MIN: ").append(min.getValueAsLong(intervalName));
		b.append(" MAX: ").append(max.getValueAsLong(intervalName));
		return b.toString();
	}

	public void updateCurrentValue(long value){
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
		if (valueName.equals("cur") || valueName.equals("current"))
			return ""+getCurrent(intervalName);
		if (valueName.equals("min"))
			return ""+getMin(intervalName);
		if (valueName.equals("max"))
			return ""+getMax(intervalName);
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

}
