package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

import java.util.function.LongSupplier;

/** Creates holders for the min or max of a sampled level. */
class SampledExtremeValueHolderFactory extends AbstractValueHolderFactory {
	private final boolean max;
	private final LongSupplier level;

	SampledExtremeValueHolderFactory(boolean max, LongSupplier level) {
		this.max = max;
		this.level = level;
	}

	@Override
	protected AbstractValueHolder createValueHolderObject(Interval interval) {
		return new SampledExtremeValueHolder(interval, max, level);
	}
}
