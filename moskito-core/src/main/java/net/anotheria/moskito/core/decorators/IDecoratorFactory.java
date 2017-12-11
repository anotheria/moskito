package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.producers.IStats;

public interface IDecoratorFactory<S extends IStats> {

    IDecorator buildDecorator(S stats);

}
