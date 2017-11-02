package net.anotheria.extensions.php.mappers.impl;

import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.extensions.php.exceptions.ValueNotFoundException;
import net.anotheria.extensions.php.mappers.AbstractOnDemandStatsProducerMapper;
import net.anotheria.extensions.php.mappers.StatsValues;
import net.anotheria.extensions.php.stats.PHPScriptExecutionStats;
import net.anotheria.extensions.php.stats.factories.PHPScriptExecutionStatsFactory;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;

/**
 * Mapper for php execution producer
 */
public class ExecutionProducerMapper extends AbstractOnDemandStatsProducerMapper<PHPScriptExecutionStats> {

    public ExecutionProducerMapper() {
            super(PHPScriptExecutionStats.class);
    }

    @Override
    public void updateStats(PHPScriptExecutionStats stats, StatsValues values) throws MappingException {

        stats.addRequest();

        try {

            stats.addExecutionTime(values.getAsLong("time"));
            stats.addMemoryUsage(values.getAsLong("memoryUsed"));
            stats.notifyRequestFinished();

            if(values.getAsBooleanOrDefault("error", false))
                stats.notifyError();

        } catch (ValueNotFoundException | ClassCastException e) {
            throw new MappingException("Failed to map php stats", e);
        }

    }

    @Override
    public IOnDemandStatsFactory<PHPScriptExecutionStats> getStatsFactory() {
        return PHPScriptExecutionStatsFactory.DEFAULT_INSTANCE;
    }

}
