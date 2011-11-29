package net.java.dev.moskito.sql.stats;

import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 4:33 PM
 *         To change this template use File | Settings | File Templates.
 */
public class QueryStats extends AbstractStats {
    private StatValue queriesExecuted;

    public QueryStats() {
        this("SQL query", Constants.getDefaultIntervals());
    }

    public QueryStats(String aName) {
        this(aName, Constants.getDefaultIntervals());
    }

    public QueryStats(String name, Interval[] selectedIntervals) {
        queriesExecuted = StatValueFactory.createStatValue(0, "queriesExecuted", selectedIntervals);
    }

    @Override
    public String toStatsString(String aIntervalName, TimeUnit unit) {
        StringBuilder ret = new StringBuilder();

        ret.append(getName()).append(' ');
        ret.append(" queriesExecuted: ").append(queriesExecuted.getValueAsInt(aIntervalName));

        return ret.toString();
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName,
                                         TimeUnit timeUnit) {

        if (valueName == null)
            throw new AssertionError("Value name can't be null");
        valueName = valueName.toLowerCase();


        if ("queriesExecuted".equals(valueName))
            return "" + getQueriesExecuted(intervalName);

        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    private int getQueriesExecuted(String intervalName) {
        return queriesExecuted.getValueAsInt(intervalName);
    }

    public void update(int anQueriesExecuted){

	}

    public void notifyExecutedQuery(String statement) {
        queriesExecuted.increase();
    }

    public void notifyBeforeQuery(String statement) {
        //To change body of created methods use File | Settings | File Templates.
    }
}
