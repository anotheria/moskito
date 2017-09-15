package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.GCStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * A decorator for the Garbage Collector stats.
 *
 * @author esmakula
 */
public class GCStatsDecorator extends AbstractDecorator {

    /**
     * Captions.
     */
    private static final String[] CAPTIONS = {
            "CollectionCount",
            "CollectionTime"
    };
    /**
     * Short explanations (mouse-over).
     */
    private static final String[] SHORT_EXPLANATIONS = {
            "Collection count",
            "Collection time",
    };

    /**
     * Detailed explanations.
     */
    private static final String[] EXPLANATIONS = {
            "Total number of collections that have occurred",
            "Approximate accumulated collection elapsed time in milliseconds"
    };

    /**
     * Default constructor.
     */
    public GCStatsDecorator() {
        super("GC", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }

    @Override
    public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
        GCStats stats = (GCStats) statsObject;

        List<StatValueAO> ret = new ArrayList<>(CAPTIONS.length);
        int i = 0;
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getCollectionCount(interval)));
        ret.add(new LongValueAO(CAPTIONS[i++], stats.getCollectionTime(interval)));

        return ret;
    }

}
