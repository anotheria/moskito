package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static net.anotheria.moskito.core.util.MBeanProducerFactory.normalize;

/**
 * Factory for mbean stats
 */
public class MBeanStatsFactory {

    private static final Logger log = LoggerFactory.getLogger(MBeanStatsFactory.class);

    /**
     * Converts string type representation of mbean attribute to
     * {@link StatValueTypes}.
     *
     * @param attributeInfo {@link MBeanAttributeInfo}
     * @return {@link StatValueTypes}.
     *      In case attribute type is not of primitive data type returns null.
     *
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

    /**
     * Updates value of given value holder
     *
     * @param value value holder to update
     * @param server mbean server to extract mbean attribute
     * @param attributeInfo mbean attribute identifier
     * @param mbeanName mbean identifier
     * @return is value was updated
     */
    private static boolean updateValue(
            final TypeAwareStatValue value, final MBeanServer server,
            final MBeanAttributeInfo attributeInfo,
            final ObjectName mbeanName) {

        final String attributeName = attributeInfo.getName();

        try {

            final Object mBeanValue = server.getAttribute(mbeanName, attributeName);

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

        } catch (
                    JMException |          // Thrown on mbean value extraction fail
                    JMRuntimeException |   // Thrown on mbean value extraction fail
                    ClassCastException |   // Thrown on expected and actual value type mismatch
                    NullPointerException e // Thrown on null mbean value
                ) {
            log.debug(
                    "Failed to update mbean stats value named " +
                    attributeName + " of " + mbeanName.getCanonicalName() + " mbean",
                    e);
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

    /**
     * Creates new instance of mbean stats
     * @param server mbean server to extract mbean attributes
     * @param mbean mbean identifier to extract mbean attributes
     * @param enableAutoUpdate enables values auto update if set to true
     * @param delay auto update delay
     * @return new instance of mbean stats or null if any of given mbean attributes can not be read
     * @throws JMException on mbean extraction fail
     */
    public static MBeanStats createMBeanStats(
            final MBeanServer server, final ObjectName mbean,
            final boolean enableAutoUpdate, final long delay)
            throws JMException {

        final MBeanAttributeInfo[] attributeInfos = server.getMBeanInfo(mbean).getAttributes();

        final Map<MBeanAttributeInfo, TypeAwareStatValue> readableValues = new ConcurrentHashMap<>();

        for (final MBeanAttributeInfo attributeInfo : attributeInfos)
            if(attributeInfo.isReadable()) {

                TypeAwareStatValue statValue =
                        buildValue(attributeInfo);

                if (
                        statValue != null &&
                                // First value update is required to ensure that
                                // value can be extracted from mbean
                                updateValue(statValue, server, attributeInfo, mbean)
                        ) {
                    readableValues.put(attributeInfo, statValue);
                }
                else {
                    log.info("Failed to build stats value from attribute named " +
                    attributeInfo.getName() + " of " + mbean.getCanonicalName() + " mbean.");
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
        return null;

    }

}
