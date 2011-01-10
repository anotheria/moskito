package net.java.dev.moskito.core.accumulation;

import net.anotheria.util.NumberUtils;

public class AccumulatedValue {
	/**
	 * Timestamp of the value.
	 */
	private long timestamp;
	/**
	 * Value of the 'value'.
	 */
	private String value;
	
	
	public AccumulatedValue(String aValue, long aTimestamp){
		value = aValue;
		timestamp = aTimestamp;
	}
	
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
	
	public String getISO8601Timestamp(){
		return NumberUtils.makeISO8601TimestampString(timestamp);
	}
}

