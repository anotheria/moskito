/**
 * (c) 2012 König-Software GmbH - http://www.koenig-software.de
 */
package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * This ValueHolderFactory implementation creates {@link DoubleValueHolder} instances.
 * 
 * @author Michael König
 */
public class DoubleValueHolderFactory extends AbstractValueHolderFactory {

    /**
     * Constructs an instance of DoubleValueHolderFactory.
     * 
     */
    public DoubleValueHolderFactory() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractValueHolder createValueHolderObject(Interval aInterval) {
        return new DoubleValueHolder(aInterval);
    }

}
