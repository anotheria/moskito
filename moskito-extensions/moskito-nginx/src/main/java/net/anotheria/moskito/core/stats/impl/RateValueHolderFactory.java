package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Value holder factory that creates RateValueHolder holders.
 *
 * @author dzhmud
 */
@SuppressWarnings("unused")
public final class RateValueHolderFactory extends AbstractValueHolderFactory {

    /** Single stateless instance. */
    public static final AbstractValueHolderFactory INSTANCE = new RateValueHolderFactory();

    private RateValueHolderFactory(){}

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractValueHolder createValueHolderObject(Interval aInterval) {
        return new RateValueHolder(aInterval);
    }

}
