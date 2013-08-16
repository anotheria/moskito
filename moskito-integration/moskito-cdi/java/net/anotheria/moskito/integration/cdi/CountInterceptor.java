package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
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
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>, Leon Rosenberg
 */
@Interceptor
@Singleton
@Count
public class CountInterceptor extends BaseInterceptor<CounterStats> implements Serializable {
	/**
     * Serialization version unique identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger.
     */
    private Logger log = LoggerFactory.getLogger(CountInterceptor.class);


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
		OnDemandStatsProducer<CounterStats> onDemandProducer = getProducer(prd);

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


	@Override
	protected IOnDemandStatsFactory getFactory() {
		return CounterStatsFactory.DEFAULT_INSTANCE;
	}

	@Override
	protected String getCategory() {
		return "counter";
	}
}
