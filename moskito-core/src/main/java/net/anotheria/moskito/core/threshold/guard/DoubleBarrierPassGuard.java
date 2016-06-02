package net.anotheria.moskito.core.threshold.guard;

import net.anotheria.moskito.core.threshold.ThresholdStatus;

/**
 * A barrier path guard that works with double values.
 * @author lrosenberg
 */
public class DoubleBarrierPassGuard extends BarrierPassGuard{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Limit value.
	 */
	private double barrierValue;

	public DoubleBarrierPassGuard(ThresholdStatus aTargetStatus, double targetValue, GuardedDirection aDirection){
		super(aTargetStatus, aDirection);
		barrierValue = targetValue;
	}
	
	@Override
	public String getValueAsString(){
		return String.valueOf(barrierValue);
	}
	@Override
	protected Number getValueAsNumber(String aValue){
		return Double.parseDouble(aValue);
	}
	@Override
	protected Number getBarrierValueAsNumber(){
		return barrierValue;
	}
	
}
