package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Stats for counters.
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class CounterStats extends GenericCounterStats{
	/**
	 * Creates new counter stats object.
	 * @param name
	 * @param intervals
	 */
	public CounterStats(String name, Interval[] intervals){
		super(name, intervals, "counter");
	}

	/**
	 * Creates new counter stats object.
	 * @param name name of the object.
	 */
	public CounterStats(String name){
		super(name, "counter");
	}

	/**
	 * Increases current counter.
	 */
	public void inc(){
		super.inc("counter");
	}

	public void dec() {
		super.dec("counter");
	}

	/**
	 * Increases current counter by a value.
	 * @param value
	 */
	public void incBy(long value){
		super.incBy("counter", value);
	}

	/**
	 * Decreases current counter by a value.
	 * @param value
	 */
	public void decBy(long value) {
		super.decBy("counter", value);
	}

	/**
	 * Gets the counter value fo an interval.
	 * @param intervalName name of the interval.
	 * @return
	 */
	public long get(String intervalName){
		return get("counter", intervalName);
	}

	/**
	 * Gets the current value for default interval.
	 * @return
	 */
	public long get(){
		return get("counter", null);
	}

	public void set(int value){
		super.set("counter", value);
	}

	@Override
	public String describeForWebUI() {
		return "Counter";
	}
}
