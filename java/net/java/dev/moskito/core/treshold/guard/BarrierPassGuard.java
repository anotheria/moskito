package net.java.dev.moskito.core.treshold.guard;

import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class BarrierPassGuard implements ThresholdConditionGuard{
	private long barrierValue;
	private ThresholdStatus targetStatus;
	private GuardedDirection direction;
	
	public BarrierPassGuard(ThresholdStatus aTargetStatus, long targetValue, GuardedDirection aDirection){
		targetStatus = aTargetStatus;
		barrierValue = targetValue;
		direction = aDirection;
	}
	
	@Override
	public ThresholdStatus getNewStatusOnUpdate(String previousValue,
			String newValue, ThresholdStatus currentStatus, Threshold threshold) {
		
		long newValueAsLong = Long.parseLong(newValue);
		ThresholdStatus ret =  direction.brokeThrough(newValueAsLong, barrierValue) ? targetStatus : ThresholdStatus.OFF;
		//System.out.println(barrierValue+", "+newValue+", "+currentStatus+" -> "+ret);
		return ret;
	}
	
	@Override public String toString(){
		return targetStatus+" if "+direction+" "+barrierValue;
	}
	
}
