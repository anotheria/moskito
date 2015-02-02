package net.anotheria.moskito.webui.accumulators.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.IComparable;

import java.util.HashMap;
import java.util.Map;

/**
 * This object is used to put values from different accumulators on the same timeline. It contains values from
 * selected accumulators at the same time.
 */
public class AccumulatedValuesBean implements IComparable{
	/**
	 * Timestamp of the value.
	 */
	private long timestamp;
	/**
	 * Map with values, with accumulator name as key, accumulator value as value.
	 */
	private Map<String, String> values;

	/**
	 * Creates a new AccumulatedValuesBean with a timestamp.
	 * @param aTimestamp the timestamp.
	 */
	public AccumulatedValuesBean(long aTimestamp){
		timestamp = aTimestamp;
		values = new HashMap<String, String>();
	}
	
	@Override public String toString(){
		return getTime() + ": "+values;
	}

	/**
	 * Sets the value for defined accumulator.
	 * @param name
	 * @param value
	 */
	public void setValue(String name, String value){
		values.put(name, value);
	}

	/**
	 * Return the timestamp as time string.
	 * @return
	 */
	public String getTime(){
		return NumberUtils.makeTimeString(timestamp);
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareLong(timestamp, ((AccumulatedValuesBean)anotherObject).timestamp);
	}

	/**
	 * Returns a value for named accumulator.
	 * @param name name of the accumulator.
	 * @return
	 */
	public String getValue(String name){
		return values.get(name);
	}

	/**
	 * Return the timestamp as numeric value.
	 *
	 * @return timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
}
