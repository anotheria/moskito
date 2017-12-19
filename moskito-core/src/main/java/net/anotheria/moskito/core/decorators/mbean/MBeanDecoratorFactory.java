package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.IDecoratorFactory;
import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

import java.util.*;

/**
 * Factory for creating mbean decorator for specific mbean stats object
 */
public class MBeanDecoratorFactory implements IDecoratorFactory<MBeanStats> {

    private static final long serialVersionUID = 8571967068028956675L;

    /**
     * Contains attributes descriptions for certain mbean stats object
     */
    private final Map<String, String> attributesDescriptions;

    /**
     * Creates object-specific decorator factory
     * for given mbean stats object
     * @param stats stats object to create factory
     */
    public MBeanDecoratorFactory(MBeanStats stats) {

        attributesDescriptions = new HashMap<>();

        for (TypeAwareStatValue statValue : stats.getAllValues()) {
            String valueName = statValue.getName();
            attributesDescriptions.put(
                    valueName,
                    stats.getValueDescriptionByName(valueName)
            );
        }

    }

    @Override
    public IDecorator buildDecorator() {

        final List<StatCaptionBean> captions = new LinkedList<>();

        for (Map.Entry<String, String> description : attributesDescriptions.entrySet()) {

            final String valueName = description.getKey();
            final String valueDescription = description.getValue();

            captions.add(new StatCaptionBean(
                    valueName,
                    valueDescription,
                    valueDescription
            ));

        }

        return new MBeanDecorator(captions);

    }

}
