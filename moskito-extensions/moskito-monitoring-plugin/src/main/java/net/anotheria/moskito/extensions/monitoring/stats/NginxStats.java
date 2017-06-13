package net.anotheria.moskito.extensions.monitoring.stats;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.extensions.monitoring.decorator.NginxStatsDecorator;
import net.anotheria.moskito.extensions.monitoring.metrics.NginxMetrics;
import net.anotheria.moskito.extensions.monitoring.parser.StatusData;

import java.util.List;

/**
 * NginxStats class. Handles stats of Nginx server represented by {@link NginxMetrics}.
 *
 * @author dzhmud
 */
public final class NginxStats extends GenericStats<NginxMetrics> {

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(NginxStats.class, new NginxStatsDecorator());
        }
    }

    /** Private constructor. Use {@link NginxStatsFactory} for creating NginxStats. */
    private NginxStats(String aName, List<NginxMetrics> metrics) {
        super(aName, metrics);
    }

    /**
     * Check HANDLED metric and if its value is lower than the stored one -
     * monitored Nginx server was restarted. In this case all DIFFLONG statValues
     * need resetting.
     *
     * @param status latest status data.
     */
    protected void handleStatsResetIfNeeded(StatusData<NginxMetrics> status) {
        StatValue previousHandled = getStatValue(NginxMetrics.HANDLED);
        Long currentHandled = Long.class.cast(status.get(NginxMetrics.HANDLED));
        if (previousHandled != null && currentHandled != null && previousHandled.getValueAsLong() > currentHandled) {
            for (NginxMetrics metric : getAvailableMetrics()) {
                if (metric.getType() == StatValueTypes.DIFFLONG) {
                    StatValue stat = getStatValue(metric);
                    if (stat != null)
                        stat.reset();
                }
            }
        }
    }

    /** {@link StatsFactory} that creates {@link NginxStats} instances. */
    public static class NginxStatsFactory implements StatsFactory<NginxStats> {

        @Override
        public NginxStats createStats(String name) {
            return new NginxStats(name, NginxMetrics.getAll());
        }

        @Override
        public NginxStats createStats(String name, List<String> metricsNames) {
            return new NginxStats(name, NginxMetrics.findMetrics(metricsNames));
        }

    }

}
