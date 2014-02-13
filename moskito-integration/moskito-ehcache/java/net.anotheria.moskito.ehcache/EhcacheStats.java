package net.anotheria.moskito.ehcache;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.ehcache.decorators.EhcacheStatsDecorator;
import net.anotheria.moskito.webui.decorators.DecoratorRegistryFactory;

/**
 * Container for Ehcache related stats.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see net.anotheria.moskito.core.producers.AbstractStats
 * @see net.sf.ehcache.Statistics
 */
public class EhcacheStats extends AbstractStats {

    /**
     * @see net.sf.ehcache.Statistics#getStatisticsAccuracyDescription()
     */
    private StatValue statisticsAccuracy;

    /**
     * @see net.sf.ehcache.Statistics#getCacheHits()
     */
    private StatValue hits;

    /**
     * @see net.sf.ehcache.Statistics#getInMemoryHits()
     */
    private StatValue inMemoryHits;

    /**
     * @see net.sf.ehcache.Statistics#getOffHeapMisses()
     */
    private StatValue offHeapHits;

    /**
     * @see net.sf.ehcache.Statistics#getOnDiskHits()
     */
    private StatValue onDiskHits;

    /**
     * @see net.sf.ehcache.Statistics#getCacheMisses()
     */
    private StatValue misses;

    /**
     * @see net.sf.ehcache.Statistics#getInMemoryMisses()
     */
    private StatValue inMemoryMisses;

    /**
     * @see net.sf.ehcache.Statistics#getOffHeapMisses()
     */
    private StatValue offHeapMisses;

    /**
     * @see net.sf.ehcache.Statistics#getOnDiskMisses()
     */
    private StatValue onDiskMisses;

    /**
     * @see net.sf.ehcache.Statistics#getObjectCount()
     */
    private StatValue elements;

    /**
     * @see net.sf.ehcache.Statistics#getMemoryStoreObjectCount()
     */
    private StatValue inMemoryElements;

    /**
     * @see net.sf.ehcache.Statistics#getOffHeapStoreObjectCount()
     */
    private StatValue offHeapElements;

    /**
     * @see net.sf.ehcache.Statistics#getDiskStoreObjectCount()
     */
    private StatValue onDiskElements;

    /**
     * @see net.sf.ehcache.Statistics#getAverageGetTime()
     */
    private StatValue averageGetTime;

    /**
     * @see net.sf.ehcache.Statistics#getAverageSearchTime()
     */
    private StatValue averageSearchTime;

    /**
     * @see net.sf.ehcache.Statistics#getSearchesPerSecond()
     */
    private StatValue searchesPerSecond;

    /**
     * @see net.sf.ehcache.Statistics#getEvictionCount()
     */
    private StatValue evictionCount;

    /**
     * @see net.sf.ehcache.Statistics#getWriterQueueSize()
     */
    private StatValue writerQueueLength;


    /**
     * Creates a new stats object of this type with given name.
     *
     * @param name name of the stats object.
     */
    public EhcacheStats(String name) {
        super(name);
        this.statisticsAccuracy = StatValueFactory.createStatValue("", "statisticsAccuracy", Constants.getDefaultIntervals());
        this.hits = newLongStatValue("hits");
        this.inMemoryHits = newLongStatValue("inMemoryHits");
        this.offHeapHits = newLongStatValue("offHeapHits");
        this.onDiskHits = newLongStatValue("onDiskHits");
        this.misses = newLongStatValue("misses");
        this.inMemoryMisses = newLongStatValue("inMemoryMisses");
        this.offHeapMisses = newLongStatValue("offHeapMisses");
        this.onDiskMisses = newLongStatValue("onDiskMisses");
        this.elements = newLongStatValue("elements");
        this.inMemoryElements = newLongStatValue("inMemoryElements");
        this.offHeapElements = newLongStatValue("offHeapElements");
        this.onDiskElements = newLongStatValue("onDiskElements");
        this.averageGetTime = StatValueFactory.createStatValue(0.0d, "averageGetTime", Constants.getDefaultIntervals()); // in milliseconds
        this.averageSearchTime = newLongStatValue("averageSearchTime"); // in milliseconds
        this.searchesPerSecond = newLongStatValue("searchesPerSecond");
        this.evictionCount = newLongStatValue("evictionCount");
        this.writerQueueLength = newLongStatValue("writerQueueLength");
    }

    /**
     * Creates new {@link net.anotheria.moskito.core.stats.StatValue} that holds the long value with given name and default intervals.
     *
     * @param valueName name of the stat value.
     *
     * @return {@link net.anotheria.moskito.core.stats.StatValue}.
     */
    private StatValue newLongStatValue(String valueName) {
        return StatValueFactory.createStatValue(0L, valueName, Constants.getDefaultIntervals());
    }


    public StatValue getStatisticsAccuracy() {
        return statisticsAccuracy;
    }

    public StatValue getHits() {
        return hits;
    }

    public StatValue getInMemoryHits() {
        return inMemoryHits;
    }

    public StatValue getOffHeapHits() {
        return offHeapHits;
    }

    public StatValue getOnDiskHits() {
        return onDiskHits;
    }

    public StatValue getMisses() {
        return misses;
    }

    public StatValue getInMemoryMisses() {
        return inMemoryMisses;
    }

    public StatValue getOffHeapMisses() {
        return offHeapMisses;
    }

    public StatValue getOnDiskMisses() {
        return onDiskMisses;
    }

    public StatValue getElements() {
        return elements;
    }

    public StatValue getInMemoryElements() {
        return inMemoryElements;
    }

    public StatValue getOffHeapElements() {
        return offHeapElements;
    }

    public StatValue getOnDiskElements() {
        return onDiskElements;
    }

    public StatValue getAverageGetTime() {
        return averageGetTime;
    }

    public StatValue getAverageSearchTime() {
        return averageSearchTime;
    }

    public StatValue getSearchesPerSecond() {
        return searchesPerSecond;
    }

    public StatValue getEvictionCount() {
        return evictionCount;
    }

    public StatValue getWriterQueueLength() {
        return writerQueueLength;
    }


    @Override
    public String toStatsString(String aIntervalName, TimeUnit unit) {
        return "EhcacheStats{" +
                " statisticsAccuracy=" + statisticsAccuracy.getValueAsString(aIntervalName) +
                ",  hits=" + hits.getValueAsString(aIntervalName) +
                ",  inMemoryHits=" + inMemoryHits.getValueAsString(aIntervalName) +
                ",  offHeapHits=" + offHeapHits.getValueAsString(aIntervalName) +
                ",  onDiskHits=" + onDiskHits.getValueAsString(aIntervalName) +
                ",  misses=" + misses.getValueAsString(aIntervalName) +
                ",  inMemoryMisses=" + inMemoryMisses.getValueAsString(aIntervalName) +
                ",  offHeapMisses=" + offHeapMisses.getValueAsString(aIntervalName) +
                ",  onDiskMisses=" + onDiskMisses.getValueAsString(aIntervalName) +
                ",  elements=" + elements.getValueAsString(aIntervalName) +
                ",  inMemoryElements=" + inMemoryElements.getValueAsString(aIntervalName) +
                ",  offHeapElements=" + offHeapElements.getValueAsString(aIntervalName) +
                ",  onDiskElements=" + onDiskElements.getValueAsString(aIntervalName) +
                ",  averageGetTime=" + averageGetTime.getValueAsString(aIntervalName) +
                ",  averageSearchTime=" + averageSearchTime.getValueAsString(aIntervalName) +
                ",  searchesPerSecond=" + searchesPerSecond.getValueAsString(aIntervalName) +
                ",  evictionCount=" + evictionCount.getValueAsString(aIntervalName) +
                ",  writerQueueLength=" + writerQueueLength.getValueAsString(aIntervalName) +
                '}';
    }

    /* this block will register stats decorator when the class is loaded at the first time */
    static {
        DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(EhcacheStats.class, new EhcacheStatsDecorator());
    }

}
