package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/** A current-request gauge keeps in-flight requests across interval boundaries. */
class CurrentRequestsValueHolder extends LongValueHolder {
	CurrentRequestsValueHolder(Interval interval) {
		super(interval);
	}

	@Override
	public void intervalUpdated(Interval caller) {
		updateLastValueFromCurrent();
	}
}
