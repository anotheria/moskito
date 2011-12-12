package net.java.dev.moskito.core.treshold;

import net.anotheria.util.NumberUtils;

/**
 * Created whenever a barrier in threshold is broken and a threshold gets new status. 
 * @author lrosenberg
 *
 */
public class ThresholdAlert {
	/**
	 * Timestamp of change.
	 */
	private long timestamp;
	/**
	 * Corresponding threshold.
	 */
	private Threshold threshold;
	/**
	 * Status before the change.
	 */
	private ThresholdStatus oldStatus;
	/**
	 * Status after the change.
	 */
	private ThresholdStatus newStatus;
	/**
	 * Value before the change.
	 */
	private String oldValue;
	/**
	 * Value after the change.
	 */
	private String newValue;
	
	public ThresholdAlert(Threshold aThreshold, ThresholdStatus anOldStatus, ThresholdStatus aNewStatus, String anOldValue, String aNewValue){
		timestamp = System.currentTimeMillis();
		threshold = aThreshold;
		oldStatus = anOldStatus;
		newStatus = aNewStatus;
		oldValue  = anOldValue;
		newValue  = aNewValue;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public Threshold getThreshold() {
		return threshold;
	}
	public void setThreshold(Threshold threshold) {
		this.threshold = threshold;
	}
	public ThresholdStatus getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(ThresholdStatus oldStatus) {
		this.oldStatus = oldStatus;
	}
	public ThresholdStatus getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(ThresholdStatus newStatus) {
		this.newStatus = newStatus;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	@Override public String toString(){
		return NumberUtils.makeISO8601TimestampString(getTimestamp())+" "+threshold.getName()+", ("+getOldStatus()+"/ "+getOldValue()+") --> "+
		"("+getNewStatus()+"/ "+getNewValue()+")";
	}
	
	
	
	
}
