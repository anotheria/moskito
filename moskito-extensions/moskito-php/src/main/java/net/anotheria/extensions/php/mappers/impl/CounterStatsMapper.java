package net.anotheria.extensions.php.mappers.impl;


import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.extensions.php.exceptions.ValueNotFoundException;
import net.anotheria.extensions.php.mappers.AbstractOnDemandStatsProducerMapper;
import net.anotheria.extensions.php.mappers.StatsValues;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.counter.CounterStatsFactory;

public class CounterStatsMapper extends AbstractOnDemandStatsProducerMapper<CounterStats> {

    public CounterStatsMapper() {
        super(CounterStats.class);
    }

    @Override
    public void updateStats(CounterStats stats, StatsValues values) throws MappingException {

        try {
            stats.incBy(values.getAsLongOrDefault("inc", 0));
        } catch (ValueNotFoundException e) {
            throw new MappingException("Failed to map counter stats", e);
        }

    }

    @Override
    public IOnDemandStatsFactory<CounterStats> getStatsFactory() {
        return CounterStatsFactory.DEFAULT_INSTANCE;
    }

}
