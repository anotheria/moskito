package net.java.dev.moskito.core.treshold.guard;

import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class LongBarrierPassGuard extends BarrierPassGuard{
	private long barrierValue;

	public LongBarrierPassGuard(ThresholdStatus aTargetStatus, long targetValue, GuardedDirection aDirection){
		super(aTargetStatus, aDirection);
		barrierValue = targetValue;
	}
	
	public String getValueAsString(){
		return ""+barrierValue;
	}
	
	protected Number getValueAsNumber(String aValue){
		return Long.parseLong(aValue);
	}
	
	protected Number getBarrierValueAsNumber(){
		return barrierValue;
	}
}
