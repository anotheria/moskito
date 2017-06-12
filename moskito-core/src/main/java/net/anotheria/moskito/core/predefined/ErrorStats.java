package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats for (read) caches.
 * @author lrosenberg
 */
public class ErrorStats extends AbstractStats implements IIntervalListener {
	/**
	 * Number of errors that were initial in their thread (first).
	 */
	private StatValue initial;

	/**
	 * Total number of errors.
	 */
	private StatValue total;

	/**
	 * Max number of errors that were initial in their thread (first).
	 */
	private StatValue maxInitial;

	/**
	 * Max total number of errors.
	 */
	private StatValue maxTotal;

	/**
	 * Value names for supported values.
	 */
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"INITIAL",
			"TOTAL",
			"MAXINITIAL",
			"MAXTOTAL"
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
		Integer pattern = Integer.valueOf(0);

		initial = StatValueFactory.createStatValue(pattern, "initial", selectedIntervals);
		total = StatValueFactory.createStatValue(pattern, "total", selectedIntervals);

		maxInitial = StatValueFactory.createStatValue(pattern, "maxInitial", selectedIntervals);
		maxTotal   = StatValueFactory.createStatValue(pattern, "maxTotal", selectedIntervals);

		addStatValues(initial, total, maxInitial, maxTotal);
		IntervalRegistry.getInstance().getInterval("1m").addSecondaryIntervalListener(this);

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
		b.append(" MAX INITIAL: ").append(maxInitial.getValueAsLong(intervalName));
		b.append(" MAX TOTAL: ").append(maxTotal.getValueAsLong(intervalName));
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
		if (valueName.equals("maxinitial"))
			return String.valueOf(maxInitial.getValueAsLong(intervalName));
		if (valueName.equals("maxtotal"))
			return String.valueOf(maxTotal.getValueAsLong(intervalName));
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	public void addError(boolean isInitialError){
		total.increase();
		if (isInitialError) {
			initial.increase();

		}
	}

	public int getInitial(){
		return initial.getValueAsInt();
	}

	public int getTotal(){
		return total.getValueAsInt();
	}

	public int getInitial(String intervalName){
		return initial.getValueAsInt(intervalName);
	}

	public int getTotal(String intervalName){
		return total.getValueAsInt(intervalName);
	}

	public int getMaxInitial(){
		return maxInitial.getValueAsInt();
	}

	public int getMaxTotal(){
		return maxTotal.getValueAsInt();
	}

	public int getMaxInitial(String intervalName){
		return maxInitial.getValueAsInt(intervalName);
	}

	public int getMaxTotal(String intervalName){
		return maxTotal.getValueAsInt(intervalName);
	}

	@Override public String toString(){
		return toStatsString();
	}

	@Override
	public void intervalUpdated(Interval callerInterval) {
		int initialValueForLastInterval = initial.getValueAsInt(callerInterval.getName());
		if (initialValueForLastInterval>maxInitial.getValueAsInt())
			maxInitial.setValueAsInt(initialValueForLastInterval);


		int totalValueForLastInterval = total.getValueAsInt(callerInterval.getName());
		if (totalValueForLastInterval>maxTotal.getValueAsInt()) {
			maxTotal.setValueAsInt(totalValueForLastInterval);
		}

	}
}
