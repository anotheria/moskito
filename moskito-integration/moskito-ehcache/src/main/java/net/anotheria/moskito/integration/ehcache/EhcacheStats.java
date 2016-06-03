package net.anotheria.moskito.integration.ehcache;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.integration.ehcache.decorators.EhcacheStatsDecorator;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
     * The part of accesses that result in cache hits.
     */
    private StatValue hitRatio;

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

    /* value names for values that this stats keeps */
    public static final String STATISTICS_ACCURACY = "statisticsAccuracy";
    public static final String HIT_RATIO = "hitRatio";
    public static final String HITS = "hits";
    public static final String IN_MEMORY_HITS = "inMemoryHits";
    public static final String OFF_HEAP_HITS = "offHeapHits";
    public static final String ON_DISK_HITS = "onDiskHits";
    public static final String MISSES = "misses";
    public static final String IN_MEMORY_MISSES = "inMemoryMisses";
    public static final String OFF_HEAP_MISSES = "offHeapMisses";
    public static final String ON_DISK_MISSES = "onDiskMisses";
    public static final String ELEMENTS = "elements";
    public static final String IN_MEMORY_ELEMENTS = "inMemoryElements";
    public static final String OFF_HEAP_ELEMENTS = "offHeapElements";
    public static final String ON_DISK_ELEMENTS = "onDiskElements";
    public static final String AVERAGE_GET_TIME = "averageGetTime";
    public static final String AVERAGE_SEARCH_TIME = "averageSearchTime";
    public static final String SEARCHES_PER_SECOND = "searchesPerSecond";
    public static final String EVICTION_COUNT = "evictionCount";
    public static final String WRITER_QUEUE_LENGTH = "writerQueueLength";

    /**
     * List of value names for values, collected by this stats.
     */
    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            STATISTICS_ACCURACY,
            HIT_RATIO,
            HITS,
            IN_MEMORY_HITS,
            OFF_HEAP_HITS,
            ON_DISK_HITS,
            MISSES,
            IN_MEMORY_MISSES,
            OFF_HEAP_MISSES,
            ON_DISK_MISSES,
            ELEMENTS,
            IN_MEMORY_ELEMENTS,
            OFF_HEAP_ELEMENTS,
            ON_DISK_ELEMENTS,
            AVERAGE_GET_TIME,
            AVERAGE_SEARCH_TIME,
            SEARCHES_PER_SECOND,
            EVICTION_COUNT,
            WRITER_QUEUE_LENGTH
    ));


    /**
     * Creates a new stats object of this type with given name.
     *
     * @param name name of the stats object.
     */
    public EhcacheStats(String name) {
        super(name);
        this.statisticsAccuracy = newStringStatValue(STATISTICS_ACCURACY);
        this.hitRatio = newDoubleStatValue(HIT_RATIO);
        this.hits = newLongStatValue(HITS);
        this.inMemoryHits = newLongStatValue(IN_MEMORY_HITS);
        this.offHeapHits = newLongStatValue(OFF_HEAP_HITS);
        this.onDiskHits = newLongStatValue(ON_DISK_HITS);
        this.misses = newLongStatValue(MISSES);
        this.inMemoryMisses = newLongStatValue(IN_MEMORY_MISSES);
        this.offHeapMisses = newLongStatValue(OFF_HEAP_MISSES);
        this.onDiskMisses = newLongStatValue(ON_DISK_MISSES);
        this.elements = newLongStatValue(ELEMENTS);
        this.inMemoryElements = newLongStatValue(IN_MEMORY_ELEMENTS);
        this.offHeapElements = newLongStatValue(OFF_HEAP_ELEMENTS);
        this.onDiskElements = newLongStatValue(ON_DISK_ELEMENTS);
        this.averageGetTime = newDoubleStatValue(AVERAGE_GET_TIME); // in milliseconds
        this.averageSearchTime = newLongStatValue(AVERAGE_SEARCH_TIME); // in milliseconds
        this.searchesPerSecond = newLongStatValue(SEARCHES_PER_SECOND);
        this.evictionCount = newLongStatValue(EVICTION_COUNT);
        this.writerQueueLength = newLongStatValue(WRITER_QUEUE_LENGTH);

    }


    /**
     * Creates new {@link net.anotheria.moskito.core.stats.StatValue} that holds the long value with given name
     * and default intervals.
     *
     * @param valueName name of the stat value.
     *
     * @return {@link net.anotheria.moskito.core.stats.StatValue}.
     */
    private StatValue newLongStatValue(String valueName) {
        StatValue sv = StatValueFactory.createStatValue(0L, valueName, Constants.getDefaultIntervals());
		addStatValues(sv);
		return sv;
    }

    /**
     * Creates new {@link net.anotheria.moskito.core.stats.StatValue} that holds the double value with given name
     * and default intervals.
     *
     * @param valueName name of the stat value.
     *
     * @return {@link net.anotheria.moskito.core.stats.StatValue}.
     */
    private StatValue newDoubleStatValue(String valueName) {
		StatValue sv = StatValueFactory.createStatValue(0.0d, valueName, Constants.getDefaultIntervals());
		addStatValues(sv);
		return sv;

    }

    /**
     * Creates new {@link net.anotheria.moskito.core.stats.StatValue} that holds the string value with given name
     * and default intervals.
     *
     * @param valueName name of the stat value.
     *
     * @return {@link net.anotheria.moskito.core.stats.StatValue}.
     */
    private StatValue newStringStatValue(String valueName) {
		StatValue sv = StatValueFactory.createStatValue("", valueName, Constants.getDefaultIntervals());
		addStatValues(sv);
        return sv;
    }

    public StatValue getStatisticsAccuracy() {
        return statisticsAccuracy;
    }

    public StatValue getHitRatio() {
        return hitRatio;
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
    public List<String> getAvailableValueNames () {
        return VALUE_NAMES;
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(valueName)) {
            throw new AssertionError("Value name can not be null or empty");
        }
        if (valueName.equals(AVERAGE_GET_TIME)) {
            return String.valueOf(timeUnit.transformMillis(getAverageGetTime().getValueAsDouble(intervalName)));
        }
        if (valueName.equals(AVERAGE_SEARCH_TIME)) {
            return String.valueOf(timeUnit.transformMillis(getAverageSearchTime().getValueAsLong(intervalName)));
        }
        if (valueName.equals(STATISTICS_ACCURACY)) {
            return getStatisticsAccuracy().getValueAsString(intervalName);
        }
        if (valueName.equals(HIT_RATIO)) {
            return getHitRatio().getValueAsString(intervalName);
        }
        if (valueName.equals(HITS)) {
            return getHits().getValueAsString(intervalName);
        }
        if (valueName.equals(IN_MEMORY_HITS)) {
            return getInMemoryHits().getValueAsString(intervalName);
        }
        if (valueName.equals(OFF_HEAP_HITS)) {
            return getOffHeapHits().getValueAsString(intervalName);
        }
        if (valueName.equals(ON_DISK_HITS)) {
            return getOnDiskHits().getValueAsString(intervalName);
        }
        if (valueName.equals(MISSES)) {
            return getMisses().getValueAsString(intervalName);
        }
        if (valueName.equals(IN_MEMORY_MISSES)) {
            return getInMemoryMisses().getValueAsString(intervalName);
        }
        if (valueName.equals(OFF_HEAP_MISSES)) {
            return getOffHeapMisses().getValueAsString(intervalName);
        }
        if (valueName.equals(ON_DISK_MISSES)) {
            return getOnDiskMisses().getValueAsString(intervalName);
        }
        if (valueName.equals(ELEMENTS)) {
            return getElements().getValueAsString(intervalName);
        }
        if (valueName.equals(IN_MEMORY_ELEMENTS)) {
            return getInMemoryElements().getValueAsString(intervalName);
        }
        if (valueName.equals(OFF_HEAP_ELEMENTS)) {
            return getOffHeapElements().getValueAsString(intervalName);
        }
        if (valueName.equals(ON_DISK_ELEMENTS)) {
            return getOnDiskElements().getValueAsString(intervalName);
        }
        if (valueName.equals(SEARCHES_PER_SECOND)) {
            return getSearchesPerSecond().getValueAsString(intervalName);
        }
        if (valueName.equals(EVICTION_COUNT)) {
            return getEvictionCount().getValueAsString(intervalName);
        }
        if (valueName.equals(WRITER_QUEUE_LENGTH)) {
            return getWriterQueueLength().getValueAsString(intervalName);
        }

        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    @Override
    public String toStatsString(String aIntervalName, TimeUnit unit) {
        return "EhcacheStats{" +
                " statisticsAccuracy=" + statisticsAccuracy.getValueAsString(aIntervalName) +
                ",  hitRatio=" + hitRatio.getValueAsString(aIntervalName) +
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
                ",  averageGetTime=" + unit.transformMillis(averageGetTime.getValueAsDouble(aIntervalName)) +
                ",  averageSearchTime=" + unit.transformMillis(averageSearchTime.getValueAsLong(aIntervalName)) +
                ",  searchesPerSecond=" + searchesPerSecond.getValueAsString(aIntervalName) +
                ",  evictionCount=" + evictionCount.getValueAsString(aIntervalName) +
                ",  writerQueueLength=" + writerQueueLength.getValueAsString(aIntervalName) +
                '}';
    }

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if(MoskitoWebUi.isPresent()) {
            new StatsDecoratorRegistrator().register();
        }
    }

    /* will be initialized only if MoSKito WebUI is embedded into application */
    private static final class StatsDecoratorRegistrator {
        public void register() {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(EhcacheStats.class, new EhcacheStatsDecorator());
        }
    }

}
