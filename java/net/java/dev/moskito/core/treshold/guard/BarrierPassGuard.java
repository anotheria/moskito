package net.java.dev.moskito.core.treshold.guard;

import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdStatus;

abstract class BarrierPassGuard implements ThresholdConditionGuard{
	private ThresholdStatus targetStatus;
	private GuardedDirection direction;
	
	public BarrierPassGuard(ThresholdStatus aTargetStatus, GuardedDirection aDirection){
		targetStatus = aTargetStatus;
		direction = aDirection;
	}
	
	@Override
	public ThresholdStatus getNewStatusOnUpdate(String previousValue,
			String newValue, ThresholdStatus currentStatus, Threshold threshold) {
		
		if (newValue.equals("NaN"))
			return currentStatus;
		ThresholdStatus ret =  direction.brokeThrough(getValueAsNumber(newValue), getBarrierValueAsNumber()) ? targetStatus : ThresholdStatus.OFF;
		return ret;
	}
	
	@Override public String toString(){
		return targetStatus+" if "+direction+" "+getValueAsString();
	}
	
	protected abstract String getValueAsString();
	protected abstract Number getValueAsNumber(String aValue);
	protected abstract Number getBarrierValueAsNumber();
}
	

