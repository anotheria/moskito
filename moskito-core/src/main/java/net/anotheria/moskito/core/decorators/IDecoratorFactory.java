package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.producers.ICustomDecoratorStats;

/**
 * Decorator factory interface for object-specific decorators
 * @param <S> class of decorator stats that this factory builds
 */
public interface IDecoratorFactory<S extends ICustomDecoratorStats> {

    /**
     * Builds new instance of decorator for specific stats object
     *
     * @param stats stats object to build decorator
     * @return new instance of decorator to use it with given stats
     */
    IDecorator<S> buildDecorator(S stats);

}
