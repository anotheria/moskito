package net.anotheria.moskito.core.threshold;

import net.anotheria.moskito.core.config.thresholds.ThresholdConfig;
import net.anotheria.moskito.core.helper.AbstractTieable;
import net.anotheria.moskito.core.helper.Tieable;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.threshold.alerts.AlertDispatcher;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static Logger log = LoggerFactory.getLogger(Threshold.class);
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
	 * Timestamp of the last change.
	 */
	private long statusChangeTimestamp;

	/**
	 * Previous status.
	 */
	private ThresholdStatus previousStatus = ThresholdStatus.OFF;

	/**
	 * Counts the number of flips (status changes) by this thresholds. THis helps to identify flipping - instable thresholds.
	 */
	private int flipCount;

	public Threshold(ThresholdDefinition aDefinition){
		super(aDefinition);
		status = ThresholdStatus.OFF;
		lastValue = "none yet";
		guards = new ArrayList<ThresholdConditionGuard>();
		flipCount = 0;
	}
	
	public void tieToStats(IStats aStatsObject){
		stats = aStatsObject;
	}
	
	public void addGuard(ThresholdConditionGuard guard){
		guards.add(guard);
	}

	public void addLongGuardLineDownUp(long green, long yellow, long orange, long red, long purple){
		addLongGuardLine(green, yellow, orange, red, purple, GuardedDirection.DOWN, GuardedDirection.UP);
	}

	public void addLongGuardLineUpDown(long green, long yellow, long orange, long red, long purple){
		addLongGuardLine(green, yellow, orange, red, purple, GuardedDirection.UP, GuardedDirection.DOWN);
	}

	private void addLongGuardLine(long green, long yellow, long orange, long red, long purple, GuardedDirection first, GuardedDirection other){
		addGuard(new LongBarrierPassGuard(ThresholdStatus.GREEN, green, first));
		addGuard(new LongBarrierPassGuard(ThresholdStatus.YELLOW, yellow, other));
		addGuard(new LongBarrierPassGuard(ThresholdStatus.ORANGE, orange, other));
		addGuard(new LongBarrierPassGuard(ThresholdStatus.RED, red, other));
		addGuard(new LongBarrierPassGuard(ThresholdStatus.PURPLE, purple, other));
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
			flipCount++;
			//generate alert
			previousStatus = status;
			statusChangeTimestamp = System.currentTimeMillis();
			AlertDispatcher.INSTANCE.dispatchAlert(new ThresholdAlert(this, status, futureStatus, previousValue, lastValue, flipCount));
		}
		status = futureStatus;
	}

	public boolean isActivated(){
		return stats != null;
	}
	
	@Override public String toString(){
		return getName()+" "+getStatus()+" Def: "+getDefinition()+" LastValue: "+getLastValue()+", Guards: "+guards+" active: "+isActivated()+", Stats: "+getStats();
	}

	public long getStatusChangeTimestamp() {
		return statusChangeTimestamp;
	}

	public void setStatusChangeTimestamp(long statusChangeTimestamp) {
		this.statusChangeTimestamp = statusChangeTimestamp;
	}

	public int getFlipCount() {
		return flipCount;
	}

	/**
	 * This method allows to recreate proper configuration object for this threshold.
	 * @return
	 */
	public ThresholdConfig toConfigObject(){
		ThresholdConfig ret = new ThresholdConfig();
		ret.setIntervalName(getDefinition().getIntervalName());
		ret.setName(getDefinition().getName());
		ret.setProducerName(getDefinition().getProducerName());
		ret.setStatName(getDefinition().getStatName());
		ret.setValueName(getDefinition().getValueName());
		ret.setTimeUnit(getDefinition().getTimeUnit().name());
		return ret;
	}

	public ThresholdStatus getPreviousStatus() {
		return previousStatus;
	}
}
