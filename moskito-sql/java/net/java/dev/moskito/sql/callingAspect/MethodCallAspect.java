package net.java.dev.moskito.sql.callingAspect;

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
public class MethodCallAspect {

    /**
     * List of jdbc calls for interception.
     */
    private static final String METHOD_CALLS = "call(* *.*(..)) && @annotation(net.java.dev.moskito.sql.annotation.MonitorMethod)";

    public MethodCallAspect() {
    }

    @Pointcut(METHOD_CALLS)
    public void methodService() {
    }

    @Around(value = "methodService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        System.out.println(pjp.getSignature());
        // stop stopwatch
        return pjp.proceed();
    }
}
