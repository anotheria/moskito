package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.producers.ICustomDecoratorStats;
import net.anotheria.moskito.core.producers.IStats;

import java.io.Serializable;

/**
 * Identifier of decorator.
 * Used to identify decorator needed by
 * certain stats object.
 * If stats object require object-specific decorator
 * this decorator name will contain decorator id and
 * decorator factory for stats object
 */
public class DecoratorName implements Serializable {

    private static final long serialVersionUID = -7449768549625929124L;

    /**
     * Stats class of decorator
     */
    private String statsClass;
    /**
     * Id of object-specific decorator required by stats object.
     * null if decorator name if not stats object specific
     */
    private String decoratorId;
    /**
     * Factory for object-specific decorator
     */
    private IDecoratorFactory factory;
    /**
     * Is decorator stats object specific
     */
    private boolean customDecorator = false;

    /**
     * Creates new instance of DecoratorName
     * for given stats object
     * @param stats stats object to create DecoratorName for
     */
    public DecoratorName(IStats stats) {

        this.statsClass = stats.getClass().getCanonicalName();

        if(stats instanceof ICustomDecoratorStats) {

            ICustomDecoratorStats customDecoratorStats =
                    ((ICustomDecoratorStats) stats);

            this.customDecorator = true;
            this.decoratorId = customDecoratorStats.getDecoratorId();
            this.factory = customDecoratorStats.getDecoratorFactory();

        }

    }

    public IDecoratorFactory getFactory() {
        return factory;
    }

    public String getDecoratorId() {
        return decoratorId;
    }

    public String getStatsClass() {
        return statsClass;
    }

    public boolean isCustomDecorator() {
        return customDecorator;
    }

}
