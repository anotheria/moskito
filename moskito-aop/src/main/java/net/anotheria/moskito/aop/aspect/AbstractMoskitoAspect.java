package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.annotation.AnnotationUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The basic aspect class.
 *
 * @author lrosenberg
 * @since 19.11.12 00:00
 */
public class AbstractMoskitoAspect<S extends IStats> {

    /**
     * Map with created producers.
     */
    private ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<>();

    /**
     * Returns the producer for the given pjp and producerId. Registers the producer in the registry if it's not already registered.
     *
     * @param pjp the pjp is used to obtain the producer id automatically if it's not submitted.
     * @param aProducerId submitted producer id, used if configured in aop.
     * @param aCategory submitted category.
     * @param aSubsystem submitted subsystem.
     * @param withMethod if true the name of the method will be part of the automatically generated producer id.
     * @param factory OnDemandStatsProducer factory
     * @param tracingSupported is tracing supported
     * @return
     */
    protected  OnDemandStatsProducer<S> getProducer(ProceedingJoinPoint pjp, String aProducerId, String aCategory, String aSubsystem, boolean withMethod, IOnDemandStatsFactory<S> factory, boolean tracingSupported){
        String producerId;
        if (aProducerId!=null && aProducerId.length()>0){
            producerId = aProducerId;
        }else{
            producerId = pjp.getSignature().getDeclaringTypeName();
            try{
                producerId = MoskitoUtils.producerName(producerId);
            }catch(RuntimeException ignored){/* ignored */}
        }

        if (withMethod)
            producerId += '.' +pjp.getSignature().getName();

        OnDemandStatsProducer<S> producer = producers.get(producerId);
        if (producer==null){

            producer = new OnDemandStatsProducer<>(producerId, getCategory(aCategory), getSubsystem(aSubsystem), factory);
            producer.setTracingSupported(tracingSupported);
            OnDemandStatsProducer<S> p = producers.putIfAbsent(producerId, producer);
            if (p==null){
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

                //now check for annotations.
                final Class producerClass = pjp.getSignature().getDeclaringType();

                createClassLevelAccumulators(producer, producerClass);
                for (Method method : producerClass.getMethods()){
                    createMethodLevelAccumulators(producer, method);
                }

            }else{
                producer = p;
            }
        }
        return producer;

    }

    /**
     * Create method level accumulators.
     *
     * @param producer {@link OnDemandStatsProducer}
     * @param method annotated method
     */
    private void createMethodLevelAccumulators(OnDemandStatsProducer<S> producer, Method method) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolderMethods = (Accumulates) method.getAnnotation(Accumulates.class);
        if (accAnnotationHolderMethods != null) {
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
    private void createClassLevelAccumulators(OnDemandStatsProducer<S> producer, Class producerClass) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolder = AnnotationUtils.findAnnotation(producerClass, Accumulates.class);//(Accumulates) producerClass.getAnnotation(Accumulates.class);
        if (accAnnotationHolder != null) {
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

    private String formAccumulatorNameForMethod(final OnDemandStatsProducer<S> producer, final Accumulate annotation, final Method m) {
        if (producer != null && annotation != null && m != null)
            return producer.getProducerId()+ '.' +m.getName()+ '.' +annotation.valueName()+ '.' +annotation.intervalName();
        return "";
    }

    private String formAccumulatorNameForClass(final OnDemandStatsProducer<S> producer, final Accumulate annotation) {
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
            if (annotation.name().length() > 0) {
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


    /**
     * Returns the category to use for the producer registration.
     *
     * @param proposal proposal
     * @return category
     */
    public String getCategory(String proposal) {
        return proposal==null || proposal.length()==0 ? "annotated" : proposal;
    }

    /**
     * Returns the subsystem for registration.
     *
     * @param proposal proposal
     * @return subsystem
     */
    public String getSubsystem(String proposal) {
        return proposal==null || proposal.length()==0 ? "default" : proposal;
    }

    public void reset() {
        producers.clear();
    }

}