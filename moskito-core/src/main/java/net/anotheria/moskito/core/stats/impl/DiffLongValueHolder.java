package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * This value holder is meant for values that are continuously increasing and where you want to monitor not an absolute amount per interval but a change.
 * For example the amount of CPU Ticks used by the process. It will be continuously increasing, so the value itself won't help you, but to know the relative
 * change you need to know the diff in the value between the beginning and the end of the interval.
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
