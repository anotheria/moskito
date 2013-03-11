/**
 * (c) 2012 König-Software GmbH - http://www.koenig-software.de
 */
package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;

/**
 * A factory which creates one {@link SimpleStatsProducer} per plattform MBean.
 * 
 * @author Michael König
 */
public final class MBeanProducerFactory {

    /**
     * Factory class for MBean related {@link GenericStats}. Although it seems to be useless, it
     * implements {@link IOnDemandStatsFactory} interface.
     */
    private static class MBeanStatsFactory implements IOnDemandStatsFactory<GenericStats> {

        private final MBeanServer server;
        private final ObjectName mbean;

        /**
         * Stats container
         */
        private final List<GenericStats> statsList = new ArrayList<GenericStats>();

        /**
         * Constructs an instance of MBeanStatsFactory.
         * 
         * @param server
         *            {@link MBeanServer}
         * @param mbean
         *            {@link ObjectName}
         */
        public MBeanStatsFactory(final MBeanServer server, final ObjectName mbean) {
            this.server = server;
            this.mbean = mbean;
            initStatsList();
            initUpdateTimer();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public GenericStats createStatsObject(final String name) {
            return new GenericStats(name);
        }

        /**
         * @param attribute
         *            {@link MBeanAttributeInfo}
         * @return {@link StatValue}
         */
        private XStatValue buildValue(final MBeanAttributeInfo attribute) {
            final StatValueTypes sType = getStatValueType(attribute);
            if (sType == null) {
                return null;
            }

            final String attribName = attribute.getName();
            return new XStatValue(attribute,
                    StatValueFactory.createStatValue(sType, attribName,
                                                     Constants.getDefaultIntervals()));
        }

        /**
         * @param server
         *            {@link MBeanServer}
         * @param mbean
         *            {@link ObjectName}
         * @return {@link Collection} of {@link MBeanAttributeInfo}s
         */
        private Collection<MBeanAttributeInfo> getAttributes() {
            final Collection<MBeanAttributeInfo> attributes = new ArrayList<MBeanAttributeInfo>();

            try {
                final MBeanAttributeInfo[] all = server.getMBeanInfo(mbean).getAttributes();

                for (final MBeanAttributeInfo attribute : all) {
                    attributes.add(attribute);
                }

            } catch (final IntrospectionException e) {
                LOGGER.info("unable to determine attributes of MBean: " + mbean, e);

            } catch (final InstanceNotFoundException e) {
                LOGGER.info("unable to determine attributes of MBean: " + mbean, e);

            } catch (final ReflectionException e) {
                LOGGER.info("unable to determine attributes of MBean: " + mbean, e);
            }

            return attributes;
        }

        /**
         * @param attribute
         *            {@link MBeanAttributeInfo}
         * @return {@link StatValueTypes}
         */
        private StatValueTypes getStatValueType(final MBeanAttributeInfo attribute) {
            final String type = attribute.getType();
            if ("long".equals(type)) {
                return StatValueTypes.LONG;
            }
            if ("int".equals(type)) {
                return StatValueTypes.INT;
            }
            if ("double".equals(type)) {
                return StatValueTypes.DOUBLE;
            }
            if ("String".equals(type) || "boolean".equals(type)) {
                return StatValueTypes.STRING;
            }
            return null;
        }

        /**
         * initializes the {@link GenericStats} values ({@link #statsList}).
         */
        private void initStatsList() {
            final Collection<MBeanAttributeInfo> attributes = getAttributes();
            if (attributes.isEmpty()) {
                // quick exit
                return;
            }

            final GenericStats gs = new GenericStats(
                    normalize(mbean.getCanonicalKeyPropertyListString()));
            for (final MBeanAttributeInfo attribute : attributes) {
                final XStatValue sValue = buildValue(attribute);
                if (sValue != null) {
                    gs.putValue(sValue);
                }
            }

            if (gs.hasValues()) {
                statsList.add(gs);
            }
        }

        /**
         * Initalizes the update {@link TimerTask}.
         */
        private void initUpdateTimer() {
            BuiltinUpdater.addTask(new TimerTask() {

                /**
                 * {@inheritDoc}
                 */
                @Override
                public void run() {
                    updateStats();
                }

            }, 15000); // starte erst nach 15 sekunden (TODO: sollte konfigurierbar sein)
        }

        /**
         * update all the stats values in {@link #statsList}
         */
        private void updateStats() {
            for (final GenericStats gs : statsList) {
                for (final StatValue sValue : gs.getAllValues()) {
                    updateValues((XStatValue) sValue);
                }
            }
        }

        /**
         * updates the stats value with current MBean values.
         * 
         * @param xsValue
         *            {@link XStatValue}
         */
        private void updateValues(final XStatValue xsValue) {
            if (xsValue.ignoreMe) {
                return;
            }

            final MBeanAttributeInfo attribute = xsValue.attribute;
            if (!attribute.isReadable()) {
                return;
            }

            final String attribName = attribute.getName();
            try {
                final Object value = server.getAttribute(mbean, attribName);
                if (value instanceof Long) {
                    xsValue.setValueAsLong((Long) value);

                } else if (value instanceof Integer) {
                    xsValue.setValueAsInt((Integer) value);

                } else if (value instanceof Double) {
                    xsValue.setValueAsDouble((Double) value);

                } else {
                    xsValue.setValueAsString(value == null ? "null" : value.toString());
                }

            } catch (final Exception e) {
                xsValue.ignoreMe = true; // set to ignore in future
                LOGGER
                    .info("unable to extract value of attribute " + attribName
                            + ". it will be ignored in future. message was: "
                            + e.getLocalizedMessage());
            }
        }
    }

    /**
     * Implements a {@link TypeAwareStatValue} and adds some Xtras.
     * 
     * @author Michael König
     */
    private static class XStatValue implements TypeAwareStatValue {

        private final MBeanAttributeInfo attribute;
        private final TypeAwareStatValue delegate;
        private boolean ignoreMe;

        /**
         * Constructs an instance of XStatValue.
         * 
         * @param attribute
         *            {@link MBeanAttributeInfo}
         * @param delegate
         *            {@link TypeAwareStatValue}
         */
        private XStatValue(final MBeanAttributeInfo attribute, final TypeAwareStatValue delegate) {
            this.attribute = attribute;
            this.delegate = delegate;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void addInterval(final Interval aInterval) {
            delegate.addInterval(aInterval);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void decrease() {
            delegate.decrease();
        }

        /**
         * {@inheritDoc}
         */

        @Override
        public void decreaseByDouble(final double aValue) {
            delegate.decreaseByDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void decreaseByInt(final int aValue) {
            delegate.decreaseByInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void decreaseByLong(final long aValue) {
            delegate.decreaseByLong(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getName() {
            return delegate.getName();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public StatValueTypes getType() {
            return delegate.getType();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getValueAsDouble() {
            return delegate.getValueAsDouble();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public double getValueAsDouble(final String aIntervalName) {
            return delegate.getValueAsDouble(aIntervalName);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getValueAsInt() {
            return delegate.getValueAsInt();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getValueAsInt(final String aIntervalName) {
            return delegate.getValueAsInt(aIntervalName);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getValueAsLong() {
            return delegate.getValueAsLong();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long getValueAsLong(final String aIntervalName) {
            return delegate.getValueAsLong(aIntervalName);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValueAsString() {
            return delegate.getValueAsString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getValueAsString(final String anIntervalName) {
            return delegate.getValueAsString(anIntervalName);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void increase() {
            delegate.increase();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void increaseByDouble(final double aValue) {
            delegate.increaseByDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void increaseByInt(final int aValue) {
            delegate.increaseByInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void increaseByLong(final long aValue) {
            delegate.increaseByLong(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() {
            delegate.reset();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setDefaultValueAsDouble(final double aValue) {
            delegate.setDefaultValueAsDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setDefaultValueAsInt(final int aValue) {
            delegate.setDefaultValueAsInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setDefaultValueAsLong(final long aValue) {
            delegate.setDefaultValueAsLong(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueAsDouble(final double aValue) {
            delegate.setValueAsDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueAsInt(final int aValue) {
            delegate.setValueAsInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueAsLong(final long aValue) {
            delegate.setValueAsLong(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueAsString(final String aValue) {
            delegate.setValueAsString(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfGreaterThanCurrentAsDouble(final double aValue) {
            delegate.setValueIfGreaterThanCurrentAsDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfGreaterThanCurrentAsInt(final int aValue) {
            delegate.setValueIfGreaterThanCurrentAsInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfGreaterThanCurrentAsLong(final long aValue) {
            delegate.setValueIfGreaterThanCurrentAsLong(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfLesserThanCurrentAsDouble(final double aValue) {
            delegate.setValueIfLesserThanCurrentAsDouble(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfLesserThanCurrentAsInt(final int aValue) {
            delegate.setValueIfLesserThanCurrentAsInt(aValue);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void setValueIfLesserThanCurrentAsLong(final long aValue) {
            delegate.setValueIfLesserThanCurrentAsLong(aValue);
        }

    }

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MBeanProducerFactory.class);

    /**
     * Build a number of {@link SimpleStatsProducer}s, one for each single MBean which is currently
     * registerd in actual JVM.
     * 
     * @param register
     *            set this to true to automatically register all the producers
     */
    public static Iterable<SimpleStatsProducer<GenericStats>> buildProducers(final boolean register) {
        final Collection<SimpleStatsProducer<GenericStats>> result = new ArrayList<SimpleStatsProducer<GenericStats>>();

        final IProducerRegistry producerRegistry = ProducerRegistryFactory
            .getProducerRegistryInstance();

        for (final MBeanServer server : getServers()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("handle MBean-Server: " + server.toString());
            }

            for (final ObjectName mbean : getObjectNames(server)) {
                final String name = mbean.getCanonicalName();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("handle MBean: " + name);
                }

                final String producerId = normalize(name);
                final String subsystem = normalize(mbean.getDomain());

                final MBeanStatsFactory factory = new MBeanStatsFactory(server, mbean);
                final SimpleStatsProducer<GenericStats> p = new SimpleStatsProducer<GenericStats>(
                        producerId, "MBean", subsystem, factory.statsList);
                result.add(p);

                if (register) {
                    producerRegistry.registerProducer(p);
                }
            }
        }

        return result;
    }

    /**
     * @param server
     *            {@link MBeanServer}
     * @return all MBeans within given {@link MBeanServer}
     */
    private static Iterable<ObjectName> getObjectNames(final MBeanServer server) {
        return server.queryNames(null, null);
    }

    /**
     * @return all registered {@link MBeanServer}s in this JVM are returned
     */
    private static Iterable<MBeanServer> getServers() {
        ManagementFactory.getPlatformMBeanServer();
        return MBeanServerFactory.findMBeanServer(null);
    }

    /**
     * Helper to ensure that given string can be handled in web-view (replaces some invalid
     * characters).
     * 
     * @param s
     *            the input string to normalize
     * @return the normalized string
     */
    private static String normalize(final String s) {
        if (StringUtils.isBlank(s)) {
            return "unspecific";
        }
        return s
            .replace(':', '/')
                .replace('=', '-')
                .replace(',', '|')
                .replace('"', '\'')
                .replace('#', '-');
    }

    /**
     * Hidden constructor - this is a utility class.
     */
    private MBeanProducerFactory() {
        super();
    }
}
