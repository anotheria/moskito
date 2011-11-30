package net.java.dev.moskito.sql.util;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.sql.stats.QueryStats;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Builtin producer for values supplied by jmx for the operation system.
 *
 * @author lrosenberg
 */
public class QueryProducer implements IStatsProducer {
    public static final String PRODUCER_ID = "Query";
    /**
     * Associated stats.
     */
    private QueryStats stats;
    /**
     * Stats container
     */
    private List<IStats> statsList;

    private static Logger log = Logger.getLogger(QueryProducer.class);

    /**
     * Creates a new producers object for a given pool.
     */
    public QueryProducer() {
        statsList = new ArrayList<IStats>(1);
        stats = new QueryStats();
        statsList.add(stats);

        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
    }

    @Override
    public String getCategory() {
        return "query";
    }

    @Override
    public String getProducerId() {
        return PRODUCER_ID;
    }

    @Override
    public List<IStats> getStats() {
        return statsList;
    }

    @Override
    public String getSubsystem() {
        return "default";
    }


    public void beforeQuery(String statement) {
        stats.notifyBeforeQuery(statement);
    }

    public void afterQuery(String statement) {
        stats.notifyExecutedQuery(statement);
    }


    public void failedQuery(String statement) {
        stats.notifyFailedQuery(statement);
    }

    public static void main(String a[]) {
        new QueryProducer();
    }

    @Override
    public String toString() {
        return getProducerId() + " " + getStats();
    }

    public void reset() {
        stats.reset();
    }
}
