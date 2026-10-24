package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/** Creates interval-registered holders for level-valued statistics. */
class LevelValueHolderFactory extends AbstractValueHolderFactory {
	@Override
	protected AbstractValueHolder createValueHolderObject(Interval interval) {
		return new LevelValueHolder(interval);
	}
}
