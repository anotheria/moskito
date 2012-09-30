package net.anotheria.moskito.core.treshold;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

/**
 * Lists possible threshold statuses.
 * @author lrosenberg
 *
 */
public enum ThresholdStatus implements IComparable{
	/**
	 * Threshold is off, there were no values ever measured.
	 */
	OFF,
	/**
	 * Threshold is ok, no problems detected.
	 */
	GREEN,
	/**
	 * Threshold is set to yellow.
	 */
	YELLOW,
	/**
	 * Threshold is set to orange. This usually means that someone should look into the data, but its not critical yet.
	 */
	ORANGE,
	/**
	 * Threshold is set to red. Something is wrong and should be investigated.
	 */
	RED,
	/**
	 * Something is terrible wrong. Usually this thresholds means that the guarded component is completely unavailable.
	 */
	PURPLE;
	
	/**
	 * Returns true if current threshold object is worse than parameter threshold.
	 * @param anotherStatus
	 * @return
	 */
	public boolean overrules(ThresholdStatus anotherStatus){
		return ordinal() > anotherStatus.ordinal();
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareInt(ordinal(), ((ThresholdStatus)anotherObject).ordinal());
	}
}
