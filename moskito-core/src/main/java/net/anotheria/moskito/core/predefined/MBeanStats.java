package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.decorators.IDecoratorFactory;
import net.anotheria.moskito.core.decorators.mbean.MBeanDecoratorFactory;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.producers.ICustomDecoratorStats;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

import javax.management.MBeanAttributeInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * Stats object for mbean values
 */
public class MBeanStats extends GenericStats implements ICustomDecoratorStats<MBeanStats> {

    /**
     * Stores descriptions of mbean attributes
     * that been collected from mbean server.
     */
    private Map<String, String> statsValuesDescriptions = new HashMap<>();

    /**
     * Constructor that applies name of stats
     * and map with mbean attribute infos as keys
     * with it associated values holders as values.
     * @param statsName name of new stats
     * @param values new stats values
     */
    public MBeanStats(String statsName, Map<MBeanAttributeInfo, TypeAwareStatValue> values) {
        super(statsName);

        for(Map.Entry<MBeanAttributeInfo, TypeAwareStatValue> valueEntry : values.entrySet()) {

            final TypeAwareStatValue value = valueEntry.getValue();
            final MBeanAttributeInfo mBeanAttributeInfo = valueEntry.getKey();

            putValue(value);
            statsValuesDescriptions.put(value.getName(), mBeanAttributeInfo.getDescription());

        }

    }

    /**
     * Returns description, that been obtained from
     * mbean, of stats value by it name
     * @param valueName name of a value to get description
     * @return description of a value.
     *         Returns null if description for given value name is not present in that
     *         stats object
     */
    public String getValueDescriptionByName(String valueName) {
        return statsValuesDescriptions.get(valueName);
    }

    @Override
    public String getDecoratorId() {
        return getName();
    }

    @Override
    public IDecoratorFactory<MBeanStats> getDecoratorFactory() {
        return MBeanDecoratorFactory.INSTANCE;
    }

}
