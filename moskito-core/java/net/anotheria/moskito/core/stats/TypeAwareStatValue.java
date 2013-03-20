package net.anotheria.moskito.core.stats;

/**
 * Extends the {@link StatValue} interface with a {@link StatValueTypes} value.
 * 
 * @author Michael König
 */
public interface TypeAwareStatValue extends StatValue {

    /**
     * returns the related {@link StatValueTypes} value.
     */
    StatValueTypes getType();

}
