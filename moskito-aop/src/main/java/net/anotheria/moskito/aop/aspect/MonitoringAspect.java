package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.util.AnnotationUtils;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.tracer.TracerRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;

/**
 * Aspect used to intercept @MonitorClass annotated classes method calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>, lrosenberg
 * @author bvanchuhov
 */
@Aspect
public class MonitoringAspect extends AbstractMoskitoAspect{

	/**
	 * Factory constant is needed to prevent continuous reinstantiation of ServiceStatsFactory objects.
	 */
	private static final ServiceStatsFactory FACTORY = new ServiceStatsFactory();

	@Around(value = "execution(* *(..)) && (@annotation(method))")
    public Object doProfilingMethod(ProceedingJoinPoint pjp, Monitor method) throws Throwable {
    	return doProfiling(pjp, method.producerId(), method.subsystem(), method.category());
    }
 /* */
    @Around(value = "execution(* *.*(..)) && (!@annotation(net.anotheria.moskito.aop.annotation.DontMonitor))")
    public Object doProfilingClass(ProceedingJoinPoint pjp) throws Throwable {
        Class clazz = pjp.getSignature().getDeclaringType();
        if (clazz.getAnnotations().length == 0) {
            return pjp.proceed();
        }

        Monitor monitor = AnnotationUtils.findAnnotation(clazz, Monitor.class);
        if (monitor == null) {
            return pjp.proceed();
        }

        return doProfiling(pjp, monitor.producerId(), monitor.subsystem(), monitor.category());
    }

    /*  */
    private Object doProfiling(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		OnDemandStatsProducer<ServiceStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, false, FACTORY, true);
		String producerId = producer.getProducerId();

    	String caseName = pjp.getSignature().getName();
    	ServiceStats defaultStats = producer.getDefaultStats();
    	ServiceStats methodStats = producer.getStats(caseName);

        final Object[] args = pjp.getArgs();
        final String method = pjp.getSignature().getName();
        defaultStats.addRequest();
        if (methodStats != null) {
            methodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;

        TracerRepository tracerRepository = TracerRepository.getInstance();
        boolean tracePassingOfThisProducer = tracerRepository.isTracingEnabledForProducer(producerId);

        StringBuilder call = null;
        if (currentTrace != null || tracePassingOfThisProducer) {
            call = new StringBuilder(producerId).append('.').append(method).append("(");
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    call.append(args[i]);
                    if (i < args.length - 1) {
                        call.append(", ");
                    }
                }
            }
            call.append(")");
        }
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(call.toString(), producer);
        }
        long startTime = System.nanoTime();
        Object ret = null;
        try {
            ret = pjp.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            defaultStats.notifyError();
            if (methodStats != null) {
                methodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw e.getCause();
        } catch (Throwable t) {
            defaultStats.notifyError();
            if (methodStats != null) {
                methodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            if (tracePassingOfThisProducer) {
                call.append(" ERR "+t.getMessage());
            }
            throw t;
        } finally {
            long exTime = System.nanoTime() - startTime;
            defaultStats.addExecutionTime(exTime);
            if (methodStats != null) {
                methodStats.addExecutionTime(exTime);
            }
            defaultStats.notifyRequestFinished();
            if (methodStats != null) {
                methodStats.notifyRequestFinished();
            }
            if (currentStep != null) {
                currentStep.setDuration(exTime);
                try {
                    currentStep.appendToCall(" = " + ret);
                } catch (Throwable t) {
                    currentStep.appendToCall(" = ERR: " + t.getMessage() + " (" + t.getClass() + ")");
                }
            }
            if (currentTrace != null) {
                currentTrace.endStep();
            }

            if (tracePassingOfThisProducer) {
                call.append(" = " + ret);
                tracerRepository.addTracedExecution(producerId, call.toString(), Thread.currentThread().getStackTrace(), exTime);
            }
        }
    }
}
