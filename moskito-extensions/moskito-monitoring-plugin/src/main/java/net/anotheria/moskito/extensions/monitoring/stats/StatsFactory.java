package net.anotheria.moskito.extensions.monitoring.stats;

import java.util.List;

/**
 * Factory that creates instances of desired stats class.
 *
 * @param <GS> type of stats object to create.
 * @author dzhmud
 */
public interface StatsFactory<GS extends GenericStats> {

    /**
     * Create stats with given name and all available metrics.
     * @param name name for the created stats.
     * @return created instance.
     */
    GS createStats(String name);

    /**
     * Create stats with given name and metrics.
     * Order of metric names is indifferent. If metricNames list is empty or null, empty metric list will be used.
     * Only those metric names that match existing metrics will be used.
     * @param name name for the created stats.
     * @param metricsNames list of metric names.
     * @return created instance.
     */
    GS createStats(String name, List<String> metricsNames);
}
