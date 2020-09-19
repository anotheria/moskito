package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.context.CurrentMeasurement;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.InvocationTargetException;

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

	/**
	 * Global configuration.
	 */
	protected MoskitoConfiguration moskitoConfiguration = MoskitoConfigurationHolder.getConfiguration();

    /*  */
    protected Object doProfiling(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

    	//check if kill switch is active, return if so.
    	if (moskitoConfiguration.getKillSwitch().disableMetricCollection())
			return pjp.proceed();

    	//check if this a synthetic method like a switch or lambda method.
    	if (((MethodSignature)pjp.getSignature()).getMethod().isSynthetic())
			return pjp.proceed();

        OnDemandStatsProducer<ServiceStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, false, FACTORY, true);
        String producerId = producer.getProducerId();
        String prevProducerId = lastProducerId.get();
        lastProducerId.set(producerId);
        //calculate cumulated stats (default stats).
		//we only do this if previous producer wasn't same as current -> meaning if we call a second method in the same producer we don't count it as new call.
        boolean calculateCumulatedStats = !producerId.equals(prevProducerId);

        //check if we are the first producer
		CurrentMeasurement cm = MoSKitoContext.get().notifyProducerEntry(producer);

        String methodName = getMethodStatName(pjp.getSignature());
        ServiceStats defaultStats = producer.getDefaultStats();
        ServiceStats methodStats = producer.getStats(methodName);

        final Object[] args = pjp.getArgs();
		if (calculateCumulatedStats) {
			defaultStats.addRequest();
		}
        if (methodStats != null) {
            methodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;

        boolean isLoggingEnabled = producer.isLoggingEnabled();

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
        if (currentTrace != null || tracePassingOfThisProducer || isLoggingEnabled || cm.isFirst()) {
            call = TracingUtil.buildCall(producerId, methodName, args, tracePassingOfThisProducer ? Tracers.getCallName(trace) : null);
        }
        if (currentTrace != null) {
            currentStep = currentTrace.startStep(call.toString(), producer, methodName);
        }

        if (cm.isFirst()){
        	cm.setCallDescription(call.toString());
		}

        long startTime = System.nanoTime();
        Object ret = null;
        try {
            ret = pjp.proceed();
            return ret;
        } catch (InvocationTargetException e) {
        	if (calculateCumulatedStats)
            	defaultStats.notifyError(e.getTargetException());
            if (methodStats != null) {
                methodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw e.getCause();
        } catch (Throwable t) {
        	if (calculateCumulatedStats)
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
            if (calculateCumulatedStats) {
                defaultStats.addExecutionTime(exTime);
            }
            if (methodStats != null) {
                methodStats.addExecutionTime(exTime);
            }
            lastProducerId.set(prevProducerId);
			if (calculateCumulatedStats) {
				defaultStats.notifyRequestFinished();
			}
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

            //TODO added this temporarly to 2.9.2 -> will be developed further in 2.10.0
            if (isLoggingEnabled){
				call.append(" = ").append(TracingUtil.parameter2string(ret));
				System.out.println(call.toString());
			}

            if (cm.isFirst()){
            	cm.notifyProducerFinished();
			}
        }
    }
}