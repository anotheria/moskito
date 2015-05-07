package net.anotheria.moskito.integration.cdi.monitor;

import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.integration.cdi.ProducerFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Alex Osadchy
 */
@Monitor
@Interceptor
public class MonitorInterceptor {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorInterceptor.class);

    /**
     *
     * @param ctx {@link InvocationContext}
     * @return result of the intercepted method
     * @throws Throwable
     */
    @AroundTimeout
    @AroundInvoke
    public Object intercept(final InvocationContext ctx) throws Throwable {
        final Method method = ctx.getMethod();
        final Monitor annotation = method.getDeclaringClass().getAnnotation(Monitor.class);

        // if producer id isn't specified by client - use class name
        final String producerId = annotation.producerId().isEmpty() ? method.getDeclaringClass().getSimpleName() : annotation.producerId();
        final String caseName = ctx.getMethod().getName();
        final OnDemandStatsProducer<ServiceStats> onDemandProducer = ProducerFinder.getProducer(producerId, annotation.category(), annotation.subsystem(), ServiceStatsFactory.DEFAULT_INSTANCE);

        final ServiceStats defaultStats = onDemandProducer.getDefaultStats();
        defaultStats.addRequest();

        ServiceStats methodStats = null;
        try {
            methodStats = onDemandProducer.getStats(caseName);
        } catch (OnDemandStatsProducerException e) {
             LOGGER.info("Couldn't get stats for case : " + caseName + ", probably limit reached");
        }

        if (methodStats != null) {
            methodStats.addRequest();
        }

        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(TracingUtil.buildCall(method, ctx.getParameters()), onDemandProducer);
        }

        final long startTime = System.nanoTime();

        Object ret = null;
        try {
            ret = ctx.proceed();
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
            throw t;
        } finally {
            final long exTime = System.nanoTime() - startTime;

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
}
