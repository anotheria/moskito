package net.anotheria.moskito.extensions.monitoring.decorator;

import net.anotheria.moskito.extensions.monitoring.metrics.NginxMetrics;
import net.anotheria.moskito.extensions.monitoring.stats.NginxStats;

/**
 * Decorator for NginxStats.
 *
 * @author dzhmud
 */
public class NginxStatsDecorator extends GenericStatsDecorator<NginxStats, NginxMetrics> {

    /** Default constructor. */
    public NginxStatsDecorator() {
        super("Nginx", NginxMetrics.getAll());
    }

}
