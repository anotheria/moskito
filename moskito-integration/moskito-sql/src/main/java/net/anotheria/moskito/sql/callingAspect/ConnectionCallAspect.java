package net.anotheria.moskito.sql.callingAspect;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.EntryCountLimitedOnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.sql.stats.QueryStats;
import net.anotheria.moskito.sql.stats.QueryStatsFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:14 PM
 */
@Aspect
public class ConnectionCallAspect {

	private static Logger log = LoggerFactory.getLogger(ConnectionCallAspect.class);

	/**
	 * List of jdbc calls for interception.
	 */
	private static final String SQL_STATEMENT_EXECUTE_CALLS = "(call(java.sql.ResultSet java.sql.Statement.executeQuery(String))" +
			"|| call(int java.sql.Statement.executeUpdate(String))" +
			"|| call(int java.sql.Statement.executeUpdate(String, int))" +
			"|| call(int java.sql.Statement.executeUpdate(String, int[]))" +
			"|| call(int java.sql.Statement.executeUpdate(String, String[]))" +
			"|| call(boolean java.sql.Statement.execute(String))" +
			"|| call(boolean java.sql.Statement.execute(String,int))" +
			"|| call(boolean java.sql.Statement.execute(String,int[]))" +
			"|| call(boolean java.sql.Statement.execute(String,String[]))" +
			"|| execution(long java.sql.Statement.executeLargeUpdate(String)) " +
			"|| execution(long java.sql.Statement.executeLargeUpdate(String,String[])) " +
			"|| execution(long java.sql.Statement.executeLargeUpdate(String, int)) " +
			"|| execution(long java.sql.Statement.executeLargeUpdate(String, int[])) " +
			')' +
			"&& args(smt)";

	private static final String SQL_PREPARED_STATEMENT_EXECUTE_CALLS = "call(boolean java.sql.PreparedStatement.execute())" +
			"|| call(long java.sql.PreparedStatement.executeLargeUpdate())" +
			"|| call(java.sql.ResultSet java.sql.PreparedStatement.executeQuery())" +
			"|| call(int java.sql.PreparedStatement.executeUpdate()) ";

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
		producer = new EntryCountLimitedOnDemandStatsProducer<QueryStats>("SQLQueries", "sql", "sql", QueryStatsFactory.DEFAULT_INSTANCE, 1000);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
	}

	@Pointcut(SQL_STATEMENT_EXECUTE_CALLS)
	public void statementExecuteCalls(String smt) {
	}

	@Pointcut(SQL_PREPARED_STATEMENT_EXECUTE_CALLS)
	public void preparedStatementExecuteCalls() {
	}

	@Around(value = "statementExecuteCalls(statement)", argNames = "pjp,statement")
	public Object doBasicProfiling(ProceedingJoinPoint pjp, String statement) throws Throwable {
		return doMoskitoProfiling(pjp, statement);
	}

	@Around(value = "preparedStatementExecuteCalls()", argNames = "pjp")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		String preparedStatement = pjp.getTarget().toString();
		String statement = preparedStatement.substring(preparedStatement.indexOf(":") + 2);
		return doMoskitoProfiling(pjp, statement);
	}

	/* test scope */ static String removeParametersFromStatement(String statement){
		return statement.replaceAll("'.+?'", "?").replaceAll(",\\s*\\d+", ", ?")
				.replaceAll("\\(\\s*\\d+", "(?").replaceAll("=\\s*\\d+", "=?");
	}

	/**
	 * Perform MoSKito profiling.
	 *
	 * @param pjp       {@link ProceedingJoinPoint}
	 * @param statement sql statement
	 * @return invocation result - both with profiling
	 * @throws Throwable on errors
	 */
	private Object doMoskitoProfiling(ProceedingJoinPoint pjp, String statement) throws Throwable {
		String statementGeneralized = removeParametersFromStatement(statement);
		long callTime = System.nanoTime();
		QueryStats cumulatedStats = producer.getDefaultStats();
		QueryStats statementStats = null;
		try{
			statementStats = producer.getStats(statementGeneralized);
		}catch(OnDemandStatsProducerException limitReachedException){
			log.warn("Query limit reached for query "+statement+", --> "+statementGeneralized);
		}

		//add Request Count, increase CR,MCR
		cumulatedStats.addRequest();
		if (statementStats != null)
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
			cumulatedStats.notifyError(t);
			if (statementStats != null)
				statementStats.notifyError();
			throw t;
		} finally {
			final long callDurationTime = System.nanoTime() - callTime;
			//add execution time
			cumulatedStats.addExecutionTime(callDurationTime);
			if (statementStats != null)
				statementStats.addExecutionTime(callDurationTime);
			//notify request finished / decrease CR/MCR
			cumulatedStats.notifyRequestFinished();
			if (statementStats != null) {
				statementStats.notifyRequestFinished();
			}

			addTrace(statement, success, callDurationTime);

		}


	}

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
			TraceStep currentStep = currentTrace.startStep((isSuccess ? EMPTY : SQL_QUERY_FAILED) + "SQL : (' " + statement + "')", producer, statement);
			if (!isSuccess)
				currentStep.setAborted();
			currentStep.setDuration(duration);
			currentTrace.endStep();
		}

	}


}
