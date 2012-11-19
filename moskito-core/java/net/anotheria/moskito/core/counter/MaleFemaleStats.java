package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class MaleFemaleStats extends GenericCounterStats{
	public MaleFemaleStats(String name){
		super(name, "male", "female");
	}
	public MaleFemaleStats(String name, Interval[] intervals){
		super(name, intervals, "male", "female");
	}

	public void incMale(){
		super.inc("male");
	}

	public void incFemale(){
		super.inc("female");
	}

	public long getMale(String intervalName){
		return get("male", intervalName);
	}

	public long getMale(){
		return get("male", null);
	}

	public long getFemale(String intervalName){
		return get("female", intervalName);
	}

	public long getFemale(){
		return get("female", null);
	}
}
