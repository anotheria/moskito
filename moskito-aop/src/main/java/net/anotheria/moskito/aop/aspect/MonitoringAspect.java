package net.anotheria.moskito.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import net.anotheria.moskito.aop.annotation.Monitor;

/**
 * Aspect used to intercept @Monitor annotated classes method calls.
 *
 * @author Vitaliy Zhovtiuk
 * @author lrosenberg
 * @author bvanchuhov
 */
@Aspect
public class MonitoringAspect extends MonitoringBaseAspect{

	@Around(value = "execution(* *(..)) && (@annotation(method))")
    public Object doProfilingMethod(ProceedingJoinPoint pjp, Monitor method) throws Throwable {
    	return doProfiling(pjp, method.producerId(), method.subsystem(), method.category());
    }

    @Around(value = "execution(* *.*(..)) && @within(monitor) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
    public Object doProfilingClass(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
        return doProfiling(pjp, monitor.producerId(), monitor.subsystem(), monitor.category());
    }
}
