package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
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

/**
 * Generic call interceptor.
 *
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>, Leon Rosenberg
 */
@Interceptor
@Singleton
@Count
public class CountInterceptor implements Serializable {
	/**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = -2722113871558244179L;

    /**
     * Logger.
     */
    private Logger log = Logger.getLogger(CountInterceptor.class);
    /**
     * The internal producer instances.
     */
    private ConcurrentMap<String, OnDemandStatsProducer<CounterStats>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<CounterStats>>();


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
        OnDemandStatsProducer<CounterStats> onDemandProducer = producers.get(producerId);
        if (onDemandProducer == null) {
            onDemandProducer = new OnDemandStatsProducer(producerId, "counter", "default", new CounterStatsFactory());
            OnDemandStatsProducer p = producers.putIfAbsent(producerId, onDemandProducer);
            if (p == null) {
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
            } else {
                onDemandProducer = p;
            }
        }

		CounterStats defaultStats = onDemandProducer.getDefaultStats();
		CounterStats methodStats = null;
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
        defaultStats.inc();
        if (methodStats != null) {
            methodStats.inc();
        }

        Object ret = null;
        try {
            ret = ctx.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            throw e.getCause();
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
