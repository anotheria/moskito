package net.anotheria.extensions.php.mappers.impl;

import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.extensions.php.exceptions.ValueNotFoundException;
import net.anotheria.extensions.php.mappers.AbstractOnDemandStatsProducerMapper;
import net.anotheria.extensions.php.mappers.StatsValues;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;

/**
 * Mapper for service stats
 */
public class ServiceStatsMapper extends AbstractOnDemandStatsProducerMapper<ServiceStats> {

    public ServiceStatsMapper() {
        super(ServiceStats.class);
    }

    @Override
    public void updateStats(ServiceStats stats, StatsValues values) throws MappingException {

        stats.addRequest();

        try {

            stats.addExecutionTime(values.getAsLong("time"));
            stats.notifyRequestFinished();

            if(values.getAsBooleanOrDefault("error", false))
                stats.notifyError();

        } catch (ValueNotFoundException | ClassCastException e) {
            throw new MappingException("Failed to map service stats", e);
        }

    }

    @Override
    public IOnDemandStatsFactory<ServiceStats> getStatsFactory() {
        return ServiceStatsFactory.DEFAULT_INSTANCE;
    }

}
