package net.anotheria.moskito.extensions.monitoring.stats;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.extensions.monitoring.decorator.ApacheStatsDecorator;
import net.anotheria.moskito.extensions.monitoring.metrics.ApacheMetrics;
import net.anotheria.moskito.extensions.monitoring.parser.StatusData;

import java.util.List;

/**
 * ApacheStats class. Handles stats of Apache httpd server represented by {@link ApacheMetrics}.
 *
 * @author dzhmud
 */
public final class ApacheStats extends GenericStats<ApacheMetrics> {

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(ApacheStats.class, new ApacheStatsDecorator());
        }
    }

    /** Private constructor. Use {@link ApacheStatsFactory} for creating ApacheStats. */
    private ApacheStats(String aName, List<ApacheMetrics> metrics) {
        super(aName, metrics);
    }

    /**
     * Check SERVER_UPTIME metric and if its value is lower than the stored one -
     * monitored apache server was restarted. In this case all DIFFLONG statValues
     * need resetting.
     *
     * @param status latest status data.
     */
    protected void handleStatsResetIfNeeded(StatusData<ApacheMetrics> status) {
        StatValue uptime = getStatValue(ApacheMetrics.SERVER_UPTIME);
        Long currUptime = Long.class.cast(status.get(ApacheMetrics.SERVER_UPTIME));
        if (uptime != null && currUptime != null && uptime.getValueAsLong() > currUptime) {
            for (ApacheMetrics metric : getAvailableMetrics()) {
                if (metric.getType() == StatValueTypes.DIFFLONG) {
                    StatValue stat = getStatValue(metric);
                    if (stat != null)
                        stat.reset();
                }
            }
        }
    }

    /** {@link StatsFactory} that creates {@link ApacheStats} instances. */
    public static class ApacheStatsFactory implements StatsFactory<ApacheStats> {

        @Override
        public ApacheStats createStats(String name) {
            return new ApacheStats(name, ApacheMetrics.getAll());
        }

        @Override
        public ApacheStats createStats(String name, List<String> metricsNames) {
            return new ApacheStats(name, ApacheMetrics.findMetrics(metricsNames));
        }

    }

}
