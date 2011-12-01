package net.java.dev.moskito.annotation.stat;

import net.java.dev.moskito.core.predefined.RequestOrientedStats;
import net.java.dev.moskito.core.stats.Interval;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 12/1/11
 *         Time: 10:10 PM
 *         To change this template use File | Settings | File Templates.
 */
public class MethodCallStats extends RequestOrientedStats {

	/**
	 * Creates a new MethodStats object with the given method name.
	 * @param aMethodName
	 */
	public MethodCallStats(String aMethodName){
		super(aMethodName);
	}

	public MethodCallStats(){
		super();
	}

	public MethodCallStats(String aMethodName, Interval[] intervals){
		super(aMethodName, intervals);
	}

    @Override
    public String toString() {
        return toStatsString();
    }
}
