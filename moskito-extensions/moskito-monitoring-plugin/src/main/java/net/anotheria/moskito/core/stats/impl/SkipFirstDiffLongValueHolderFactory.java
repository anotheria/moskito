package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates SkipFirstDiffLongValueHolder instances.
 * @author dzhmud
 * @see SkipFirstDiffLongValueHolder
 */
public final class SkipFirstDiffLongValueHolderFactory extends DiffLongValueHolderFactory {

    /** Single stateless instance. */
    public static final SkipFirstDiffLongValueHolderFactory INSTANCE = new SkipFirstDiffLongValueHolderFactory();

    private SkipFirstDiffLongValueHolderFactory(){}

    @Override protected SkipFirstDiffLongValueHolder createValueHolderObject(Interval aInterval) {
        return new SkipFirstDiffLongValueHolder(aInterval);
    }

}
