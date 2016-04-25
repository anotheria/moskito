package net.anotheria.moskito.extension.mongodb.decorator;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.extension.mongodb.MongodbStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for mongodbStats in Moskito WebUi
 */
public class MongodbStatsDecorator extends AbstractDecorator {

    private static final String[] CAPTIONS = {
            "Flushes",
            "TotalMsWrite",
            "AvgMsWrite",
            "LastMsWrite",
            "CurrentConnections",
            "AvailableConnections",
            "TotalCreatedConnections"
    };

    private static final String[] SHORT_EXPLANATIONS = {
            "Flushes of db",
            "TotalMsWrite data to disk",
            "AvgMsWrite data to disk",
            "LastMsWrite data to disk",
            "CurrentConnections from clients",
            "AvailableConnections from clients",
            "TotalCreatedConnections from clients"
    };

    private static final String[] EXPLANATIONS = {
            "Number of times the database has been flushed",
            "Total time (ms) that the mongod process spent writing data to disk",
            "Average time (ms) that the mongod process spent writing data to disk",
            "Time (ms) that the mongod process last spent writing data to disk",
            "Number of current connections to the database server from clients. This number includes the current shell connection " +
                    "as well as any inter-node connections to support a replica set or sharded cluster.",
            "Number of unused available connections that the database can provide. Consider this value in combination with " +
                    "the value of Current to understand the connection load on the database.",
            "Total created connections since db up"
    };


    public MongodbStatsDecorator() {
        super("MongoMonitor", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }

    @Override
    public List<StatValueAO> getValues(IStats stats, String interval, TimeUnit unit) {
        MongodbStats mongodbStats = ((MongodbStats) stats);
        List<StatValueAO> bean = new ArrayList<>(CAPTIONS.length);
        int i = 0;
        bean.add(new StringValueAO(CAPTIONS[i++], mongodbStats.getFlushes().getValueAsString(interval)));
        bean.add(new LongValueAO(CAPTIONS[i++], mongodbStats.getTotal_ms_write().getValueAsLong(interval)));
        bean.add(new DoubleValueAO(CAPTIONS[i++], mongodbStats.getAvg_ms_write().getValueAsDouble(interval)));
        bean.add(new LongValueAO(CAPTIONS[i++], mongodbStats.getLast_ms_write().getValueAsLong(interval)));
        bean.add(new LongValueAO(CAPTIONS[i++], mongodbStats.getCurrent_connections().getValueAsLong(interval)));
        bean.add(new LongValueAO(CAPTIONS[i++], mongodbStats.getAvailable_connections().getValueAsLong(interval)));
        bean.add(new LongValueAO(CAPTIONS[i], mongodbStats.getTotal_created_connections().getValueAsLong(interval)));
        return bean;
    }
}
