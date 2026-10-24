package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/** A long-valued gauge that keeps its current level across interval updates. */
class LevelValueHolder extends LongValueHolder {
	LevelValueHolder(Interval interval) {
		super(interval);
	}

	@Override
	public void intervalUpdated(Interval caller) {
		updateLastValueFromCurrent();
	}
}
