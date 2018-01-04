package net.anotheria.moskito.core.producers;

import net.anotheria.moskito.core.decorators.IDecoratorFactory;

/**
 * Interface for stats that requires
 * object-specific decorators instead
 * of class-specific.
 */
public interface ICustomDecoratorStats extends IStats {

    /**
     * Returns id of decorator that can be used with
     * this stats object
     * @return id of decorator
     */
    String getDecoratorId();

    /**
     * Returns decorator factory to build new
     * decorator if it not present in registry yet.
     * @return instance of decorator factory for this stats object
     */
    IDecoratorFactory getDecoratorFactory();

}
