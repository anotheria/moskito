package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class CounterStats extends GenericCounterStats{
	public CounterStats(String name, Interval[] intervals){
		super(name, intervals, "counter");
	}
	public CounterStats(String name){
		super(name, "counter");
	}

	public void inc(){
		super.inc("counter");
	}

	public void incBy(long value){
		super.incBy("counter", value);
	}

	public long get(String intervalName){
		return get("counter", intervalName);
	}

	public long get(){
		return get("counter", null);
	}

	@Override
	public String describeForWebUI() {
		return "Counter";
	}
}
