package net.anotheria.moskito.core.predefined;

import static net.anotheria.moskito.core.util.MBeanProducerFactory.normalize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TimerTask;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.util.BuiltinUpdater;

import org.apache.log4j.Logger;

/**
 * This is a self filling and unmodifiable list of MBean related {@link GenericStats}.
 */
public class MBeanStatsList extends ArrayList<GenericStats> {

    /**
     * Implements a {@link TypeAwareStatValue} and adds some Xtras.
     */
    private static final class XStatValue implements TypeAwareStatValue {

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
         * @return {@link MBeanAttributeInfo}
         */
        public MBeanAttributeInfo getAttribute() {
            return attribute;
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
         * @return {@link #ignoreMe}
         */
        public boolean isIgnoreMe() {
            return ignoreMe;
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
         * Sets the {@link #ignoreMe} flag.
         * 
         * @param ignoreMe
         *            flag
         */
        public void setIgnoreMe(final boolean ignoreMe) {
            this.ignoreMe = ignoreMe;
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
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1776269894160250078L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(MBeanStatsList.class);

    /**
     * {@link MBeanServer}.
     */
    private final MBeanServer server;

    /**
     * the MBean's {@link ObjectName}.
     */
    private final ObjectName mbean;

    /**
     * Constructs an instance of MBeanStatsFactory.
     * 
     * @param server
     *            {@link MBeanServer}
     * @param mbean
     *            {@link ObjectName}
     * @param enableAutoUpdate
     *            indicates if values will be updated automatically
     * @param delay
     *            the delay before first update in milliseconds
     */
    public MBeanStatsList(final MBeanServer server, final ObjectName mbean, final boolean enableAutoUpdate,
            final long delay) {
        this.server = server;
        this.mbean = mbean;
        initStatsList();
        if (enableAutoUpdate) {
            initUpdateTimer(delay);
        }
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean add(final GenericStats e) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void add(final int index, final GenericStats element) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean addAll(final Collection<? extends GenericStats> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean addAll(final int index, final Collection<? extends GenericStats> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public GenericStats remove(final int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public GenericStats set(final int index, final GenericStats element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Updates all the internal values.
     */
    public void update() {
        for (final GenericStats gs : this) {
            for (final StatValue sValue : gs.getAllValues()) {
                updateValue((XStatValue) sValue);
            }
        }
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
        return new XStatValue(attribute, StatValueFactory.createStatValue(sType, attribName,
                Constants.getDefaultIntervals()));
    }

    /**
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

        final GenericStats gs = new GenericStats(normalize(mbean.getCanonicalKeyPropertyListString()));
        for (final MBeanAttributeInfo attribute : attributes) {
            final XStatValue sValue = buildValue(attribute);
            if (sValue != null) {
                gs.putValue(sValue);
            }
        }

        if (gs.hasValues()) {
            super.add(gs);
        }
    }

    /**
     * Initalizes the update {@link TimerTask}.
     */
    private void initUpdateTimer(final long delay) {
        BuiltinUpdater.addTask(new TimerTask() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void run() {
                update();
            }

        }, delay);
    }

    /**
     * updates the stats value with current MBean values.
     * 
     * @param xsValue
     *            {@link XStatValue}
     */
    private void updateValue(final XStatValue xsValue) {
        if (xsValue.isIgnoreMe()) {
            return;
        }

        final MBeanAttributeInfo attribute = xsValue.getAttribute();
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
            // CHECKSTYLE:OFF we have to catch ALL exceptions
        } catch (final Exception e) {
        	// CHECKSTYLE:ON
            xsValue.setIgnoreMe(true); // set to ignore in future
            LOGGER.info("unable to extract value of attribute " + attribName
                    + ". it will be ignored in future. message was: " + e.getLocalizedMessage());
        }
    }
}
