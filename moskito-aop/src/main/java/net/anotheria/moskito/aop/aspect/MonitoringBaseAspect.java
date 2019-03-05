package net.anotheria.moskito.aop.aspect;

import java.lang.reflect.InvocationTargetException;

import org.aspectj.lang.ProceedingJoinPoint;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;

/**
 * @author Roman Stetsiuk.
 */
public class MonitoringBaseAspect extends AbstractMoskitoAspect<ServiceStats>{

    /**
     * Factory constant is needed to prevent continuous reinstantiation of ServiceStatsFactory objects.
     */
    protected static final ServiceStatsFactory FACTORY = new ServiceStatsFactory();

    /**
     * Remember previous producer to avoid time cumulation of recursive calls.
     */
    protected static final ThreadLocal<String> lastProducerId = new ThreadLocal<>();

    /*  */
    protected Object doProfiling(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		// check if moskito pointcuts are enabled
		if (!config.isMoskitoPointcutsEnabled()) {
			return pjp.proceed();
		}

        OnDemandStatsProducer<ServiceStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, false, FACTORY, true);
        String producerId = producer.getProducerId();
        String prevProducerId = lastProducerId.get();
        lastProducerId.set(producerId);

        String methodName = getMethodStatName(pjp.getSignature());
        ServiceStats defaultStats = producer.getDefaultStats();
        ServiceStats methodStats = producer.getStats(methodName);

        final Object[] args = pjp.getArgs();
        defaultStats.addRequest();
        if (methodStats != null) {
            methodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;

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
            call = TracingUtil.buildCall(producerId, methodName, args, tracePassingOfThisProducer ? Tracers.getCallName(trace) : null);
        }
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(call.toString(), producer, methodName);
        }
        long startTime = System.nanoTime();
        
        Object ret = null;
        try {
            ret = pjp.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            defaultStats.notifyError(e.getTargetException());
            if (methodStats != null) {
                methodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw e.getCause();
        } catch (Throwable t) {
            defaultStats.notifyError(t);
            if (methodStats != null) {
                methodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            if (tracePassingOfThisProducer) {
                call.append(" ERR ").append(t.getMessage());
            }
            throw t;
        } finally {
            long exTime = System.nanoTime() - startTime;
            if (!producerId.equals(prevProducerId)) {
                defaultStats.addExecutionTime(exTime);
            }
            if (methodStats != null) {
                methodStats.addExecutionTime(exTime);
            }
            lastProducerId.set(prevProducerId);
            defaultStats.notifyRequestFinished();
            if (methodStats != null) {
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
                    myJourney.addUseCase((CurrentlyTracedCall) RunningTraceContainer.endTrace());
                    RunningTraceContainer.cleanup();
                }


                tracerRepository.addTracedExecution(producerId, trace);
            }
        }
    }
}