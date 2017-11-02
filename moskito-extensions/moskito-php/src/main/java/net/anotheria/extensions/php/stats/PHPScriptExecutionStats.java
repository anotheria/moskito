package net.anotheria.extensions.php.stats;

import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

/**
 * Stats for php execution producer. Based on request oriented stats.
 * In addition to {@link RequestOrientedStats} values
 * has memory consumption values
 */
public class PHPScriptExecutionStats extends RequestOrientedStats {

    /**
     * Value for maximum memory used by php script
     */
    private StatValue maxMemoryUsage;
    /**
     * Value for minimum memory used by php script
     */
    private StatValue minMemoryUsage;
    /**
     * Value of last memory amount used by php script
     */
    private StatValue lastMemoryUsage;
    /**
     * Value of total memory used by php script
     */
    private StatValue totalMemoryUsage;

    /**
     * @param url url that invoked script execution
     * @param aSelectedIntervals stats intervals
     */
    public PHPScriptExecutionStats(String url, Interval[] aSelectedIntervals) {
        super(url, aSelectedIntervals);

        final Long longPattern = 0L;

        maxMemoryUsage = StatValueFactory.createStatValue(longPattern, "maxMemoryUsage", aSelectedIntervals);
        minMemoryUsage = StatValueFactory.createStatValue(longPattern, "minMemoryUsage", aSelectedIntervals);
        lastMemoryUsage = StatValueFactory.createStatValue(longPattern, "lastMemoryUsage", aSelectedIntervals);
        totalMemoryUsage = StatValueFactory.createStatValue(longPattern, "totalMemoryUsage", aSelectedIntervals);

        minMemoryUsage.setDefaultValueAsLong(Long.MAX_VALUE);
        minMemoryUsage.reset();
        maxMemoryUsage.setDefaultValueAsLong(Long.MIN_VALUE);
        maxMemoryUsage.reset();

        addStatValues(maxMemoryUsage, minMemoryUsage, lastMemoryUsage, totalMemoryUsage);

    }

    /**
     * Adds memory used by script call
     * @param memoryUsed amount of memory used in bytes
     */
    public void addMemoryUsage(long memoryUsed) {
        totalMemoryUsage.increaseByLong(memoryUsed);
        minMemoryUsage.setValueIfLesserThanCurrentAsLong(memoryUsed);
        maxMemoryUsage.setValueIfGreaterThanCurrentAsLong(memoryUsed);
    }

    /**
     * @param intervalName name of interval
     * @return average amount of memory used for selected interval
     */
    public double getAverageMemoryUsage(String intervalName) {
        return totalMemoryUsage.getValueAsDouble(intervalName) / getTotalRequests(intervalName);
    }

    public long getMaxMemoryUsage() {
        return maxMemoryUsage.getValueAsLong();
    }

    public long getMinMemoryUsage() {
        return minMemoryUsage.getValueAsLong();
    }

    public long getLastMemoryUsage() {
        return lastMemoryUsage.getValueAsLong();
    }

}
