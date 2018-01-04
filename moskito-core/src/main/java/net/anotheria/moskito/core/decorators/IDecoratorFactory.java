package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.producers.ICustomDecoratorStats;

import java.io.Serializable;

/**
 * Decorator factory interface
 * @param <S> class of decorator stats that this factory builds
 */
public interface IDecoratorFactory<S extends ICustomDecoratorStats> extends Serializable {

    /**
     * Builds new instance of decorator
     *
     * @return new instance of decorator
     */
    IDecorator<S> buildDecorator();

}
