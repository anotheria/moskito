package net.anotheria.moskito.extension.nginx;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.extension.nginx.decorator.NginxStatsDecorator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * NginxStats containing all the statValues we gather from nginx.
 *
 * @author dzhmud
 * @see NginxMetrics
 */
public class NginxStats extends AbstractStats {

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(NginxStats.class, new NginxStatsDecorator());
        }
    }

    /**
     * Cached List of values names.
     */
    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            NginxMetrics.getStrings(NginxMetrics.Strings.VALUENAME)
    ));

    /** StatValue stats organized in Map for faster access. */
    private final Map<String, StatValue> statValueMap = new LinkedHashMap<>();

    /** Flag signaling that this stats object was never updated. Used by decorator.*/
    private boolean neverUpdated = true;

    /**
     * Creates a new NginxStats object with given name.
     * @param aName name of the stats object.
     */
    NginxStats(String aName) {
        super(aName);
        for (NginxMetrics metric : NginxMetrics.values()) {
            statValueMap.put(metric.valueName, metric.createStatValue());
        }
        addStatValues(statValueMap.values().toArray(new StatValue[statValueMap.size()]));
    }

    /**
     * Get 'neverUpdated' status of this NginxStats.
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
        return VALUE_NAMES;
    }

    /**
     * Update stats with new values.
     * @param status NginxStatus object with current values.
     */
    void update(NginxStatus status) {
        neverUpdated = false;
        for (NginxMetrics metric : NginxMetrics.values()) {
            getStatValue(metric).setValueAsLong(metric.getValue(status));
        }
    }

    private StatValue getStatValue(NginxMetrics metric) {
        return statValueMap.get(metric.valueName);
    }

    /**
     * Get value of provided metric for the given interval as long value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public long getStatValueAsLong(NginxMetrics metric, String interval) {
        return metric.getValueAsLong(getStatValue(metric), interval);
    }

    /**
     * Get value of provided metric for the given interval as double value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     * */
    public double getStatValueAsDouble(NginxMetrics metric, String interval) {
        return metric.getValueAsDouble(getStatValue(metric), interval);
    }

    /**
     * Get value of provided metric for the given interval as String value.
     * @param metric metric which value we wanna get
     * @param interval the name of the Interval or <code>null</code> to get the absolute value
     * @return the current value
     */
    public String getStatValueAsString(NginxMetrics metric, String interval) {
        return metric.getValueAsString(getStatValue(metric), interval);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
        if (valueName == null || valueName.isEmpty())
            throw new IllegalArgumentException("Value name can not be empty");
        NginxMetrics metric = NginxMetrics.getByValueName(valueName);
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
        for (NginxMetrics metric : NginxMetrics.values()) {
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
