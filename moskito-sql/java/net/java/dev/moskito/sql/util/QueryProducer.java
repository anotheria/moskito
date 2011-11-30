package net.java.dev.moskito.sql.util;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.sql.stats.QueryStats;
import net.java.dev.moskito.sql.stats.QueryStringStats;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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
    private final Map<String, IStats> queryStatsMap;

    private static Logger log = Logger.getLogger(QueryProducer.class);

    private QueryStringStatsFactory factory;


    /**
     * A cached stat list for faster access.
     */
    private List<IStats> _cachedStatsList;
    private static final int LIMIT = 10000;

    /**
     * Creates a new producers object for a given pool.
     */
    public QueryProducer() {
        statsList = new ArrayList<IStats>(1);
        stats = new QueryStats();
        statsList.add(stats);
        factory = new QueryStringStatsFactory();

        queryStatsMap = new HashMap<String, IStats>();
        _cachedStatsList = new CopyOnWriteArrayList<IStats>();
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
        QueryStringStats caseStats = (QueryStringStats) getStringQueryStats(statement);
        if (caseStats != null)
            caseStats.addRequest();

    }

    public void afterQuery(String statement) {
        stats.notifyExecutedQuery(statement);
        QueryStringStats caseStats = (QueryStringStats) getStringQueryStats(statement);
        if (caseStats != null)
            caseStats.notifyRequestFinished();
    }

    public void failedQuery(String statement) {
        stats.notifyFailedQuery(statement);
        QueryStringStats caseStats = (QueryStringStats) getStringQueryStats(statement);
        if (caseStats != null)
            caseStats.notifyError();
    }

    public IStats getStringQueryStats(String statement) {
        IStats stat;
        synchronized (queryStatsMap) {
            stat = queryStatsMap.get(statement);
        }
        if (stat == null) {
            if (limitForNewEntriesReached())
                //throw new OnDemandStatsProducerException("Limit reached");
                stat = factory.createStatsObject(statement);
            synchronized (queryStatsMap) {
                //check whether another thread was faster
                if (queryStatsMap.get(statement) == null) {
                    queryStatsMap.put(statement, stat);
                    _cachedStatsList.add(stat);
                } else {
                    //ok, another thread was faster, we have to throw away our object.
                    stat = queryStatsMap.get(statement);
                }
            }
        }

        return stat;
    }

    protected boolean limitForNewEntriesReached() {
        return _cachedStatsList.size() > LIMIT;
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
