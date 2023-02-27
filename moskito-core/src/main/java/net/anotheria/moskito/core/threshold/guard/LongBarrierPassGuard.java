package net.anotheria.moskito.core.threshold.guard;

import net.anotheria.moskito.core.threshold.ThresholdStatus;

import jakarta.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * A barrier path guard that works with long values.
 * @author another
 *
 */

public class LongBarrierPassGuard extends BarrierPassGuard implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2297541614810269187L;

	/**
	 * The threshold value of the guard.
	 */
	@XmlElement
	private long barrierValue;

	/**
	 * Creates a new LongBarrierPassGuard.
	 * @param aTargetStatus target status.
	 * @param targetValue threshold value.
	 * @param aDirection direction.
	 */
	public LongBarrierPassGuard(ThresholdStatus aTargetStatus, long targetValue, GuardedDirection aDirection){
		super(aTargetStatus, aDirection);
		barrierValue = targetValue;
	}
	
	@Override public String getValueAsString(){
		return String.valueOf(barrierValue);
	}
	
	@Override protected Number getValueAsNumber(String aValue){
		return Long.parseLong(aValue);
	}
	
	@Override protected Number getBarrierValueAsNumber(){
		return barrierValue;
	}

}
