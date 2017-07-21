package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats object for Garbage Collector related values.
 *
 * @author esmakula
 */
public class GCStats extends AbstractStats {
    /**
     * Collection count amount.
     */
    private final StatValue collectionCount;
    /**
     * Collection time.
     */
    private final StatValue collectionTime;

    /**
     * Constructor.
     *
     * @param aName stat name
     */
    public GCStats(String aName) {
        this(aName, Constants.getDefaultIntervals());
    }

    private GCStats(String aName, Interval[] selectedIntervals) {
        super(aName);
        collectionCount = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "collectionCount", selectedIntervals);
        collectionTime = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "collectionTime", selectedIntervals);

        addStatValues(collectionCount, collectionTime);

    }

    @Override
    public String toStatsString(String intervalName, TimeUnit unit) {
        StringBuilder ret = new StringBuilder();

        ret.append(getName()).append(' ');
        ret.append(" CollectionCount: ").append(collectionCount.getValueAsLong(intervalName));
        ret.append(" CollectionTime: ").append(collectionTime.getValueAsLong(intervalName));

        return ret.toString();
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {

        if (valueName == null)
            throw new AssertionError("Value name can't be null");
        valueName = valueName.toLowerCase();

        if (valueName.equals("collectioncount") || valueName.equals("collection count"))
            return String.valueOf(getCollectionCount(intervalName));
        if (valueName.equals("collectiontime") || valueName.equals("collection time"))
            return String.valueOf(getCollectionTime(intervalName));

        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    /**
     * Value names.
     */
    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "CollectionCount",
            "CollectionTime"
    ));

    @Override
    public List<String> getAvailableValueNames() {
        return VALUE_NAMES;
    }

    /**
     * Updates stats values.
     *
     * @param aCollectionCount collection count
     * @param aCollectionTime  collection time
     */
    public void update(long aCollectionCount, long aCollectionTime) {
        collectionCount.setValueAsLong(aCollectionCount);
        collectionTime.setValueAsLong(aCollectionTime);
    }

    /**
     * Returns collection count for interval.
     *
     * @param intervalName interval name
     * @return collection count long value
     */
    public long getCollectionCount(String intervalName) {
        return collectionCount.getValueAsLong(intervalName);
    }

    /**
     * Returns collection time for interval.
     *
     * @param intervalName interval name
     * @return collection time long value
     */
    public long getCollectionTime(String intervalName) {
        return collectionTime.getValueAsLong(intervalName);
    }


}
