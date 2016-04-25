package net.anotheria.moskito.integration.cdi.count;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.integration.cdi.AbstractInterceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * {@link Count} interceptor.
 *
 * @author Alex Osadchy
 * @author Leon Rosenberg
 */
@Count
@Interceptor
public class CountInterceptor extends AbstractInterceptor<CounterStats> implements Serializable {

    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected final Class<CounterStats> getStatsClass() {
        return CounterStats.class;
    }

    @Override
    protected final IOnDemandStatsFactory<CounterStats> getStatsFactory() {
        return CounterStatsFactory.DEFAULT_INSTANCE;
    }

    /**
     * Around method invoke.
     *
     * @param ctx context
     * @return method result
     * @throws Throwable any exception
     */
    @AroundInvoke
    @AroundTimeout
    public Object aroundInvoke(InvocationContext ctx) throws Throwable {
        final Method method = ctx.getMethod();

        final Count annotation = getAnnotationFromContext(ctx, Count.class);
        final CountParameter parameter = method.getAnnotation(CountParameter.class);

        final String producerId = annotation.producerId().isEmpty() ? method.getDeclaringClass().getSimpleName() : annotation.producerId();
        final String caseName = (parameter != null && !parameter.value().isEmpty()) ? parameter.value() : method.getName();
        final OnDemandStatsProducer onDemandProducer = getProducer(producerId, annotation.category(), annotation.subsystem(), false);

        if (onDemandProducer != null) {
            final CounterStats defaultStats = getDefaultStats(onDemandProducer);
            final CounterStats stats = getStats(onDemandProducer, caseName);

            if (defaultStats != null)
                defaultStats.inc();
            if (stats != null)
                stats.inc();
        }

        return proceed(ctx);
    }

}
