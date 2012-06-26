package net.java.dev.moskito.annotation.callingAspect;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.calltrace.TraceStep;
import net.java.dev.moskito.core.calltrace.TracedCall;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 */
@Aspect
public class MethodCallAspect {
	
    /**
     * Annotation advice.
     */
    private static final String METHOD_CALLS = "execution(@net.java.dev.moskito.annotation.MonitorMethod * *.*(..)) || execution(* (@net.java.dev.moskito.annotation.MonitorClass *).*(..))";
    
    private ConcurrentMap<String, OnDemandStatsProducer> producers = new ConcurrentHashMap<String, OnDemandStatsProducer>();


    public MethodCallAspect() {
    }

    @Pointcut(METHOD_CALLS)
    public void methodService() {
    }

    @Around(value = "methodService()")
    public Object doProfiling(ProceedingJoinPoint pjp) throws Throwable {
    	
    	String producerId = pjp.getSignature().getDeclaringTypeName();
    	try{
    		producerId = producerId.substring(producerId.lastIndexOf('.')+1);
    	}catch(RuntimeException ignored){/* ignored */}
    	OnDemandStatsProducer producer = producers.get(producerId);
    	if (producer==null){
    		producer = new OnDemandStatsProducer(producerId, getCategory(), getSubsystem(), new ServiceStatsFactory());
    		OnDemandStatsProducer p = producers.putIfAbsent(producerId, producer);
    		if (p==null){
    			ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
    		}else{
    			producer = p;
    		}
    	}
    	
    	String caseName = pjp.getSignature().getName();
    	ServiceStats defaultStats = (ServiceStats) producer.getDefaultStats();
    	ServiceStats methodStats = (ServiceStats) producer.getStats(caseName);

        final Object[] args = pjp.getArgs();
        final String method = pjp.getSignature().getName();
        defaultStats.addRequest();
        if (methodStats != null) {
            methodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;
        if (currentTrace != null) {
            StringBuilder call = new StringBuilder(producerId).append('.').append(method).append("(");
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    call.append(args[i]);
                    if (i < args.length - 1) {
                        call.append(", ");
                    }
                }
            }
            call.append(")");
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
            //System.out.println("exception of class: "+e.getCause()+" is thrown");
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
        }
    }

    public String getCategory() {
        return "annotated";
    }

    public String getSubsystem() {
        return "default";
    }

    public void reset() {
    	producers.clear();
    }
}
