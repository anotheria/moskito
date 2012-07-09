package net.java.dev.moskito.integration.cdi;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.calltrace.TraceStep;
import net.java.dev.moskito.core.calltrace.TracedCall;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducerException;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Generic call interceptor.
 *
 * @author Vitaliy Zhovtiuk, Leon Rosenberg
 */
public class CallInterceptor implements Serializable {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = -2722113871558244179L;

    /**
     * Logger.
     */
    @Inject
    private Logger log;
    /**
     * The internal producer instances.
     */
    private ConcurrentMap<String, OnDemandStatsProducer> producers = new ConcurrentHashMap<String, OnDemandStatsProducer>();


    /**
     * Around method invoke.
     *
     * @param ctx context
     * @return method result
     * @throws Throwable any exception
     */
    // CHECKSTYLE:OFF
    @AroundInvoke
    public Object aroundInvoke(InvocationContext ctx) throws Throwable {

        String producerId = extractProducerId(ctx);
        OnDemandStatsProducer onDemandProducer = producers.get(producerId);
        if (onDemandProducer == null) {
            onDemandProducer = new OnDemandStatsProducer(producerId, getCategory(), getSubsystem(), new ServiceStatsFactory());
            OnDemandStatsProducer p = producers.putIfAbsent(producerId, onDemandProducer);
            if (p == null) {
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
            } else {
                onDemandProducer = p;
            }
        }

        ServiceStats defaultStats = (ServiceStats) onDemandProducer.getDefaultStats();
        ServiceStats methodStats = null;
        String caseName = extractCaseName(ctx);
        try {
            if (caseName != null) {
                methodStats = (ServiceStats) onDemandProducer.getStats(caseName);
            }
        } catch (OnDemandStatsProducerException e) {
        	log.info("Couldn't get stats for case : " + caseName + ", probably limit reached");
        }

        final Object[] args = ctx.getParameters();
        final Method method = ctx.getMethod();
        defaultStats.addRequest();
        if (methodStats != null) {
            methodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;
        if (currentTrace != null) {
            StringBuilder call = new StringBuilder(getClassName(ctx.getTarget())).append('.').append(method.getName()).append("(");
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    call.append(args[i]);
                    if (i < args.length - 1) {
                        call.append(", ");
                    }
                }
            }
            call.append(")");
            currentStep = currentTrace.startStep(call.toString(), onDemandProducer);
        }
        long startTime = System.nanoTime();
        Object ret = null;
        try {
            ret = ctx.proceed();
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
    // CHECKSTYLE:ON

    /**
     * Extract case name from execution context.
     *
     * @param ctx execution context
     * @return case name
     */
    private String extractCaseName(InvocationContext ctx) {
        return ctx.getMethod().toGenericString();
    }

    /**
     * Get producer id.
     *
     * @param ctx invocation context
     * @return string producer id
     */
    private String extractProducerId(InvocationContext ctx) {
        return ctx.getMethod().getDeclaringClass().getName();
    }

    public String getCategory() {
        return "service";
    }

    public String getSubsystem() {
        return "default";
    }

    /**
     * Get class name by class implementation.
     *
     * @param implementation current implementation object
     * @return string class name
     */
    private static String getClassName(Object implementation) {
        String className = implementation.getClass().getName();
        return className.substring(className.lastIndexOf(".") + 1);
    }

}
