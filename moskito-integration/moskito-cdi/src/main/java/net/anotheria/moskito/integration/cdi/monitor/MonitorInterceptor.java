package net.anotheria.moskito.integration.cdi.monitor;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.integration.cdi.AbstractInterceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Monitor interceptor.
 *
 * @author Alex Osadchy
 */
@Monitor
@Interceptor
public class MonitorInterceptor extends AbstractInterceptor<ServiceStats> implements Serializable {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected final Class<ServiceStats> getStatsClass() {
        return ServiceStats.class;
    }

    @Override
    protected final IOnDemandStatsFactory<ServiceStats> getStatsFactory() {
        return ServiceStatsFactory.DEFAULT_INSTANCE;
    }

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
        final Monitor annotation = getAnnotationFromContext(ctx, Monitor.class);

        // if producer id isn't specified by client - use class name
        final String producerId = annotation.producerId().isEmpty() ? method.getDeclaringClass().getSimpleName() : annotation.producerId();

        final OnDemandStatsProducer onDemandProducer = getProducer(producerId, annotation.category(), annotation.subsystem());

        if (onDemandProducer == null) {
            return proceed(ctx);
        }

        final ServiceStats defaultStats = getDefaultStats(onDemandProducer);
        final ServiceStats methodStats = getStats(onDemandProducer, ctx.getMethod().getName());

        if (defaultStats != null)
            defaultStats.addRequest();

        if (methodStats != null)
            methodStats.addRequest();

        final TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        final CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(TracingUtil.buildCall(method, ctx.getParameters()), onDemandProducer);
        }

        final long startTime = System.nanoTime();

        Object ret = null;
        try {
            ret = ctx.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            if (defaultStats != null)
                defaultStats.notifyError();
            if (methodStats != null)
                methodStats.notifyError();
            if (currentStep != null)
                currentStep.setAborted();

            throw e.getCause();
        } catch (Throwable t) {
            if (defaultStats != null)
                defaultStats.notifyError();
            if (methodStats != null)
                methodStats.notifyError();
            if (currentStep != null)
                currentStep.setAborted();

            throw t;
        } finally {
            final long exTime = System.nanoTime() - startTime;

            if (defaultStats != null) {
                defaultStats.addExecutionTime(exTime);
                defaultStats.notifyRequestFinished();
            }

            if (methodStats != null) {
                methodStats.addExecutionTime(exTime);
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
