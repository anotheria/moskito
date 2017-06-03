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
 * Stats for (read) caches.
 * @author lrosenberg
 */
public class ErrorStats extends AbstractStats {
	/**
	 * Number of errors that were initial in their thread (first).
	 */
	private StatValue initial;

	/**
	 * Total number of errors.
	 */
	private StatValue total;

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"INITIAL",
			"TOTAL"
	));

	/**
	 * Creates a new 'unnamed' cache stats object with default intervals.
	 */
	public ErrorStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}

	public ErrorStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new cachestats object.
	 * @param aName
	 * @param selectedIntervals
	 */
	public ErrorStats(String aName, Interval[] selectedIntervals){
		super(aName);
		Long longPattern = Long.valueOf(0);

		initial = StatValueFactory.createStatValue(longPattern, "initial", selectedIntervals);
		total = StatValueFactory.createStatValue(longPattern, "total", selectedIntervals);

		addStatValues(initial);

	}
	

	
	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" INITIAL: ").append(initial.getValueAsLong(intervalName));
		b.append(" TOTAL: ").append(total.getValueAsLong(intervalName));
		return b.toString();
	}

	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		if (valueName.equals("initial"))
			return String.valueOf(initial.getValueAsLong(intervalName));
		if (valueName.equals("total"))
			return String.valueOf(total.getValueAsLong(intervalName));
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	public void addError(boolean isInitialError){
		total.increase();
		if (isInitialError)
			initial.increase();
	}

	public long getInitial(){
		return initial.getValueAsLong();
	}

	public long getTotal(){
		return total.getValueAsLong();
	}

	public long getInitial(String intervalName){
		return initial.getValueAsLong(intervalName);
	}

	public long getTotal(String intervalName){
		return total.getValueAsLong(intervalName);
	}

	@Override public String toString(){
		return toStatsString();
	}
}
