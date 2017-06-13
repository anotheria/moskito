package net.anotheria.moskito.extensions.monitoring.stats;


import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.extensions.monitoring.metrics.IGenericMetrics;
import net.anotheria.moskito.extensions.monitoring.parser.StatusData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GenericStats abstract class.
 * Contains StatValues for all(or just configured set) of specified metrics.
 *
 * @param <T> type of metrics that this class takes care of.
 * @author dzhmud
 */
public abstract class GenericStats<T extends IGenericMetrics> extends AbstractStats {

    /** StatValue stats organized in Map for faster access. */
    private final Map<String, StatValue> statValueMap = new LinkedHashMap<>();

    /** List of metrics that this stats instance handles. */
    private final List<T> metrics;

    /** List of metric names. */
    private final List<String> valueNames;

    /** Flag signaling that this stats object was never updated. Used by decorator.*/
    private volatile boolean neverUpdated = true;

    /**
     * Creates a new GenericStats object with given name.
     * @param aName name of the stats object.
     */
    protected GenericStats(String aName, List<T> metrics) {
        super(aName);
        this.metrics = Collections.unmodifiableList(metrics);
        for (T metric : metrics) {
            statValueMap.put(metric.getCaption(), metric.createStatValue());
        }
        valueNames = Collections.unmodifiableList(new ArrayList<>(statValueMap.keySet()));
        addStatValues(statValueMap.values().toArray(new StatValue[statValueMap.size()]));
    }

    /**
     * Get 'neverUpdated' status of this GenericStats.
     * @return <tt>false</tt> until first values update and <tt>true</tt> - after.
     */
    public boolean isNeverUpdated() {
        return neverUpdated;
    }

    /**
     * Notify that this stats was updated.
     */
    @SuppressWarnings("unused")
    protected void notifyStatsWasUpdated() {
        this.neverUpdated = false;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getAvailableValueNames() {
        return valueNames;
    }

    /**
     * Get list of all metrics available in this stats.
     * @return list of all metrics that this stats is handling.
     */
    public List<T> getAvailableMetrics() {
        return metrics;
    }

    /**
     * Returns current class logger.
     * @return logger for the current class.
     */
    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    /**
     * Update this stats with new data contained in StatusData parameter.
     * @param status {@link StatusData} object containing new data.
     * @throws IllegalArgumentException if given status is null.
     */
    public void update(StatusData<T> status) {
        if (status == null) {
            throw new IllegalArgumentException("Received null as status data!");
        }
        neverUpdated = false;
        handleStatsResetIfNeeded(status);
        for (T metric : metrics) {
            Object value = status.get(metric);
            if (value == null) {
                getLogger().warn("StatusData contains no value for metric '"+metric.getCaption() + "'.");
                continue;
            }
            StatValue stat = getMonitoredStatValue(metric);
            switch (metric.getType()) {
                case INT: stat.setValueAsInt(Integer.class.cast(value)); break;
                case DOUBLE: stat.setValueAsDouble(Double.class.cast(value)); break;
                case STRING: stat.setValueAsString(value.toString()); break;
                case COUNTER:
                case DIFFLONG:
                case LONG: stat.setValueAsLong(Long.class.cast(value));break;
                default: stat.setValueAsString(value.toString()); break;
            }
        }
    }

    /**
     * In case monitored app/server was restarted and its metrics were reset,
     * DIFFLONG stats need special resetting to not break any graphics.
     * So, basically, child class should check any constantly increasing metric and if its new value
     * is less than stored one, reset all statValues of type DIFFLONG and its derivatives.
     * Make sure that corresponding metric is always present.
     *
     * @param status latest status data.
     */
    protected abstract void handleStatsResetIfNeeded(StatusData<T> status);

    /**
     * Retrieve single StatValue from the internal cache.
     * @param metric Metric from which StatValue was created.
     * @return StatValue from the internal cache or null if there is no corresponding StatValue.
     */
    protected StatValue getStatValue(T metric) {
        return statValueMap.get(metric.getCaption());
    }

    /**
     * Retrieve single StatValue from the internal cache. Method checks if statValue is present and
     * throws {@link IllegalArgumentException} if it's not there.
     * @param metric Metric from which StatValue was created.
     * @return StatValue from the internal cache.
     * @throws IllegalArgumentException if internal cache does not contain statValue for the given metric.
     */
    protected final StatValue getMonitoredStatValue(T metric) {
        StatValue statValue = getStatValue(metric);
        if (statValue == null)
            throw new IllegalArgumentException("Metric '"+metric.getCaption()+"' is not monitored!");
        return statValue;
    }

    /**
     * Get value of provided metric for the given interval as long value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public long getStatValueAsLong(T metric, String interval) {
        if (metric.isRateMetric()) {
            return (long)(getStatValueAsDouble(metric, interval));
        }
        return getMonitoredStatValue(metric).getValueAsLong(interval);
    }

    /**
     * Get value of provided metric for the given interval as int value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public int getStatValueAsInteger(T metric, String interval) {
        if (metric.isRateMetric()) {
            return (int)(getStatValueAsDouble(metric, interval));
        }
        return getMonitoredStatValue(metric).getValueAsInt(interval);
    }

    /**
     * Get value of provided metric for the given interval as String value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public String getStatValueAsString(T metric, String interval) {
        if (metric.isRateMetric()) {
            return String.valueOf(getStatValueAsDouble(metric, interval));
        }
        return getMonitoredStatValue(metric).getValueAsString(interval);
    }

    /**
     * Get value of provided metric for the given interval as double value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     * */
    public double getStatValueAsDouble(T metric, String interval) {
        StatValue statValue = getMonitoredStatValue(metric);
        if (metric.isRateMetric()) {
            //check for DEFAULT can be removed when same check removed from statValueImpl
            if("default".equals(interval)) {
                interval = DefaultIntervals.ONE_MINUTE.getName();
            }
            double result = statValue.getValueAsLong(interval);
            long duration = IntervalRegistry.getInstance().getInterval(interval).getLength();
            if (duration > 0)
                result /= duration;
            return result;
        }
        return statValue.getValueAsDouble(interval);
    }

    /**
     * {@inheritDoc}.
     * Called by Accumulator.
     */
    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
        if (valueName == null || valueName.isEmpty())
            throw new IllegalArgumentException("Value name can not be empty");
        StatValue statValue = statValueMap.get(valueName);
        if (statValue != null) {
            for (T metric : metrics) {
                if (valueName.equals(metric.getCaption()))
                    return getStatValueAsString(metric, intervalName);
            }
        }
        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }


    @Override
    public String toStatsString(String intervalName, TimeUnit timeUnit) {
        StringBuilder b = new StringBuilder();
        b.append(getName()).append(" [");
        for (T metric : metrics) {
            b.append(" ").append(metric.getCaption()).append(": ").append(getStatValueAsString(metric, intervalName)).append(";");
        }
        b.append(']');
        return b.toString();
    }

    @Override
    public void destroy() {
        super.destroy();
        statValueMap.clear();
    }

}

