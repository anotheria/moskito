package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

import java.util.function.LongSupplier;

/** A long holder whose next interval starts from a live, supplied value. */
class IntervalStartValueHolder extends LongValueHolder {
	private final LongSupplier intervalStartValueSupplier;

	IntervalStartValueHolder(Interval interval, LongSupplier intervalStartValueSupplier) {
		super(interval);
		this.intervalStartValueSupplier = intervalStartValueSupplier;
		long initialValue = intervalStartValueSupplier.getAsLong();
		setDefaultValueAsLong(initialValue);
		setValueAsLong(initialValue);
	}

	@Override
	public void intervalUpdated(Interval caller) {
		setDefaultValueAsLong(intervalStartValueSupplier.getAsLong());
		super.intervalUpdated(caller);
	}
}
