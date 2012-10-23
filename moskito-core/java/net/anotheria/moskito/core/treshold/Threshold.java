package net.anotheria.moskito.core.treshold;

import net.anotheria.moskito.core.helper.AbstractTieable;
import net.anotheria.moskito.core.helper.Tieable;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.treshold.alerts.AlertHistory;
import net.anotheria.moskito.core.treshold.alerts.ThresholdAlert;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A threshold represents a value of stats producer.
 * @author lrosenberg
 *
 */
public class Threshold extends AbstractTieable<ThresholdDefinition> implements Tieable, ThresholdMBean{
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(Threshold.class);
	/**
	 * Status of the threshold.
	 */
	private ThresholdStatus status;
	/**
	 * Configured guards.
	 */
	private List<ThresholdConditionGuard> guards;
	/**
	 * Attached stats.
	 */
	private IStats stats;
	/**
	 * Last measured value.
	 */
	private String lastValue;
	/**
	 * Last change as string (description).
	 */
	private String statusChange = null;
	/**
	 * Timestamp of the last change.
	 */
	private long statusChangeTimestamp;
	/**
	 * Instance number of the current instance.
	 */
	private int instanceNumber;
	
	public Threshold(ThresholdDefinition aDefinition){
		super(aDefinition);
		status = ThresholdStatus.OFF;
		lastValue = "none yet";
		guards = new ArrayList<ThresholdConditionGuard>();
	}
	
	public void tieToStats(IStats aStatsObject){
		stats = aStatsObject;
	}
	
	public void addGuard(ThresholdConditionGuard guard){
		guards.add(guard);
	}
	
	public List<ThresholdConditionGuard> getGuards(){
		ArrayList<ThresholdConditionGuard> ret = new ArrayList<ThresholdConditionGuard>(guards.size());
		ret.addAll(guards);
		return ret;
	}

	public ThresholdStatus getStatus() {
		return status;
	}
	
	public String getStatusString(){
		return getStatus().name();
	}

	public IStats getStats() {
		return stats;
	}

	public String getLastValue() {
		return lastValue;
	}
	
	@Override public void update(){
		if (!isActivated()){
			return;
		}
		
		String previousValue = lastValue;
		lastValue = stats.getValueByNameAsString(getDefinition().getValueName(), getDefinition().getIntervalName(), getDefinition().getTimeUnit());
		
		ThresholdStatus futureStatus = status == ThresholdStatus.OFF ? ThresholdStatus.OFF : ThresholdStatus.GREEN;
		for (ThresholdConditionGuard guard : guards){
			try{
				ThresholdStatus newStatus = guard.getNewStatusOnUpdate(previousValue, lastValue, status, this);
				if (newStatus.overrules(futureStatus)){
					futureStatus = newStatus;
				}
			}catch(Exception e){
				log.warn("Error in ThresholdConditionGuard: "+guard+" in getNewStatusOnUpdate("+previousValue+", "+lastValue+", "+status+", "+this, e);
			}
		}
		
		//generate alert.
		if (status != futureStatus){
			//generate alert
			statusChange = status+" --> "+futureStatus;
			statusChangeTimestamp = System.currentTimeMillis();
			AlertHistory.INSTANCE.addAlert(new ThresholdAlert(this, status, futureStatus, previousValue, lastValue));
		}
		status = futureStatus;
	}

	public boolean isActivated(){
		return stats != null;
	}
	
	@Override public String toString(){
		return getName()+" "+getStatus()+" Def: "+getDefinition()+" LastValue: "+getLastValue()+", Guards: "+guards+" active: "+isActivated()+", Stats: "+getStats();
	}

	public String getStatusChange() {
		return statusChange;
	}

	public void setStatusChange(String statusChange) {
		this.statusChange = statusChange;
	}

	public long getStatusChangeTimestamp() {
		return statusChangeTimestamp;
	}

	public void setStatusChangeTimestamp(long statusChangeTimestamp) {
		this.statusChangeTimestamp = statusChangeTimestamp;
	}
}
