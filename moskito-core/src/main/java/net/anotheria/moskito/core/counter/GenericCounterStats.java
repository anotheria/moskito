package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

	protected GenericCounterStats(String name, Interval[] intervals, String firstCounter, String ... moreCounters){
		super(name);
		values = new HashMap<>();
		StatValue firstCounterValue = StatValueFactory.createStatValue(Long.valueOf(0L), firstCounter, intervals);
		addStatValues(firstCounterValue);
		values.put(firstCounter, firstCounterValue);
		if (moreCounters != null){
			for (String mc : moreCounters){
				StatValue additionalValue = StatValueFactory.createStatValue(Long.valueOf(0L), mc, intervals);
				addStatValues(additionalValue);
				values.put(mc, additionalValue);
			}
		}
		valueNames = Collections.unmodifiableList(new ArrayList<String>(values.keySet()));
	}

	protected GenericCounterStats(String name, String firstCounter, String ... moreCounters){
		this(name, Constants.getDefaultIntervals(), firstCounter, moreCounters);
	}

	public void inc(String counterName){
		values.get(counterName).increase();
	}

	public void dec(String counterName) {
		values.get(counterName).decrease();
	}

	public void incBy(String counterName, long value){
		values.get(counterName).increaseByLong(value);
	}

	public long get(String counterName, String intervalName){
		return values.get(counterName).getValueAsLong(intervalName);
	}


	@Override
	public String toStatsString(String aIntervalName, TimeUnit unit) {
		StringBuilder s = new StringBuilder();
		s.append(getName()).append(' ');
		for (Map.Entry<String,StatValue> entry : values.entrySet()) {
			s.append(entry.getKey()).append(": ");
			s.append(entry.getValue().getValueAsLong(aIntervalName));
		}
		return s.toString();
	}

	@Override public String toString(){
		return getName()+ ' ' +values.values();
	}

	public Set<String> getPossibleNames(){
		return values.keySet();
	}

	public abstract String describeForWebUI();

	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		return String.valueOf(values.get(valueName).getValueAsLong(intervalName));
	}

	@Override
	public List<String> getAvailableValueNames() {
		return valueNames;
	}

	public void set(String counterName, int value) {
		values.get(counterName).setValueAsInt(value);
	}
}

