package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.decorators.IDecoratorFactory;
import net.anotheria.moskito.core.decorators.mbean.MBeanDecoratorFactory;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.producers.ICustomDecoratorStats;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.util.BuiltinUpdater;

import javax.management.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.anotheria.moskito.core.util.NewMBeanProducerFactory.normalize;

public class MBeanStats extends GenericStats implements ICustomDecoratorStats {

    private Map<String, String> statsValuesDescriptions = new HashMap<>();

    /**
     * @param attributeInfo
     *            {@link MBeanAttributeInfo}
     * @return {@link StatValueTypes}
     */
    private static StatValueTypes getStatValueType(final MBeanAttributeInfo attributeInfo) {

        String attributeType = attributeInfo.getType();

        if(attributeType == null)
            return null;

        switch (attributeInfo.getType()) {

            case "long":
            case "java.lang.Long":
                return StatValueTypes.LONG;

            case "int":
            case "java.lang.Integer":
                return StatValueTypes.INT;

            case "double":
            case "java.lang.Double":
                return StatValueTypes.DOUBLE;

            case "String":
            case "java.lang.String":
            case "boolean":
            case "java.lang.Boolean":
                return StatValueTypes.STRING;

            default:
                return null;

        }

    }

    private static boolean updateValue(
            final TypeAwareStatValue value, final MBeanServer server,
            final MBeanAttributeInfo attributeInfo,
            final ObjectName name) {

        final String attributeName = attributeInfo.getName();

        try {

            final Object mBeanValue = server.getAttribute(name, attributeName);

            switch (value.getType()) {

                case LONG:
                    value.setValueAsLong((Long) mBeanValue);
                    break;
                case INT:
                    value.setValueAsInt((Integer) mBeanValue);
                    break;
                case DOUBLE:
                    value.setValueAsDouble((Double) mBeanValue);
                    break;
                case STRING:
                    value.setValueAsString(mBeanValue == null ? "null" : mBeanValue.toString());
                    break;

                default:
                    return false;

            }

            return true;

        } catch ( // TODO : log it
                JMException |          // Thrown on mbean value extraction fail
                JMRuntimeException |   // Thrown on mbean value extraction fail
                ClassCastException |   // Thrown on expected and actual value type mismatch
                NullPointerException e // Thrown on null mbean value
                ) {
            return false;
        }

    }

    private static TypeAwareStatValue buildValue(MBeanAttributeInfo attributeInfo) {

        final StatValueTypes statType = getStatValueType(attributeInfo);

        if (statType == null) {
            return null;
        }

        return StatValueFactory.createStatValue(
                statType,
                attributeInfo.getName(),
                Constants.getDefaultIntervals()
        );

    }

    // TODO : MAKE VALUES UPDATING CODE MORE CLEAR
    public static MBeanStats createMBeanStats(
            final MBeanServer server, final ObjectName mbean,
            final boolean enableAutoUpdate, final long delay)
            throws IntrospectionException, InstanceNotFoundException, ReflectionException {

        final MBeanAttributeInfo[] attributeInfos = server.getMBeanInfo(mbean).getAttributes();

        final Map<MBeanAttributeInfo, TypeAwareStatValue> readableValues = new ConcurrentHashMap<>();

        for (final MBeanAttributeInfo attributeInfo : attributeInfos)
            if(attributeInfo.isReadable()) {

            TypeAwareStatValue statValue =
                    buildValue(attributeInfo);

            if (statValue != null && updateValue(statValue, server, attributeInfo, mbean)) {
                readableValues.put(attributeInfo, statValue);
            }

        }

        if(!readableValues.isEmpty()) {

            // Starting updater task for mbean values if required
            if (enableAutoUpdate) BuiltinUpdater.addTask(new TimerTask() {

                @Override
                public void run() {
                    for(Map.Entry<MBeanAttributeInfo, TypeAwareStatValue> valueEntry
                            : readableValues.entrySet())

                        updateValue(valueEntry.getValue(), server, valueEntry.getKey(), mbean);

                }

            }, delay);

            return new MBeanStats(
                    normalize(mbean.getCanonicalKeyPropertyListString()),
                    readableValues
            );

        }
        else
            return null;

    }

    private MBeanStats(String statsName, Map<MBeanAttributeInfo, TypeAwareStatValue> values) {
        super(statsName);

        for(Map.Entry<MBeanAttributeInfo, TypeAwareStatValue> valueEntry : values.entrySet()) {

            final TypeAwareStatValue value = valueEntry.getValue();
            final MBeanAttributeInfo mBeanAttributeInfo = valueEntry.getKey();

            putValue(value);
            statsValuesDescriptions.put(value.getName(), mBeanAttributeInfo.getDescription());

        }

    }

    public String getValueDescriptionByName(String valueName) {
        return statsValuesDescriptions.get(valueName);
    }

    @Override
    public String getDecoratorId() {
        return getName();
    }

    @Override
    public IDecoratorFactory getDecoratorFactory() {
        return MBeanDecoratorFactory.INSTANCE;
    }

    public Collection<TypeAwareStatValue> getAllValues() {
        return super.getAllValues();
    }
}
