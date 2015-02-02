package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.06.14 01:21
 */
public class DiffLongValueHolder extends LongValueHolder {

	private volatile long lastCurrentValue = 0;

	public DiffLongValueHolder(Interval anInterval){
		super(anInterval);
	}

	@Override
	public void intervalUpdated(Interval caller) {
		long currentCurrentValue = getCurrentValueAsLong();
		long diff = currentCurrentValue - lastCurrentValue;
		lastCurrentValue = currentCurrentValue;
		setLastValue(diff);
	}

	@Override
	public String toString() {
		return super.toString() + " LD " + getLastValue() + " / "	+ (getCurrentValueAsLong()-getLastValue());
	}
}
