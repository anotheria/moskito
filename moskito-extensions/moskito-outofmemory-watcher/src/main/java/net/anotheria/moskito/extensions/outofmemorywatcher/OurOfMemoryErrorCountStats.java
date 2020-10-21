package net.anotheria.moskito.extensions.outofmemorywatcher;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats for out of memory errors.
 *
 * @author asamoilich
 */
public class OurOfMemoryErrorCountStats extends GenericStats {

    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "CURRENT"
    ));

    /**
     * Stats value of outofmemory errors.
     */
    private StatValue current;

    /**
     * Creates new OurOfMemoryErrorCountStats.
     */
    public OurOfMemoryErrorCountStats(String name) {
        this(name, Constants.getDefaultIntervals());
    }

    /**
     * Creates new OurOfMemoryErrorCountStats.
     */
    public OurOfMemoryErrorCountStats(String name, Interval[] intervals) {
        super(name);
        current = StatValueFactory.createStatValue(-1L, "current", intervals);
        addStatValues(current);
    }

    /**
     * Called regularly by a timer, updates the internal stats.
     *
     * @param errorCount
     */
    public void update(long errorCount) {
        current.setValueAsLong(errorCount);
    }


    @Override
    public String toStatsString(String intervalName, TimeUnit unit) {
        StringBuilder ret = new StringBuilder("Out of memory errors ");
        ret.append(" current: ").append(current.getValueAsInt(intervalName));
        return ret.toString();
    }

    public long getCurrentErrorCount(String interval) {
        return current.getValueAsLong(interval);
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
        if (valueName == null)
            return null;
        valueName = valueName.toLowerCase();

        if (valueName.equals("current"))
            return String.valueOf(getCurrentErrorCount(intervalName));
        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    @Override
    public List<String> getAvailableValueNames() {
        return VALUE_NAMES;
    }
}
