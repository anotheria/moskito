package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.*;

/**
 * Generic counter implementation.
 *
 * @author lrosenberg
 * @since 16.11.12 23:11
 */
public abstract class GenericCounterStats extends AbstractStats{

	/**
	 * Map with statvalues for faster access.
	 */
	private HashMap<String, StatValue> values;

	/**
	 * Available value names.
	 */
	private final List<String> valueNames;

	/**
	 * Main constructor, counter names as list
	 * @param name name of the stats object
	 * @param intervals intervals
	 * @param counters list of counter names
	 */
	protected GenericCounterStats(String name, Interval[] intervals, List<String> counters) {
		// Super call
		super(name);

		// Make sure that there are some counters and that they aren't empty
		if (counters != null && !counters.isEmpty()) {
			// Create new map to store counterName - statValue pairs
			this.values = new HashMap<>(counters.size());

			// For each counter
			for (String counter : counters) {
				// Create new stat value
				StatValue statValue = StatValueFactory.createStatValue(0L, counter, intervals);

				// Store it in the map
				this.values.put(counter, statValue);

				// Register it in the parent class
				super.addStatValues(statValue);
			}

			// Copy the counter names as an unmodifiable list
			this.valueNames = Collections.unmodifiableList(counters);
		}

		// If counters are null or empty - throw an exception
		else {
			throw new IllegalArgumentException("Illegal counters=" + counters);
		}
	}

	/**
	 * Main constructor, counter names as array
	 * @param name name of the stats object
	 * @param intervals intervals
	 * @param counters array of counter names
	 */
	protected GenericCounterStats(String name, Interval[] intervals, String ... counters) {
		this(name, intervals, Arrays.asList(counters));
	}

	/**
	 * Shortened constructor, counter names as list
	 * We assume that we use default intervals obtained by {@link Constants#getDefaultIntervals()}
	 * @param name name of the stats object
	 * @param counters list of counter names
	 */
	protected GenericCounterStats(String name, List<String> counters) {
		this(name, Constants.getDefaultIntervals(), counters);
	}

	/**
	 * Shortened constructor, counter names as array
	 * We assume that we use default intervals obtained by {@link Constants#getDefaultIntervals()}
	 * @param name name of the stats object
	 * @param counters array of counter names
	 */
	protected GenericCounterStats(String name, String ... counters){
		this(name, Constants.getDefaultIntervals(), counters);
	}

	/**
	 * Increment counter by 1
	 * @param counterName counter
	 */
	public void inc(String counterName){
		values.get(counterName).increase();
	}

	/**
	 * Decrement counter by 1
	 * @param counterName counter
	 */
	public void dec(String counterName) {
		values.get(counterName).decrease();
	}

	/**
	 * Increase counter by a given value
	 * @param counterName counter
	 * @param value increment value
	 */
	public void incBy(String counterName, long value){
		values.get(counterName).increaseByLong(value);
	}

	/**
	 * Decrease counter by a given value
	 * @param counterName counter
	 * @param value decrement value
	 */
	public void decBy(String counterName, long value) {
		values.get(counterName).decreaseByLong(value);
	}

	public long get(String counterName, String intervalName){
		return values.get(counterName).getValueAsLong(intervalName);
	}

	public void set(String counterName, int value) {
		values.get(counterName).setValueAsInt(value);
	}

	@Override
	public String toStatsString(String aIntervalName, TimeUnit unit) {
		// Create new string builder
		StringBuilder s = new StringBuilder();

		// Append stat name
		s.append(getName()).append(' ');

		// For each counter name
		for (String name : valueNames) {
			// Extract stat value and then long value for this counter
			StatValue statValue = values.get(name);
			long longValue = statValue.getValueAsLong(aIntervalName);

			// Append them to the string builder
			s.append(name).append(": ").append(longValue);
		}

		// Build the resulting string
		return s.toString();
	}

	@Override
	public String toString(){
		return getName()+ ' ' +values.values();
	}

	public abstract String describeForWebUI();

	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		// If counter name is null or empty
		if (valueName == null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();

		// Get the value of this counter and convert it to string
		return String.valueOf(get(valueName, intervalName));
	}

	@Override
	public List<String> getAvailableValueNames() {
		return valueNames;
	}

	/**
	 * This method is no longer supported, please use {@link GenericCounterStats#getAvailableValueNames()}
	 * @return a set of possible names
	 */
	@Deprecated
	public Set<String> getPossibleNames() {
		return new LinkedHashSet<>(this.getAvailableValueNames());
	}
}