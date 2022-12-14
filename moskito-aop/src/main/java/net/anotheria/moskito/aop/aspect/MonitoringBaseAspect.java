package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.aspect.specialtreater.HttpFilterHandler;
import net.anotheria.moskito.aop.aspect.specialtreater.SpecialCaseHandler;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
	 * TODO: maybe we can remove this and rely on the one in the context now.
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

    	MoSKitoContext moSKitoContext = MoSKitoContext.get();

        OnDemandStatsProducer<ServiceStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, false, FACTORY, true);
        String producerId = producer.getProducerId();
        String prevProducerId = lastProducerId.get();
        lastProducerId.set(producerId);

        //in most cases this is the same as lastProducerId. However lastProducerId is restored after the call
		//and previousProducerName is not.
		//we are using previous producer name for source monitoring.
		String previousProducerName = null;
		if (moSKitoContext.getLastProducer()!=null){
			previousProducerName = moSKitoContext.getLastProducer().getProducerId();
		}


		boolean sourceMonitoringActive = !producerId.equals(previousProducerName) && producer.sourceMonitoringEnabled();
        OnDemandStatsProducer<ServiceStats> sourceMonitoringProducer = null;
        if (sourceMonitoringActive){
        	sourceMonitoringProducer = getSourceMonitoringProducer(producerId, previousProducerName, aCategory, aSubsystem, FACTORY,  pjp.getSignature().getDeclaringType());
		}

        //calculate cumulated stats (default stats).
		//we only do this if previous producer wasn't same as current -> meaning if we call a second method in the same producer we don't count it as new call.
        boolean calculateCumulatedStats = !producerId.equals(prevProducerId);

        //check if we are the first producer
		CurrentMeasurement cm = moSKitoContext.notifyProducerEntry(producer);

        String methodName = getMethodStatName(pjp.getSignature());
        ServiceStats defaultStats = producer.getDefaultStats();
        ServiceStats methodStats  = producer.getStats(methodName);

        //source monitoring
        ServiceStats smDefaultStats = null, smMethodStats = null;
        if (sourceMonitoringActive){
        	smDefaultStats = sourceMonitoringProducer.getDefaultStats();
			smMethodStats  = sourceMonitoringProducer.getStats(methodName);
		}

        final Object[] args = pjp.getArgs();
		if (calculateCumulatedStats) {
			defaultStats.addRequest();
			if (sourceMonitoringActive) smDefaultStats.addRequest();
		}
        if (methodStats != null) {
            methodStats.addRequest();
			if (sourceMonitoringActive) smMethodStats.addRequest();
        }
        TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
        TraceStep currentStep = null;
        CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ? (CurrentlyTracedCall) aRunningTrace : null;

        boolean isLoggingEnabled = producer.isLoggingEnabled();

		TracerRepository tracerRepository = TracerRepository.getInstance();
        //only trace this producer if no tracers have been fired yet.
        boolean tracePassingOfThisProducer =
				!moSKitoContext.hasTracerFired() && tracerRepository.isTracingEnabledForProducer(producerId, methodName);
        Trace trace = null;
        boolean journeyStartedByMe = false;

        //we create trace here already, because we want to reserve a new trace id.
        if (tracePassingOfThisProducer){
            trace = new Trace();
            moSKitoContext.setTracerFired();
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
        	if (isSpecial(methodName)){
				call = getSpecialTreatmentForCall(methodName, pjp.getTarget(), args);
			}
        	if (call==null)
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
        	if (calculateCumulatedStats) {
				defaultStats.notifyError(e.getTargetException());
				if (sourceMonitoringActive) smDefaultStats.notifyError();
			}

            if (methodStats != null) {
                methodStats.notifyError();
                if (sourceMonitoringActive) smMethodStats.notifyError();
            }
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw e.getCause();
        } catch (Throwable t) {
        	if (calculateCumulatedStats) {
				defaultStats.notifyError(t);
				if (sourceMonitoringActive) smDefaultStats.notifyError();
			}
            if (methodStats != null) {
                methodStats.notifyError();
				if (sourceMonitoringActive) smMethodStats.notifyError();
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
				if (sourceMonitoringActive) smDefaultStats.addExecutionTime(exTime);
            }
            if (methodStats != null) {
                methodStats.addExecutionTime(exTime);
				if (sourceMonitoringActive) smMethodStats.addExecutionTime(exTime);

			}
            lastProducerId.set(prevProducerId);
            moSKitoContext.notifyProducerExit(producer);
			if (calculateCumulatedStats) {
				defaultStats.notifyRequestFinished();
				if (sourceMonitoringActive) smDefaultStats.notifyRequestFinished();
			}
            if (methodStats != null) {
                methodStats.notifyRequestFinished();
				if (sourceMonitoringActive) smMethodStats.notifyRequestFinished();
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
                    Journey myJourney = JourneyManagerFactory.getJourneyManager().getOrCreateJourney(
							Tracers.getJourneyNameForTracers(
									TracerRepository.getInstance().getEffectiveTracerId(producerId, methodName)));

                    myJourney.addCall((CurrentlyTracedCall) RunningTraceContainer.endTrace());
                    RunningTraceContainer.cleanup();
                }


                tracerRepository.addTracedExecution(producerId, methodName, trace);
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


	/**
	 * Checks if the method is notified as special case, used to add additional info to known, often used methods,
	 * which do not have publicly available arguments with toString methods, for example javax.servlet.Filter.
	 * @param methodName
	 * @return
	 */
	private boolean isSpecial(String methodName) {
    	return specialCases.containsKey(methodName);
	}

	private StringBuilder getSpecialTreatmentForCall(String methodName, Object target, Object[] args){
    	StringBuilder ret = new StringBuilder();
    	SpecialCase sc = specialCases.get(methodName);
    	if (sc==null)
    		throw new IllegalStateException("Special case is not found even it must have been found before for method '"+methodName+"'");

		Class clazz = null;
    	try{
    		clazz = Class.forName(sc.className, false, getClass().getClassLoader());
		}catch(ClassNotFoundException e){
    		e.printStackTrace();
    		return null;
		}

    	if (clazz.isInstance(target)){
    		//special treatment for httpfilter.
    		return sc.treater.getCallDescription(clazz, methodName, target, args);
		}
    	return null;
	}

	/**
	 * Map with special case definition.
	 */
	private static final ConcurrentMap<String, SpecialCase> specialCases = new ConcurrentHashMap<>();

	static{
    	specialCases.put("doFilter", new SpecialCase("doFilter", "javax.servlet.Filter", new HttpFilterHandler()));
	}

	/**
	 * Definition of a special case. We probably should cache the clazz at some point to reduce load, but since the clazz is only loaded if the
	 * method name matches, we shouldn't be in much trouble for now.
	 */
	static class SpecialCase{
		/**
		 * Name of the method. i.e. doFilter in ServletFilter.
		 */
    	private final String methodName;
		/**
		 * Full name of the class to be matched after method name.
		 */
		private final String className;
		/**
		 * The special handler for this type of call.
		 */
    	private final SpecialCaseHandler treater;

    	SpecialCase(String aMethodName, String aClassName, SpecialCaseHandler aTreater){
    		methodName = aMethodName;
    		className = aClassName;
    		treater = aTreater;
		}

	}

}