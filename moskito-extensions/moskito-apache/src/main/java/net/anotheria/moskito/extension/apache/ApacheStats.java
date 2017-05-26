package net.anotheria.moskito.extension.apache;


import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.extension.apache.decorator.ApacheStatsDecorator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ApacheStats containing all the statValues we gather from apache.
 *
 * @author dzhmud
 * @see ApacheMetrics
 */
public class ApacheStats extends AbstractStats {

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(ApacheStats.class, new ApacheStatsDecorator());
        }
    }

    /** StatValue stats organized in Map for faster access. */
    private final Map<String, StatValue> statValueMap = new LinkedHashMap<>();

    /** Flag signaling that this stats object was never updated. Used by decorator.*/
    private boolean neverUpdated = true;

    /**
     * Creates a new ApacheStats object with given name.
     * @param aName name of the stats object.
     */
    ApacheStats(String aName) {
        super(aName);
        for (ApacheMetrics metric : ApacheMetrics.values()) {
            statValueMap.put(metric.valueName, metric.createStatValue());
        }
        addStatValues(statValueMap.values().toArray(new StatValue[statValueMap.size()]));
    }

    /**
     * Get 'neverUpdated' status of this ApacheStats.
     * @return <tt>false</tt> until first values update and <tt>true</tt> - after.
     */
    public boolean isNeverUpdated() {
        return neverUpdated;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getAvailableValueNames() {
        return Collections.unmodifiableList(Arrays.asList(ApacheMetrics.getCaptions()));
    }

    /**
     * Update stats with new values.
     * @param status ApacheStatus object with current values.
     */
    void update(ApacheStatus status) {
        neverUpdated = false;
        for (ApacheMetrics metric : ApacheMetrics.values()) {
            StatValue stat = getStatValue(metric);
            switch (metric.type) {
                case INT: stat.setValueAsInt(metric.getIntValue(status)); break;
                case DOUBLE: stat.setValueAsDouble(metric.getDoubleValue(status)); break;
                case STRING: stat.setValueAsString(metric.getStringValue(status)); break;
                case COUNTER:
                case DIFFLONG:
                case LONG: stat.setValueAsLong(metric.getLongValue(status));break;
                default:
            }
        }
    }

    private StatValue getStatValue(ApacheMetrics metric) {
        return statValueMap.get(metric.valueName);
    }

    /**
     * Get value of provided metric for the given interval as long value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public long getStatValueAsLong(ApacheMetrics metric, String interval) {
        return metric.getValueAsLong(getStatValue(metric), interval);
    }

    /**
     * Get value of provided metric for the given interval as int value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public int getStatValueAsInteger(ApacheMetrics metric, String interval) {
        return metric.getValueAsInt(getStatValue(metric), interval);
    }

    /**
     * Get value of provided metric for the given interval as double value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     * */
    public double getStatValueAsDouble(ApacheMetrics metric, String interval) {
        return metric.getValueAsDouble(getStatValue(metric), interval);
    }

    /**
     * Get value of provided metric for the given interval as String value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public String getStatValueAsString(ApacheMetrics metric, String interval) {
        return metric.getValueAsString(getStatValue(metric), interval);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
        if (valueName == null || valueName.isEmpty())
            throw new IllegalArgumentException("Value name can not be empty");
        ApacheMetrics metric = ApacheMetrics.getByValueName(valueName);
        StatValue statValue = statValueMap.get(valueName);
        if (statValue != null)
            return metric.getValueAsString(statValue, intervalName);
        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toStatsString(String intervalName, TimeUnit timeUnit) {
        StringBuilder b = new StringBuilder();
        b.append(getName()).append(" [");
        for (ApacheMetrics metric : ApacheMetrics.values()) {
            b.append(" ").append(metric.valueName).append(": ").append(getStatValueAsString(metric, intervalName)).append(";");
        }
        b.append(']');
        return b.toString();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void destroy() {
        super.destroy();
        statValueMap.clear();
    }

}

