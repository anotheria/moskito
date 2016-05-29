package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.integration.cdi.accumulation.Accumulate;
import net.anotheria.moskito.integration.cdi.accumulation.Accumulates;
import net.anotheria.moskito.core.util.annotation.AnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.InvocationContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
     * @param producerClass producer class
     * @param producerId producer id
     * @param category category
     * @param subsystem subsystem
     * @param tracingSupported is tracing supported
     * @return {@link OnDemandStatsProducer}
     */
    protected final OnDemandStatsProducer getProducer(Class producerClass, String producerId, String category, String subsystem, boolean tracingSupported){
        OnDemandStatsProducer<T> producer = (OnDemandStatsProducer<T>) ProducerRegistryFactory.getProducerRegistryInstance().getProducer(producerId);

        if (producer == null) {
            producer = new OnDemandStatsProducer<T>(producerId, category, subsystem, getStatsFactory());
            producer.setTracingSupported(tracingSupported);
            ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

            //check for annotations
            createClassLevelAccumulators(producer, producerClass);

            for (Method method : producerClass.getMethods()){
                createMethodLevelAccumulators(producer, method);
            }
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

    /**
     * Create method level accumulators.
     *
     * @param producer {@link OnDemandStatsProducer}
     * @param method annotated method
     */
    private void createMethodLevelAccumulators(OnDemandStatsProducer<T> producer, Method method) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolderMethods = (Accumulates) method.getAnnotation(Accumulates.class);
        if (accAnnotationHolderMethods != null && accAnnotationHolderMethods.value() != null) {
            Accumulate[] accAnnotations  = accAnnotationHolderMethods.value();
            for (Accumulate accAnnotation : accAnnotations) {
                createAccumulator(
                        producer.getProducerId(),
                        accAnnotation,
                        formAccumulatorNameForMethod(producer, accAnnotation, method),
                        method.getName()
                );
            }
        }

        //If there is no @Accumulates annotation but @Accumulate is present
        createAccumulator(
                producer.getProducerId(),
                method.getAnnotation(Accumulate.class),
                formAccumulatorNameForMethod(producer, method.getAnnotation(Accumulate.class), method),
                method.getName()
        );
    }

    /**
     * Create accumulators for class.
     *
     * @param producer {@link OnDemandStatsProducer}
     * @param producerClass producer class
     */
    private void createClassLevelAccumulators(OnDemandStatsProducer<T> producer, Class producerClass) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolder = AnnotationUtils.findAnnotation(producerClass, Accumulates.class);//(Accumulates) producerClass.getAnnotation(Accumulates.class);
        if (accAnnotationHolder != null && accAnnotationHolder.value() != null) {
            Accumulate[] accAnnotations  = accAnnotationHolder.value();
            for (Accumulate accAnnotation : accAnnotations) {
                createAccumulator(
                        producer.getProducerId(),
                        accAnnotation,
                        formAccumulatorNameForClass(producer, accAnnotation),
                        "cumulated");
            }
        }

        //If there is no @Accumulates annotation but @Accumulate is present
        Accumulate annotation = AnnotationUtils.findAnnotation(producerClass, Accumulate.class);//producerClass.getAnnotation(Accumulate.class);
        createAccumulator(
                producer.getProducerId(),
                annotation,
                formAccumulatorNameForClass(producer, annotation),
                "cumulated"
        );
    }

    private String formAccumulatorNameForMethod(final OnDemandStatsProducer<T> producer, final Accumulate annotation, final Method m) {
        if (producer != null && annotation != null && m != null)
            return producer.getProducerId()+ '.' +m.getName()+ '.' +annotation.valueName()+ '.' +annotation.intervalName();
        return "";
    }

    private String formAccumulatorNameForClass(final OnDemandStatsProducer<T> producer, final Accumulate annotation) {
        if (producer != null && annotation != null)
            return producer.getProducerId()+ '.' +annotation.valueName()+ '.' +annotation.intervalName();
        return "";
    }

    /**
     * Create accumulator and register it.
     *
     * @param producerId id of the producer
     * @param annotation Accumulate annotation
     * @param accName Accumulator name
     * @param statsName Statistics name
     */
    private void createAccumulator(String producerId, Accumulate annotation, String accName, String statsName) {
        if (annotation != null && producerId != null && !producerId.isEmpty() &&
                accName!=null && !accName.isEmpty() && statsName != null && !statsName.isEmpty()){

            AccumulatorDefinition definition = new AccumulatorDefinition();
            if (annotation.name() != null && annotation.name().length() >0) {
                definition.setName(annotation.name());
            }else{
                definition.setName(accName);
            }
            definition.setIntervalName(annotation.intervalName());
            definition.setProducerName(producerId);
            definition.setStatName(statsName);
            definition.setValueName(annotation.valueName());
            definition.setTimeUnit(annotation.timeUnit());
            AccumulatorRepository.getInstance().createAccumulator(definition);
        }
    }
}
