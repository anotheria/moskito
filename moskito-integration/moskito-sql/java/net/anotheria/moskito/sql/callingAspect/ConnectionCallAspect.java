package net.anotheria.moskito.sql.callingAspect;

import net.anotheria.moskito.sql.util.QueryProducer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
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
            "&& args(smt) && !within(net.anotheria.moskito.sql.callingAspect.ConnectionCallAspect)";

    private QueryProducer queryProducer;

    public ConnectionCallAspect() {
        queryProducer = new QueryProducer();
    }

    @Pointcut(JDBC_CALLS)
    public void connectionService(String smt) {
    }

    @Around(value = "connectionService(smt)", argNames = "pjp,smt")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, String smt) throws Throwable {
        long callTime = System.nanoTime();
        // start stopwatch
        System.out.println(smt);
        queryProducer.beforeQuery(smt);
        Object retVal = pjp.proceed();
        queryProducer.afterQuery(smt, System.nanoTime() - callTime);

        // stop stopwatch
        return retVal;
    }

    /**
     * Method executed after any jdbc call executed.
     *
     * @param smt sql statement
     */
    @AfterThrowing(JDBC_CALLS)
    public void afterThrowingQueryCall(String smt) {
        queryProducer.failedQuery(smt);
    }
}
