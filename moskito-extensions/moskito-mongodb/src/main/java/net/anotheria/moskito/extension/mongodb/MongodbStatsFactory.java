package net.anotheria.moskito.extension.mongodb;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;

/**
 * Factory for mongodb stats
 */
public class MongodbStatsFactory implements IOnDemandStatsFactory<MongodbStats> {
    @Override
    public MongodbStats createStatsObject(String name) {
        return new MongodbStats(name);
    }
}
