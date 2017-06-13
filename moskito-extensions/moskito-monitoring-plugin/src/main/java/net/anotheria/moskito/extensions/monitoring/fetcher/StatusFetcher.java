package net.anotheria.moskito.extensions.monitoring.fetcher;

import net.anotheria.moskito.extensions.monitoring.config.MonitoredInstance;

/**
 * Interface that represents entity that fetches needed data from {@link MonitoredInstance}.
 *
 * @param <T> type of result.
 * @author dzhmud
 */
public interface StatusFetcher<T> {

    /**
     * Fetch current status for the instance.
     * @param instance {@link MonitoredInstance} to fetch status from.
     * @return retrieved status.
     */
    T fetchStatus(MonitoredInstance instance);

}
