package net.anotheria.moskito.core.util.statistics;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.stats.Interval;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.09.21 22:32
 */
public class StatisticStats extends ServiceStats {
	public StatisticStats(String aMethodName){
		super(aMethodName);
	}

	public StatisticStats(){
		super();
	}

	public StatisticStats(String aMethodName, Interval[] intervals){
		super(aMethodName, intervals);
	}

	public void update(long totalRequests1m, long totalRequestTime1m, long errorCount1m, long currentRequestCount1m, long maxCurrentRequestCount1m, long maxRequestTime1m){
		internalGetTotalRequests().setValueAsLong(totalRequests1m);
		internalGetTotalTime().setValueAsLong(totalRequestTime1m);
		internalGetErrorCount().setValueAsLong(errorCount1m);
		internalGetCurrentRequestCount().setValueAsLong(currentRequestCount1m);
		internalGetMaxConcurrentRequestCount().setValueAsLong(maxCurrentRequestCount1m);
		internalGetMaxTime().setValueAsLong(maxRequestTime1m);
	}

}
