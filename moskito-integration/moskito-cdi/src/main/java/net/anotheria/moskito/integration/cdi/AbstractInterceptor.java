package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * Base class for all interceptors.
 * @param <T> stats type
 *
 * @author Alex Osadchy
 */
public abstract class AbstractInterceptor<T extends IStats> {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInterceptor.class);

    /**
     * Returns class of the stats.
     *
     * @return class of the stats
     */
    protected abstract Class<T> getStatsClass();

    /**
     * Returns stats factory.
     *
     * @return {@link IOnDemandStatsFactory} for stats
     */
    protected abstract IOnDemandStatsFactory<T> getStatsFactory();

    /**
     * Returns {@link OnDemandStatsProducer}.
     *
     * @param producerId producer id
     * @param category category
     * @param subsystem subsystem
     * @return {@link OnDemandStatsProducer}
     */
    protected final OnDemandStatsProducer getProducer(String producerId, String category, String subsystem){
        IStatsProducer producer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(producerId);

        if (producer == null) {
            producer = new OnDemandStatsProducer<T>(producerId, category, subsystem, getStatsFactory());
            ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
        }

        try {
            return  (OnDemandStatsProducer) ProducerRegistryFactory.getProducerRegistryInstance().getProducer(producerId);
        } catch (ClassCastException e) {
            LOGGER.error("getProducer(): Unexpected producer type", e);
            return null;
        }
    }

    /**
     * Returns default stats for producer.
     *
     * @param producer {@link OnDemandStatsProducer}
     * @return default stats for producer
     */
    protected final T getDefaultStats(OnDemandStatsProducer producer) {
        try {
            return getStatsClass().cast(producer.getDefaultStats());
        } catch (ClassCastException e) {
            LOGGER.error("getDefaultStats(): Unexpected stats type", e);
            return null;
        }
    }

    /**
     * Returns stats for producer.
     *
     * @param producer {@link OnDemandStatsProducer}
     * @param name stats name
     * @return stats for producer
     */
    protected final T getStats(OnDemandStatsProducer producer, String name) {
        try {
            return getStatsClass().cast(producer.getStats(name));
        } catch (ClassCastException e) {
            LOGGER.error("getStats(): Unexpected stats type", e);
        } catch (OnDemandStatsProducerException e) {
            LOGGER.error("getStats(): Failed to get stats for name=" + name, e);
        }

        return null;
    }

    /**
     * Finds given annotation from {@link InvocationContext} (actually from method or class).
     *
     * @param context {@link InvocationContext}
     * @param clazz annotation class
     * @param <T> type of the annotation
     * @return annotation instance
     */
    protected static <T extends Annotation> T getAnnotationFromContext(InvocationContext context, Class<T> clazz) {
        T result = context.getMethod().getAnnotation(clazz);
        return result != null ? result : context.getTarget().getClass().getAnnotation(clazz);
    }

    /**
     * Proceed further.
     *
     * @param ctx {@link InvocationContext}
     * @return method return value
     * @throws Throwable if intercepted method throws exception
     */
    protected static Object proceed(InvocationContext ctx) throws Throwable {
        try {
            return ctx.proceed();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
