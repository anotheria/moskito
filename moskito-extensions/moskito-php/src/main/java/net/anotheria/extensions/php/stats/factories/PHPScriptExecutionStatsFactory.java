package net.anotheria.extensions.php.stats.factories;

import net.anotheria.extensions.php.stats.PHPScriptExecutionStats;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory for php script execution stats
 */
public class PHPScriptExecutionStatsFactory extends AbstractStatsFactory<PHPScriptExecutionStats> {

    /**
     * Default instance to spare additional object creation.
     */
    public static final AbstractStatsFactory<PHPScriptExecutionStats> DEFAULT_INSTANCE
            = new PHPScriptExecutionStatsFactory();

    /**
     * Creates a new factory with default intervals.
     */
    public PHPScriptExecutionStatsFactory() {
        super();
    }

    @Override
    public PHPScriptExecutionStats createStatsObject(String name) {
        return new PHPScriptExecutionStats(name, getIntervals());
    }

}
