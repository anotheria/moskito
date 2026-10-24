package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

import java.util.function.LongSupplier;

/** Creates holders that reset to a supplied live value at each interval boundary. */
class IntervalStartValueHolderFactory extends AbstractValueHolderFactory {
	private final LongSupplier intervalStartValueSupplier;

	IntervalStartValueHolderFactory(LongSupplier intervalStartValueSupplier) {
		this.intervalStartValueSupplier = intervalStartValueSupplier;
	}

	@Override
	protected AbstractValueHolder createValueHolderObject(Interval interval) {
		return new IntervalStartValueHolder(interval, intervalStartValueSupplier);
	}
}
