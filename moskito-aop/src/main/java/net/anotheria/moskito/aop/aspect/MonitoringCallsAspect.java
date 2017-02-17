package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.MonitorCalls;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Aspect used to intercept @MonitorCalls annotated classes method calls.
 */
@Aspect
public class MonitoringCallsAspect extends MonitoringBaseAspect{

	@Around(value = "call(* *(..)) && (@annotation(method))")
    public void doProfilingMethod(ProceedingJoinPoint pjp, MonitorCalls method) throws Throwable {
        doProfiling(pjp, method.producerId(), method.subsystem(), method.category());
    }

    @Around(value = "call(* *.*(..)) && @within(monitor) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
    public void doProfilingClass(ProceedingJoinPoint pjp, MonitorCalls monitor) throws Throwable {
        doProfiling(pjp, monitor.producerId(), monitor.subsystem(), monitor.category());
    }

}
