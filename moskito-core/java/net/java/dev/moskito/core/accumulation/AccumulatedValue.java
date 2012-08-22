package net.java.dev.moskito.core.accumulation;

import net.anotheria.util.NumberUtils;

/**
 * Holds a value accumulated by the acucmulator.
 * @author another
 *
 */
public class AccumulatedValue {
	/**
	 * Timestamp of the value.
	 */
	private long timestamp;
	/**
	 * Value of the 'value'.
	 */
	private String value;
	
	/**
	 * Creates a new AccumulatedValue.
	 * @param aValue the value for the timestamp.
	 * @param aTimestamp the timestamp of the probe.
	 */
	public AccumulatedValue(String aValue, long aTimestamp){
		value = aValue;
		timestamp = aTimestamp;
	}
	
	/**
	 * Creates a new accumulated value with now as timestamp.
	 * @param aValue
	 */
	public AccumulatedValue(String aValue){
		this(aValue, System.currentTimeMillis());
	}
	
	@Override public String toString(){
		return getISO8601Timestamp()+" "+getValue();
	}
	
	public long getTimestamp(){
		return timestamp;
	}
	
	public String getValue(){
		return value;
	}
	
	/**
	 * Returns the ISO8601 Timestamp.
	 * @return the timestamp in iso format.
	 */
	public String getISO8601Timestamp(){
		return NumberUtils.makeISO8601TimestampString(timestamp);
	}
}

