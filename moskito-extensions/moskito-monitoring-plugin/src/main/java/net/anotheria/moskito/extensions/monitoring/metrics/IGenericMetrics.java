package net.anotheria.moskito.extensions.monitoring.metrics;

import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;

/**
 * Interface that represents collected metrics.
 *
 * @author dzhmud
 */
public interface IGenericMetrics {

    /**
     * Get caption(value name) of this metric.
     * @return caption.
     */
    String getCaption();

    /**
     * Get short explanation of this metric if present, or caption.
     * @return short explanation.
     */
    String getShortExplanation();

    /**
     * Get full explanation of this metric if present, or short explanation, or caption.
     * @return full explanation.
     */
    String getExplanation();

    /**
     * Get StatValue type of current metric.
     * @return StatValue type
     * @see StatValueTypes
     */
    StatValueTypes getType();

    /**
     * Create {@link StatValue} from this metric.
     * @return created {@link StatValue}.
     * @see StatValue
     */
    StatValue createStatValue();

    /**
     * Check if this metric is 'rate' metric.
     * 'Rate' metrics are about calculating value-per-second value.
     * If metric is 'rate' metric, value per second will be calculated by #getValueAsX methods.
     * @return {@code true} if this metric is 'rate' metric.
     */
    boolean isRateMetric();

    /**
     * Check if this metric produces double values.
     * @return {@code true} if this metric produces value of type double, {@code false} otherwise.
     */
    boolean isDoubleValue();

    /**
     * Check if this metric produces String values.
     * @return {@code true} if this metric produces value of type String, {@code false} otherwise.
     */
    boolean isStringValue();

    /**
     * Check if this metric produces integer values.
     * @return {@code true} if this metric produces value of type integer, {@code false} otherwise.
     */
    boolean isIntegerValue();

    /**
     * Check if this metric produces long values.
     * @return {@code true} if this metric produces value of type long, {@code false} otherwise.
     */
    boolean isLongValue();

    /**
     * Parse value according to current metric type.
     * @param value String value to parse.
     * @return parsed value of proper type(Long/Integer/Double/String) or {@code null}
     *      if parsing into desired type failed.
     */
    Object parseValue(String value);

    /**
     * Checks if provided value is of correct type for this metric.
     * @param value value to check.
     * @return {@code true} if value is of correct type, {@code false} otherwise.
     */
    boolean isCorrectValue(Object value);

}
