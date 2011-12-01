package net.java.dev.moskito.sql.util;

import net.java.dev.moskito.core.dynamic.OnDemandStatsProducerException;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.sql.stats.QueryStats;
import net.java.dev.moskito.sql.stats.QueryStringStats;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

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
     * Stats container.
     */
    private List<IStats> statsList;

    /**
     * A map where all stat and their ids (strings) are being stored.
     */
    private final Map<String, QueryStringStats> queryStatsMap;

    private static Logger log = Logger.getLogger(QueryProducer.class);

    private QueryStringStatsFactory factory;


    /**
     * A cached stat list for faster access.
     */
    private List<QueryStringStats> _cachedStatsList;
    private static final int LIMIT = 10000;

    /**
     * Creates a new producers object for a given pool.
     */
    public QueryProducer() {
        statsList = new ArrayList<IStats>(1);
        stats = new QueryStats("SQLQuery");
        statsList.add(stats);
        factory = new QueryStringStatsFactory();

        queryStatsMap = new HashMap<String, QueryStringStats>();
        _cachedStatsList = new CopyOnWriteArrayList<QueryStringStats>();
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
        QueryStringStats caseStats = null;
        try {
            caseStats = (QueryStringStats) getStringQueryStats(statement);
            if (caseStats != null)
                caseStats.addRequest();
        } catch (OnDemandStatsProducerException e) {
            log.info("Couldn't get stats for case : " + statement + ", probably limit reached");
        }

    }

    public void afterQuery(String statement, long callTime) {
        stats.notifyExecutedQuery(statement);
        QueryStringStats caseStats = null;
        try {
            caseStats = (QueryStringStats) getStringQueryStats(statement);
            if (caseStats != null) {
                 caseStats.notifyRequestFinished();
                caseStats.addExecutionTime(callTime);
            }
        } catch (OnDemandStatsProducerException e) {
            log.info("Couldn't get stats for case : " + statement + ", probably limit reached");
        }
    }

    public void failedQuery(String statement) {
        stats.notifyFailedQuery(statement);
        QueryStringStats caseStats = null;
        try {
            caseStats = (QueryStringStats) getStringQueryStats(statement);
            if (caseStats != null)
                caseStats.notifyError();
        } catch (OnDemandStatsProducerException e) {
            log.info("Couldn't get stats for case : " + statement + ", probably limit reached");
        }
    }

    public IStats getStringQueryStats(String statement) throws OnDemandStatsProducerException {
        QueryStringStats stringStats;
        synchronized (queryStatsMap) {
            stringStats = queryStatsMap.get(statement);
        }
        if (stringStats == null) {
            if (limitForNewEntriesReached())
                throw new OnDemandStatsProducerException("Limit reached");
            stringStats = (QueryStringStats) factory.createStatsObject(statement);
            synchronized (queryStatsMap) {
                //check whether another thread was faster
                if (queryStatsMap.get(statement) == null) {
                    queryStatsMap.put(statement, stringStats);
                    _cachedStatsList.add(stringStats);
                } else {
                    //ok, another thread was faster, we have to throw away our object.
                    stringStats = queryStatsMap.get(statement);
                }
            }
        }

        return stringStats;
    }

    protected boolean limitForNewEntriesReached() {
        return _cachedStatsList.size() > LIMIT;
    }



    @Override
    public String toString() {
        return getProducerId() + ", Total queries: " + Arrays.toString(getStats().toArray()) + ", Stat per query " + Arrays.toString(queryStatsMap.values().toArray());
    }

    public void reset() {
        stats.reset();
        _cachedStatsList.clear();
        queryStatsMap.clear();
    }
}
