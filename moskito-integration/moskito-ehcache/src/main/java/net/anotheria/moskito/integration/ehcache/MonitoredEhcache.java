package net.anotheria.moskito.integration.ehcache;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Statistics;
import net.sf.ehcache.constructs.EhcacheDecoratorAdapter;

import java.util.TimerTask;

/**
 * Proxy for {@link net.sf.ehcache.Ehcache} instances that allows MoSKito to access Ehcache stats.
 *
 * Statistics about monitored instance can be found in MoSKito's producer registry by the same producer ID
 * that the name of the underlying cache is.
 *
 * Mention that Ehcache statistics will become enabled for underlying cache when the instance of this class is created.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see PeriodicStatsUpdater
 * @see net.anotheria.moskito.core.util.BuiltInMemoryProducer
 * @see net.sf.ehcache.terracotta.InternalEhcache
 */
public class MonitoredEhcache extends EhcacheDecoratorAdapter {

    /**
     * Producer that keeps the stats produced by underlying cache.
     */
    private OnDemandStatsProducer<EhcacheStats> cacheProducer;

    /**
     * Creates monitored proxy instance for underlying cache with default statistics accuracy and other parameters.
     *
     * @param instance underlying cache.
     */
    public MonitoredEhcache(Ehcache instance) {
        this(instance, Statistics.STATISTICS_ACCURACY_NONE);
    }

    /**
     * Creates monitored proxy instance for underlying cache with given update period and default statistics accuracy.
     *
     * @param instance     underlying cache.
     * @param updatePeriod Ehcache statistics accuracy.
     */
    public MonitoredEhcache(Ehcache instance, long updatePeriod) {
        this(instance, Statistics.STATISTICS_ACCURACY_NONE, updatePeriod);
    }

    /**
     * Creates monitored proxy instance for underlying cache with given statistics accuracy.
     *
     * @param instance           underlying cache.
     * @param statisticsAccuracy Ehcache statistics accuracy.
     */
    public MonitoredEhcache(Ehcache instance, int statisticsAccuracy) {
        this(instance, statisticsAccuracy, "Ehcache", "cache", 60*1000L);
    }

    /**
     * Creates monitored proxy instance for underlying cache with given parameters.
     *
     * @param instance           underlying cache.
     * @param statisticsAccuracy Ehcache statistics accuracy.
     * @param updatePeriod       period (in milliseconds) in which stats values will be updated.
     */
    public MonitoredEhcache(Ehcache instance, int statisticsAccuracy, long updatePeriod) {
        this(instance, statisticsAccuracy, "Ehcache", "cache", updatePeriod);
    }

    /**
     * Creates monitored proxy instance for underlying cache with given parameters.
     *
     * @param instance           underlying cache.
     * @param statisticsAccuracy Ehcache statistics accuracy.
     * @param category           category for related stats producer.
     * @param subsystem          subsystem for related stats producer.
     * @param updatePeriod       period (in milliseconds) in which stats values will be updated.
     */
    public MonitoredEhcache(Ehcache instance, int statisticsAccuracy, String category, String subsystem, long updatePeriod) {
        super(instance);
        underlyingCache.setStatisticsAccuracy(statisticsAccuracy);
        underlyingCache.setStatisticsEnabled(true);
        cacheProducer = new OnDemandStatsProducer<EhcacheStats>(underlyingCache.getName(), category, subsystem, new EhcacheStatsFactory());
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(cacheProducer);

        PeriodicStatsUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                if (underlyingCache.isStatisticsEnabled()) {
                    updateStats();
                }
            }
        }, updatePeriod);

    }

    /**
     * Retrieves internal producer's stats.
     *
     * @return {@link EhcacheStats} instance related to this cache.
     */
    private EhcacheStats getProducerStats() {
        try {
            return cacheProducer.getStats("cumulated");
        } catch (OnDemandStatsProducerException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Updates internal producer's stats using Ehcache statistics.
     */
    private void updateStats() {
        EhcacheStats stats = getProducerStats();
        Statistics statistics = underlyingCache.getStatistics();
        double accesses = statistics.getCacheHits() + statistics.getCacheMisses();
        double hitRatio = accesses != 0 ? statistics.getCacheHits() / accesses : 0;

        stats.getStatisticsAccuracy().setValueAsString(statistics.getStatisticsAccuracyDescription());
        stats.getHitRatio().setValueAsDouble(hitRatio);
        stats.getHits().setValueAsLong(statistics.getCacheHits());
        stats.getMisses().setValueAsLong(statistics.getCacheMisses());
        stats.getElements().setValueAsLong(statistics.getObjectCount());

        stats.getInMemoryHits().setValueAsLong(statistics.getInMemoryHits());
        stats.getInMemoryMisses().setValueAsLong(statistics.getInMemoryMisses());
        stats.getInMemoryElements().setValueAsLong(statistics.getMemoryStoreObjectCount());

        stats.getOnDiskHits().setValueAsLong(statistics.getOnDiskHits());
        stats.getOnDiskMisses().setValueAsLong(statistics.getOnDiskMisses());
        stats.getOnDiskElements().setValueAsLong(statistics.getDiskStoreObjectCount());

        stats.getOffHeapHits().setValueAsLong(statistics.getOffHeapHits());
        stats.getOffHeapMisses().setValueAsLong(statistics.getOffHeapMisses());
        stats.getOffHeapElements().setValueAsLong(statistics.getOffHeapStoreObjectCount());

        stats.getAverageGetTime().setValueAsDouble(statistics.getAverageGetTime());
        stats.getAverageSearchTime().setValueAsLong(statistics.getAverageSearchTime());

        stats.getSearchesPerSecond().setValueAsLong(statistics.getSearchesPerSecond());
        stats.getEvictionCount().setValueAsLong(statistics.getEvictionCount());
        stats.getWriterQueueLength().setValueAsLong(statistics.getWriterQueueSize());
    }

}
