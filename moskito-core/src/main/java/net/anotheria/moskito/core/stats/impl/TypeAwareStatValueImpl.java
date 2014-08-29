package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

/**
 * Extends the {@link StatValueImpl} type with a {@link StatValueTypes} value.
 * 
 * @author Michael König
 */
public class TypeAwareStatValueImpl extends StatValueImpl implements TypeAwareStatValue {

    /**
     * the {@link StatValueTypes}.
     */
    private final StatValueTypes type;

    /**
     * Constructs an instance of TypeAwareStatValueImpl.
     * 
     * @param aName
     *            the name of the statistic value
     * @param svType
     *            the {@link StatValueTypes}
     * @param aFactory
     *            the factory to create ValueHolder instances on Interval registration
     */
    public TypeAwareStatValueImpl(final String aName, final StatValueTypes svType, final IValueHolderFactory aFactory) {
        super(aName, aFactory);
        this.type = svType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatValueTypes getType() {
        return type;
    }

}
