package net.anotheria.moskito.ehcache.decorators;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.ehcache.EhcacheStats;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;
import net.anotheria.moskito.webui.shared.bean.DoubleValueBean;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;
import net.anotheria.moskito.webui.shared.bean.StringValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link net.anotheria.moskito.ehcache.EhcacheStats} decorator for MoSKito WebUI.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see net.anotheria.moskito.webui.decorators.AbstractDecorator
 */
public class EhcacheStatsDecorator extends AbstractDecorator {

    /**
     * Captions.
     */
    static final String CAPTIONS[] = {
            "Accuracy",
            "Hits",
            "InMemoryHits",
            "OffHeapHits",
            "OnDiskHits",
            "Misses",
            "InMemoryMisses",
            "OffHeapMisses",
            "OnDiskMisses",
            "Elements",
            "InMemoryElements",
            "OffHeapElements",
            "OnDiskElements",
            "AvgGetTime",
            "AvgSearchTime",
            "SearchesPerSecond",
            "EvictionCount",
            "WriterQueueLength"
    };

    /**
     * Short explanations.
     */
    static final String SHORT_EXPLANATIONS[] = {
            "Statistics accuracy",
            "Cache hits",
            "In-memory cache hits",
            "Off-heap cache hits",
            "On-disk cache hits",
            "On-disk cache hits",
            "Cache misses",
            "In-memory misses",
            "Off-heap misses",
            "On-disk misses",
            "Elements in cache",
            "Elements in in-memory cache",
            "Elements in off-heap cache",
            "Elements in on-disk cache",
            "Average get time",
            "Average search time",
            "Searches per second",
            "Eviction count",
            "Writer queue length",
    };

    /**
     * Explanations.
     */
    static final String EXPLANATIONS[] = {
            "A human readable description of the accuracy setting. One of \"None\", \"Best Effort\" or \"Guaranteed\". Accurately measuring statistics can be expensive",
            "The number of times a requested item was found in the cache",
            "The number of times a requested item was found in the memory store",
            "The number of times a requested item was found in the off-heap store",
            "The number of times a requested item was found in the disk store",
            "The number of times a requested element was not found in the cache",
            "The number of times a requested item was not found in memory store",
            "The number of times a requested item was not found in off-heap store",
            "The number of times a requested item was not found on disk, or 0 if there is no disk storage configured",
            "The number of elements in the ehcache, with a varying degree of accuracy, depending on accuracy setting",
            "The number of objects in the memory store",
            "The number of objects in the off-heap store",
            "The number of objects in the disk store",
            "The average time taken to get an element from the cache",
            "The average search execution time within the last sample period",
            "The number of search executions that have completed in the last second",
            "The number of cache evictions, since the cache was created, or statistics were cleared",
            "The number of elements waiting to be processed by the write behind writer. -1 if no write-behind. The value is for all local buckets",
    };


    /**
     * Creates a new decorator for {@link net.anotheria.moskito.ehcache.EhcacheStats}.
     */
    public EhcacheStatsDecorator() {
        super("Ehcache", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }


    @Override
    public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
        EhcacheStats stats = (EhcacheStats) statsObject;
        List<StatValueBean> bean = new ArrayList<StatValueBean>(CAPTIONS.length);
        int i = 0;

        bean.add(new StringValueBean(CAPTIONS[i++], stats.getStatisticsAccuracy().getValueAsString(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getHits().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getInMemoryHits().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOffHeapHits().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOnDiskHits().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getMisses().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getInMemoryMisses().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOffHeapMisses().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOnDiskMisses().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getElements().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getInMemoryElements().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOffHeapElements().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getOnDiskElements().getValueAsLong(interval)));
        bean.add(new DoubleValueBean(CAPTIONS[i++], unit.transformMillis(stats.getAverageGetTime().getValueAsDouble(interval))));
        bean.add(new LongValueBean(CAPTIONS[i++], unit.transformMillis(stats.getAverageGetTime().getValueAsLong(interval))));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getSearchesPerSecond().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getEvictionCount().getValueAsLong(interval)));
        bean.add(new LongValueBean(CAPTIONS[i++], stats.getWriterQueueLength().getValueAsLong(interval)));

        return bean;
    }

}
