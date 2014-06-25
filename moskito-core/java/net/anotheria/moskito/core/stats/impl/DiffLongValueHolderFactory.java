package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.06.14 01:23
 */
public class DiffLongValueHolderFactory extends AbstractValueHolderFactory {

	@Override protected AbstractValueHolder createValueHolderObject(Interval aInterval) {
		return new DiffLongValueHolder(aInterval);
	}

}