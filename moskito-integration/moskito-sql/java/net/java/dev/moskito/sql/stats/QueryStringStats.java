package net.java.dev.moskito.sql.stats;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 4:33 PM
 *         To change this template use File | Settings | File Templates.
 */
public class QueryStringStats extends RequestOrientedStats {
    public QueryStringStats(String aQueryString) {
        super(aQueryString, Constants.getDefaultIntervals());
    }

    public QueryStringStats(String aQueryString, Interval[] intervals) {
        super(aQueryString, intervals);
    }

    @Override
    public String toString() {
        return toStatsString();
    }
}
