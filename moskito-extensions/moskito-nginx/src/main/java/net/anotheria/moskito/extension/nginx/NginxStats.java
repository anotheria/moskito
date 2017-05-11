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
     * StatValue stats organized in Map for faster access.
     */
    private final Map<String, StatValue> statValueMap = new LinkedHashMap<>();

    /** Flag signaling that this stats object was never updated. Used by decorator.*/
    private boolean neverUpdated = true;

    /**
     * {@inheritDoc}.
     */
    NginxStats(String aName) {
        super(aName);
        for (NginxMetrics metric : NginxMetrics.values()) {
            statValueMap.put(metric.valueName, metric.createStatValue());
        }
        addStatValues(statValueMap.values().toArray(new StatValue[statValueMap.size()]));
    }

    private StatValue getStatValue(NginxMetrics metric) {
        return statValueMap.get(metric.valueName);
    }

    public long getStatValueAsLong(NginxMetrics metric, String interval) {
        return metric.getValueAsLong(getStatValue(metric), interval);
    }

    public String getStatValueAsString(NginxMetrics metric, String interval) {
        return metric.getValueAsString(getStatValue(metric), interval);
    }

    public double getStatValueAsDouble(NginxMetrics metric, String interval) {
        return metric.getValueAsDouble(getStatValue(metric), interval);
    }

    public void update(NginxStatus status) {
        neverUpdated = false;
        for (NginxMetrics metric : NginxMetrics.values()) {
            getStatValue(metric).setValueAsLong(metric.getValue(status));
        }
    }

    public boolean isNeverUpdated() {
        return neverUpdated;
    }

    @Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
        StringBuilder b = new StringBuilder();
        b.append(getName()).append(' ');
        for (NginxMetrics metric : NginxMetrics.values()) {
            b.append(" ").append(metric.valueName).append(": ").append(getStatValueAsString(metric, intervalName));
        }
        return b.toString();
    }

    public void destroy() {
        super.destroy();
        statValueMap.clear();
    }

    /**
     * Cached List of values names.
     */
    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            NginxMetrics.getStrings(NginxMetrics.Strings.VALUENAME)
    ));

    @Override
    public List<String> getAvailableValueNames() {
        return VALUE_NAMES;
    }

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

}
