package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Predefined counter stats for traffic separation between male and female users. This is an example for a counter.
 * @author lrosenberg
 * @since 17.11.12 23:05
 */
public class MaleFemaleStats extends GenericCounterStats{
	/**
	 * Creates a new male-female stats object, with the default intervals.
	 * @param name name of the stats object.
	 */
	public MaleFemaleStats(String name){
		super(name, "male", "female");
	}

	/**
	 * Creates a new stats object.
	 * @param name name of the stats object.
	 * @param intervals intervals.
	 */
	public MaleFemaleStats(String name, Interval[] intervals){
		super(name, intervals, "male", "female");
	}

	/**
	 * Increases male value.
	 */
	public void incMale(){
		super.inc("male");
	}

	/**
	 * Increases female value.
	 */
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
	@Override
	public String describeForWebUI() {
		return "MaleFemale";
	}

}
