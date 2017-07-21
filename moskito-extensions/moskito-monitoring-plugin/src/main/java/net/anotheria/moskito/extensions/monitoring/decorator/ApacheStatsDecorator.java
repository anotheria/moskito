package net.anotheria.moskito.extensions.monitoring.decorator;

import net.anotheria.moskito.extensions.monitoring.metrics.ApacheMetrics;
import net.anotheria.moskito.extensions.monitoring.stats.ApacheStats;

/**
 * Decorator for ApacheStats.
 *
 * @author dzhmud
 */
public class ApacheStatsDecorator extends GenericStatsDecorator<ApacheStats,ApacheMetrics> {

    /** Default constructor. */
    public ApacheStatsDecorator() {
        super("Apache", ApacheMetrics.getAll());
    }

}
