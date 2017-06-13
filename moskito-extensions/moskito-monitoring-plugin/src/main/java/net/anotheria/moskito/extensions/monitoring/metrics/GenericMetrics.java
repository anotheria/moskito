package net.anotheria.moskito.extensions.monitoring.metrics;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.SkipFirstDiffLongValueHolderFactory;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.stats.impl.TypeAwareStatValueImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Generic implementation of {@link IGenericMetrics} interface.
 *
 * @author dzhmud
 */
public abstract class GenericMetrics implements IGenericMetrics {

    /** Private registry of all created instances, mapped by their Class.*/
    private static final ConcurrentMap<Class<? extends GenericMetrics>, List<? extends GenericMetrics>> registry = new ConcurrentHashMap<>();

    /**
     * Get all registered metrics of given Class.
     * @param type metrics of this class method should return.
     * @param <T> class type.
     * @return list of metrics of the desired type, or null if there are none.
     */
    protected static <T extends GenericMetrics> List<T> getMetricsOfType(Class<T> type) {
        List<T> metricsList = (List<T>)registry.get(type);
        if (metricsList == null) {
            metricsList = new CopyOnWriteArrayList<>();
            List<T> previous = (List<T>)registry.putIfAbsent(type, metricsList);
            if (previous != null)
                metricsList = previous;
        }
        return metricsList;
    }

    /** Caption, short explanation and full explanation variables.*/
    private final String caption, shortExpl, explanation;

    /** ValueType of current metric. */
    private final StatValueTypes type;

    /** If true, value per second will be calculated. */
    private final boolean isRateMetric;

    /**
     * Shortest constructor of this class. ValueName param goes also to both explanations.
     * @param type type of the created StatValue
     * @param valueName valueName, used as caption in decorator, as metric name in MonitoringPluginConfig#metrics,
     *                  generally, as string ID of the metric.
     */
    protected GenericMetrics(StatValueTypes type, String valueName) {
        this(type, false, valueName, valueName);
    }

    /**
     * Constructor that allows adding custom explanation and short explanation.
     * @param type type of the created StatValue
     * @param valueName string ID of the metric.
     * @param strings first string goes to short explanation, second - to full explanation, next are ignored.
     */
    protected GenericMetrics(StatValueTypes type, String valueName, String ... strings ) {
        this(type, false, valueName, strings);
    }

    /**
     * Last constructor in chain. After assigning all instance variables register this instance in static registry.
     * @param type type of the created StatValue
     * @param isRateMetric boolean marker for 'rate' metrics, which means they will try to calculate value-per-second.
     * @param valueName string ID of the metric.
     * @param strings first string goes to short explanation, second - to full explanation, next are ignored.
     */
    protected GenericMetrics(StatValueTypes type, boolean isRateMetric, String valueName, String ... strings) {
        if (type == null || valueName == null)
            throw new IllegalArgumentException("StatValueType and valueName are both required!");
        this.type = type;
        this.isRateMetric = isRateMetric;
        this.caption = valueName;
        this.shortExpl = (strings == null || strings.length == 0) ? valueName : strings[0];
        this.explanation = (strings == null || strings.length < 2) ? shortExpl : strings[1];
        register(this);
    }

    private <T extends GenericMetrics> void register(T metric) {
        Class<GenericMetrics> clazz =  (Class<GenericMetrics>)metric.getClass();
        if (clazz.isAnonymousClass()) {
            clazz = (Class<GenericMetrics>)clazz.getSuperclass();
        }
        getMetricsOfType(clazz).add(metric);
    }

    @Override
    public StatValue createStatValue() {
        if (type != StatValueTypes.DIFFLONG) {
            return StatValueFactory.createStatValue(type, caption, Constants.getDefaultIntervals());
        } else {
            //for DIFFLONG type use upgraded diffLong value holders that ignore first interval.
            final IValueHolderFactory factory = SkipFirstDiffLongValueHolderFactory.INSTANCE;
            final TypeAwareStatValue statValue = new TypeAwareStatValueImpl(caption, type, factory);
            for (Interval interval : Constants.getDefaultIntervals()) {
                statValue.addInterval(interval);
            }
            return statValue;
        }
    }

    /**
     * Get logger for current class.
     * @return logger.
     */
    protected Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    @Override
    public StatValueTypes getType() {
        return type;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getExplanation() {
        return explanation;
    }

    @Override
    public String getShortExplanation() {
        return shortExpl;
    }

    @Override
    public boolean isRateMetric() {
        return isRateMetric;
    }

    @Override
    public boolean isDoubleValue() {
        return isRateMetric() || type == StatValueTypes.DOUBLE;
    }

    @Override
    public boolean isStringValue() {
        return !isRateMetric() && type == StatValueTypes.STRING;
    }

    @Override
    public boolean isIntegerValue() {
        return !isRateMetric() && type == StatValueTypes.INT;
    }

    @Override
    public boolean isLongValue() {
        return !isRateMetric() && (type == StatValueTypes.LONG || type == StatValueTypes.DIFFLONG || type == StatValueTypes.COUNTER);
    }

    @Override
    public Object parseValue(String value) {
        if (value == null) return null;
        Object result = null;
        try {
            switch (type) {
                case COUNTER:
                case DIFFLONG:
                case LONG: result = Long.parseLong(value); break;
                case INT: result = Integer.parseInt(value); break;
                case DOUBLE: result = Double.parseDouble(value); break;
                case STRING: result = value; break;
                default: result = value;
            }
        } catch (NumberFormatException nfe) {
            getLogger().warn("Failed to parse value '"+value+"'! Metric: " + this.toString(), nfe);
        }
        return result;
    }

    @Override
    public boolean isCorrectValue(Object value) {
        if (value == null)
            return true;
        final boolean result;
        switch (type) {
            case COUNTER:
            case DIFFLONG:
            case LONG: result = value instanceof Long; break;
            case INT: result = value instanceof Integer; break;
            case DOUBLE: result = value instanceof Double; break;
            case STRING: result = value instanceof String; break;
            default: result = false;
        }
        return result;
    }

    @Override
    public String toString() {
        return "GenericMetrics{" +
                "type=" + type +
                ", isRateMetric=" + isRateMetric +
                ", caption='" + caption + '\'' +
                ", shortExpl='" + shortExpl + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }

}
