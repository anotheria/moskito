package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;

/**
 * Aspect used to intercept @MonitorClass annotated classes method calls.
 *
 * @author Vitaliy Zhovtiuk
 * @author lrosenberg
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

    @Around(value = "execution(* *.*(..)) && @within(monitor) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
    public Object doProfilingClass(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
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
		Trace trace = null;
		boolean journeyStartedByMe = false;

		//we create trace here already, because we want to reserve a new trace id.
		if (tracePassingOfThisProducer){
			trace = new Trace();
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

            call = new StringBuilder();
			if (tracePassingOfThisProducer)
				call.append(Tracers.getCallName(trace)).append(' ');
			call.append(producerId).append('.').append(method).append("(");
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
