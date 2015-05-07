package net.anotheria.moskito.integration.cdi.count;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.integration.cdi.ProducerFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link Count} interceptor.
 *
 * @author Alex Osadchy
 * @author Leon Rosenberg
 */
@Count
@Interceptor
public class CountInterceptor {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CountInterceptor.class);

    /**
     * Around method invoke.
     *
     * @param ctx context
     * @return method result
     * @throws Throwable any exception
     */
    @AroundInvoke
    public Object aroundInvoke(InvocationContext ctx) throws Throwable {
        final Method method = ctx.getMethod();

        if (method != null) {
            final Count annotation = method.getDeclaringClass().getAnnotation(Count.class);
            final CountParameter parameter = method.getAnnotation(CountParameter.class);

            final String producerId = annotation.producerId().isEmpty() ? method.getDeclaringClass().getSimpleName() : annotation.producerId();
            final String caseName = (parameter != null && !parameter.value().isEmpty()) ? parameter.value() : method.getName();
            final OnDemandStatsProducer<CounterStats> onDemandProducer = ProducerFinder.getProducer(producerId, annotation.category(), annotation.subsystem(), CounterStatsFactory.DEFAULT_INSTANCE);

            onDemandProducer.getDefaultStats().inc();

            try {
                onDemandProducer.getStats(caseName).inc();
            } catch (OnDemandStatsProducerException e) {
                LOGGER.info("Couldn't get stats for case : " + caseName + ", probably limit reached");
            }
        }

        try {
            return ctx.proceed();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
