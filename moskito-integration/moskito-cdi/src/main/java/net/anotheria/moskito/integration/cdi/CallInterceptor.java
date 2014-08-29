package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Generic call interceptor.
 *
 * @author Vitaliy Zhovtiuk, Leon Rosenberg
 */
@Interceptor
@Singleton
@Monitor()
public class CallInterceptor extends BaseInterceptor<ServiceStats> implements Serializable {
    /**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = -2722113871558244179L;

    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(CallInterceptor.class);


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

		ProducerRuntimeDefinition prd = extractProducerDefinition(ctx);
		OnDemandStatsProducer<ServiceStats> onDemandProducer = getProducer(prd);

        ServiceStats defaultStats = onDemandProducer.getDefaultStats();
        ServiceStats methodStats = null;
        String caseName = extractCaseName(ctx);
        try {
            if (caseName != null) {
                methodStats = onDemandProducer.getStats(caseName);
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
            StringBuilder call = new StringBuilder(getClassName(ctx)).append('.').append(method.getName()).append("(");
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

    public String getCategory() {
        return "service";
    }

	@Override
	protected IOnDemandStatsFactory getFactory() {
		return ServiceStatsFactory.DEFAULT_INSTANCE;
	}

	private static String getClassName(InvocationContext ctx) {
		return ctx.getMethod().getDeclaringClass().getSimpleName();
	}
}
