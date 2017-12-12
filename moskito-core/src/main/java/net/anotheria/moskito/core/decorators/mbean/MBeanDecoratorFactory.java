package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.IDecoratorFactory;
import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Factory for creating mbean decorator for specific mbean stats object
 */
public class MBeanDecoratorFactory implements IDecoratorFactory<MBeanStats> {

    public static final MBeanDecoratorFactory INSTANCE = new MBeanDecoratorFactory();

    @Override
    public IDecorator buildDecorator(MBeanStats stats) {

        final Collection<TypeAwareStatValue> statsValues = stats.getAllValues();
        final List<StatCaptionBean> captions = new LinkedList<>();

        for (TypeAwareStatValue statsValue : statsValues) {

            final String valueName = statsValue.getName();
            final String valueDescription = stats.getValueDescriptionByName(valueName);

            captions.add(new StatCaptionBean(
                    valueName,
                    valueDescription,
                    valueDescription
            ));

        }

        return new MBeanDecorator(captions);

    }

}
