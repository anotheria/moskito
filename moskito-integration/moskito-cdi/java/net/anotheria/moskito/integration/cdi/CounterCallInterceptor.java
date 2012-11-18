package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.apache.log4j.Logger;

import javax.inject.Singleton;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static net.anotheria.moskito.integration.cdi.CounterCallInterceptor.BUSINESS_CATEGORY;

/**
 * Counter call interceptor.
 * TODO in progress. Not ready for release.
 *
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Interceptor
@Singleton
@Monitor(BUSINESS_CATEGORY)
public class CounterCallInterceptor implements Serializable {

    public static final String BUSINESS_CATEGORY = "business";

    /**
     * Logger.
     */
    private Logger log = Logger.getLogger(CounterCallInterceptor.class);
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
            onDemandProducer = new OnDemandStatsProducer(producerId, getCategory(), getSubsystem(), new CounterStatsFactory());
            OnDemandStatsProducer p = producers.putIfAbsent(producerId, onDemandProducer);
            if (p == null) {
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
            } else {
                onDemandProducer = p;
            }
        }


        OnDemandStatsProducer<CounterStats> producer = new OnDemandStatsProducer<CounterStats>("business", "counter", "business", new CounterStatsFactory());
        //TODO test
        producer.getStats("registration").inc();
        producer.getStats("login").inc();

        CounterStats defaultStats = (CounterStats) onDemandProducer.getDefaultStats();

        String caseName = extractCaseName(ctx);
        log.error("TODO casename " + caseName);

        // TODO not checked yet.
        final Object[] args = ctx.getParameters();
        final Method method = ctx.getMethod();
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
            //System.out.println("exception of class: "+e.getCause()+" is thrown");
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw e.getCause();
        } catch (Throwable t) {
            if (currentStep != null) {
                currentStep.setAborted();
            }
            throw t;
        } finally {
            long exTime = System.nanoTime() - startTime;
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
        return getClassName(ctx);
    }

    public String getCategory() {
        return BUSINESS_CATEGORY;
    }

    public String getSubsystem() {
        return "counter";
    }


    /**
     * Get class name by invocation context.
     *
     * @param ctx invocation context
     * @return string class name
     */
    private static String getClassName(InvocationContext ctx) {
        return ctx.getMethod().getDeclaringClass().getName();
    }

}
