package net.anotheria.moskito.core.producers;

import net.anotheria.moskito.core.decorators.IDecoratorFactory;

public interface ICustomDecoratorStats {

    String getDecoratorId();
    IDecoratorFactory getDecoratorFactory();

}
