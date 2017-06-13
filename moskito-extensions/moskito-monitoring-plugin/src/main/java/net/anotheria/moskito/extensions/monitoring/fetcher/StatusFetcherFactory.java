package net.anotheria.moskito.extensions.monitoring.fetcher;

/**
 * Factory that creates needed {@link StatusFetcher}.
 * @param <T> type of result that StatusFetcher returns.
 * @author dzhmud
 */
public interface StatusFetcherFactory<T> {

    /**
     * Returns StatusFetcher instance, new or cached.
     * @return StatusFetcher instance.
     */
    StatusFetcher<T> getStatusFetcher();

}
