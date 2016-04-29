package net.anotheria.moskito.sql.callingAspect;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.sql.stats.QueryStats;
import net.anotheria.moskito.sql.stats.QueryStatsFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:14 PM
 */
@Aspect
public class ConnectionCallAspect {

	/**
	 * List of jdbc calls for interception.
	 */
	private static final String JDBC_CALLS = "  (call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String)) " +
			"|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String))" +
			"|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, String[]))" +
			"|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int))" +
			"|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int[]))" +
			"|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int, int))" +
			"|| call(java.sql.PreparedStatement java.sql.Connection.prepareStatement(String, int, int, int))" +
			"|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String, int, int))" +
			"|| call(java.sql.CallableStatement java.sql.Connection.prepareCall(String, int, int, int))" +
			"|| call(java.sql.ResultSet java.sql.Statement.executeQuery(String))" +
			"|| call(int java.sql.Statement.executeUpdate(String))" +
			"|| call(int java.sql.Statement.executeUpdate(String, int))" +
			"|| call(int java.sql.Statement.executeUpdate(String, int[]))" +
			"|| call(int java.sql.Statement.executeUpdate(String, String[]))" +
			"|| call(boolean java.sql.Statement.execute(String))" +
			"|| call(boolean java.sql.Statement.execute(String,int))" +
			"|| call(boolean java.sql.Statement.execute(String,int[]))" +
			"|| call(boolean java.sql.Statement.execute(String,String[]))" +
			"|| call(void java.sql.Statement.addBatch(String))" +
			")" +
			"&& args(smt) && !within(net.anotheria.moskito.sql.aspect.ConnectionCallAspect)";
	/**
	 * Query failed.
	 */
	private static final String SQL_QUERY_FAILED = " FAILED!!! ";
	/**
	 * Empty string .
	 */
	private static final String EMPTY = "";

	/**
	 * Query stats producer.
	 */
	private OnDemandStatsProducer<QueryStats> producer;

	/**
	 * Constructor.
	 */
	public ConnectionCallAspect() {
		producer = new OnDemandStatsProducer<QueryStats>("SQLQueries", "sql", "sql", QueryStatsFactory.DEFAULT_INSTANCE);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
	}

	@Pointcut(JDBC_CALLS)
	public void connectionService(String smt) {
	}

	@Around(value = "connectionService(statement)", argNames = "pjp,statement")
	public Object doBasicProfiling(ProceedingJoinPoint pjp, String statement) throws Throwable {
		long callTime = System.nanoTime();
		QueryStats cumulatedStats = producer.getDefaultStats();
		QueryStats statementStats = producer.getStats(statement);
		//add Request Count, increase CR,MCR
		cumulatedStats.addRequest();
		if (statementStats!=null)
			statementStats.addRequest();
		// start stopwatch
		//System.out.println(smt);
		boolean success = true;
		try {
			Object retVal = pjp.proceed();
			// stop stopwatch
			return retVal;
		} catch (Throwable t) {
			success = false;
			cumulatedStats.notifyError();
			if (statementStats!=null)
				statementStats.notifyError();
			throw t;
		} finally{
			final long callDurationTime = System.nanoTime() - callTime;
			//add execution time
			cumulatedStats.addExecutionTime(callDurationTime);
			if (statementStats!=null)
				statementStats.addExecutionTime(callDurationTime);
			//notify request finished / decrease CR/MCR
			cumulatedStats.notifyRequestFinished();
			if (statementStats != null){
				statementStats.notifyRequestFinished();
			}

			addTrace(statement, success, callDurationTime);

		}


	}

	/**
	 * Method executed after any jdbc call executed.
	 *
	 * @param smt sql statement
	 */
	//@AfterThrowing(JDBC_CALLS)
	//public void afterThrowingQueryCall(String smt) {
	//	queryProducer.failedQuery(smt);
	//}

	/**
	 * Perform additional profiling - for Journey stuff.
	 *
	 * @param statement prepared statement
	 * @param isSuccess is success
	 */
	private void addTrace(String statement, final boolean isSuccess, final long duration) {
		TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
		CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;
		if (currentTrace != null) {
			TraceStep currentStep = currentTrace.startStep((isSuccess ? EMPTY : SQL_QUERY_FAILED) + "SQL : (' " + statement + "')", producer);
			if (!isSuccess)
				currentStep.setAborted();
			currentStep.setDuration(duration);
			currentTrace.endStep();
		}

	}


}
