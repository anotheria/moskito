package net.java.dev.moskito.core.treshold.guard;

import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class DoubleBarrierPassGuard extends BarrierPassGuard{
	private double barrierValue;

	public DoubleBarrierPassGuard(ThresholdStatus aTargetStatus, double targetValue, GuardedDirection aDirection){
		super(aTargetStatus, aDirection);
		barrierValue = targetValue;
	}
	
	public String getValueAsString(){
		return ""+barrierValue;
	}
	
	protected Number getValueAsNumber(String aValue){
		return Double.parseDouble(aValue);
	}
	protected Number getBarrierValueAsNumber(){
		return barrierValue;
	}
	
}
