package net.anotheria.moskito.core.treshold.guard;

import net.anotheria.moskito.core.treshold.Threshold;
import net.anotheria.moskito.core.treshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.treshold.ThresholdStatus;

/**
 * A barrier pass guard fires as soon as a barrier has been passed.
 * @author lrosenberg
 *
 */
abstract class BarrierPassGuard implements ThresholdConditionGuard {
	/**
	 * The status to activate in case the barrier has been passed.
	 */
	private ThresholdStatus targetStatus;
	/**
	 * The guarded direction (top or down).
	 */
	private GuardedDirection direction;
	
	public BarrierPassGuard(ThresholdStatus aTargetStatus, GuardedDirection aDirection){
		targetStatus = aTargetStatus;
		direction = aDirection;
	}
	
	@Override
	public ThresholdStatus getNewStatusOnUpdate(String previousValue,
			String newValue, ThresholdStatus currentStatus, Threshold threshold) {
		
		if (newValue==null || newValue.equals("NaN"))
			return currentStatus;
		ThresholdStatus ret =  direction.brokeThrough(getValueAsNumber(newValue), getBarrierValueAsNumber()) ? targetStatus : ThresholdStatus.OFF;
		return ret;
	}
	
	@Override public String toString(){
		return targetStatus+" if "+direction+" "+getValueAsString();
	}
	/**
	 * Returns the value as string for alert generation.
	 * @return
	 */
	protected abstract String getValueAsString();
	/**
	 * Returns the value of the producer as number.
	 * @return
	 */
	protected abstract Number getValueAsNumber(String aValue);
	/**
	 * Returns the guard threshold as number.
	 * @return
	 */
	protected abstract Number getBarrierValueAsNumber();
}
	

