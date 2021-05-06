package net.anotheria.moskito.integration.cdi.monitor;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;
import net.anotheria.moskito.integration.cdi.AbstractInterceptor;
import net.anotheria.moskito.integration.cdi.StatName;

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
     * SerialVersionUID.
     */
    private static final long serialVersionUID = -984691445584460188L;

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
        final Object[] parameters = ctx.getParameters();
        final Monitor annotation = getAnnotationFromContext(ctx, Monitor.class);

        // if producer id isn't specified by client - use class name
        String className = method.getDeclaringClass().getSimpleName();
        final String producerId = annotation.producerId().isEmpty() ? className : annotation.producerId();

        final OnDemandStatsProducer onDemandProducer = getProducer(method.getDeclaringClass(), producerId, annotation.category(), annotation.subsystem(), true);

        if (onDemandProducer == null) {
            return proceed(ctx);
        }

        String methodName = getMethodName(method);
        final ServiceStats defaultStats = getDefaultStats(onDemandProducer);
        final ServiceStats methodStats = getStats(onDemandProducer, methodName);

        if (defaultStats != null)
            defaultStats.addRequest();

        if (methodStats != null)
            methodStats.addRequest();

        final TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;

        if (currentTrace != null) {
            String optionalPrefix = null;
            String call = TracingUtil.buildCall(className, methodName, parameters, optionalPrefix).toString();
            currentStep = currentTrace.startStep(call, onDemandProducer, method.getName());
        }

		MoSKitoContext context = MoSKitoContext.get();
        TracerRepository tracerRepository = TracerRepository.getInstance();
		//only trace this producer if no tracers have been fired yet.
		boolean tracePassingOfThisProducer =
				context.hasTracerFired() ?
						false :
						tracerRepository.isTracingEnabledForProducer(producerId);
        Trace trace = null;
        boolean journeyStartedByMe = false;

        //we create trace here already, because we want to reserve a new trace id.
        if (tracePassingOfThisProducer){
            trace = new Trace();
			context.setTracerFired();
		}

        if (currentTrace == null && tracePassingOfThisProducer){
            //ok, we will create a new journey on the fly.
            String journeyCallName = Tracers.getCallName(trace);
            RunningTraceContainer.startTracedCall(journeyCallName);
            journeyStartedByMe = true;

            currentTrace = (CurrentlyTracedCall) RunningTraceContainer.getCurrentlyTracedCall();
        }

        StringBuilder call = null;
        if (currentTrace != null || tracePassingOfThisProducer) {
            call = TracingUtil.buildCall(producerId, methodName, parameters, tracePassingOfThisProducer ? Tracers.getCallName(trace) : null);
        }
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(call.toString(), onDemandProducer, method.getName());
        }

        final long startTime = System.nanoTime();

        Object ret = null;
        try {
            ret = ctx.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            if (defaultStats != null)
                defaultStats.notifyError(e.getTargetException());
            if (methodStats != null)
                methodStats.notifyError();
            if (currentStep != null)
                currentStep.setAborted();

            throw e.getCause();
        } catch (Throwable t) {
            if (defaultStats != null)
                defaultStats.notifyError(t);
            if (methodStats != null)
                methodStats.notifyError();
            if (currentStep != null)
                currentStep.setAborted();
            if (tracePassingOfThisProducer) {
                call.append(" ERR ").append(t.getMessage());
            }

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
                    currentStep.appendToCall(" = " + TracingUtil.parameter2string(ret));
                } catch (Throwable t) {
                    currentStep.appendToCall(" = ERR: " + t.getMessage() + " (" + t.getClass() + ')');
                }
            }

            if (currentTrace != null) {
                currentTrace.endStep();
            }

            if (tracePassingOfThisProducer) {
                call.append(" = ").append(TracingUtil.parameter2string(ret));
                trace.setCall(call.toString());
                trace.setDuration(exTime);
                trace.setElements(Thread.currentThread().getStackTrace());

                if (journeyStartedByMe) {
                    //now finish the journey.
                    Journey myJourney = JourneyManagerFactory.getJourneyManager().getOrCreateJourney(Tracers.getJourneyNameForTracers(producerId));
                    myJourney.addCall((CurrentlyTracedCall) RunningTraceContainer.endTrace());
                    RunningTraceContainer.cleanup();
                }


                tracerRepository.addTracedExecution(producerId, trace);
            }
        }
    }

    /**
     * Returns name for monitored method.
     *
     * @param method the monitored {@link Method}
     * @return the method name or custom name provided via {@link StatName} annotation
     */
    private String getMethodName(Method method) {
        StatName statName = method.getAnnotation(StatName.class);
        return statName == null ? method.getName() : statName.value();
    }
}
