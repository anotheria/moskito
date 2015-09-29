package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
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
    private ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<S>>();

    /**
     * Returns the producer for the given pjp and producerId. Registers the producer in the registry if it's not already registered.
     * @param pjp the pjp is used to obtain the producer id automatically if it's not submitted.
     * @param aProducerId submitted producer id, used if configured in aop.
     * @param aCategory submitted category.
     * @param aSubsystem submitted subsystem.
     * @param withMethod if true the name of the method will be part of the automatically generated producer id.
     * @return
     */
    protected  OnDemandStatsProducer<S> getProducer(ProceedingJoinPoint pjp, String aProducerId, String aCategory, String aSubsystem, boolean withMethod, IOnDemandStatsFactory<S> factory, boolean tracingSupported){
        String producerId = null;
        if (aProducerId!=null && aProducerId.length()>0){
            producerId = aProducerId;
        }else{
            producerId = pjp.getSignature().getDeclaringTypeName();
            try{
                producerId = producerId.substring(producerId.lastIndexOf('.')+1);
            }catch(RuntimeException ignored){/* ignored */}
        }

        if (withMethod)
            producerId += "."+pjp.getSignature().getName();

        OnDemandStatsProducer<S> producer = producers.get(producerId);
        if (producer==null){

            producer = new OnDemandStatsProducer(producerId, getCategory(aCategory), getSubsystem(aSubsystem), factory);
            producer.setTracingSupported(tracingSupported);
            OnDemandStatsProducer<S> p = producers.putIfAbsent(producerId, producer);
            if (p==null){
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

                //now check for annotations.
                Class producerClass = pjp.getSignature().getDeclaringType();

                createClassLevelAccumulators(producer, producerClass);

                Method[] methods = producerClass.getMethods();
                for (Method m : methods){
                    createMethodLevelAccumulators(producer, producerClass, m);
                }

            }else{
                producer = p;
            }
        }
        return producer;

    }

    /**
     * Create method level accumulators
     * @param producer
     * @param producerClass
     * @param method annotated method
     */
    private void createMethodLevelAccumulators(OnDemandStatsProducer<S> producer, Class producerClass, Method method) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolderMethods = (Accumulates) producerClass.getAnnotation(Accumulates.class);
        if (accAnnotationHolderMethods != null && accAnnotationHolderMethods.value() != null) {
            Accumulate[] accAnnotations  = accAnnotationHolderMethods.value();
            for (Accumulate accAnnotation : accAnnotations) {
                createAccumulator(
                        producer.getProducerId(),
                        method.getAnnotation(Accumulate.class),
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
     * Create accumulators for class
     * @param producer
     * @param producerClass
     */
    private void createClassLevelAccumulators(OnDemandStatsProducer<S> producer, Class producerClass) {
        //several @Accumulators in accumulators holder
        Accumulates accAnnotationHolder = (Accumulates) producerClass.getAnnotation(Accumulates.class);
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
        Accumulate annotation = (Accumulate) producerClass.getAnnotation(Accumulate.class);
        createAccumulator(
                producer.getProducerId(),
                annotation,
                formAccumulatorNameForClass(producer, annotation),
                "cumulated"
        );
    }

    private String formAccumulatorNameForMethod(final OnDemandStatsProducer<S> producer, final Accumulate annotation, final Method m) {
        if (producer != null && annotation != null && m != null)
            return producer.getProducerId()+"."+m.getName()+"."+annotation.valueName()+"."+annotation.intervalName();
        return "";
    }

    private String formAccumulatorNameForClass(final OnDemandStatsProducer<S> producer, final Accumulate annotation) {
        if (producer != null && annotation != null)
            return producer.getProducerId()+"."+annotation.valueName()+"."+annotation.intervalName();
        return "";
    }

    /**
     * Create accumulator and register it
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


    /**
     * Returns the category to use for the producer registration.
     * @param proposal
     * @return
     */
    public String getCategory(String proposal) {
        return proposal==null || proposal.length()==0 ? "annotated" : proposal;
    }

    /**
     * Returns the subsystem for registration.
     * @param proposal
     * @return
     */
    public String getSubsystem(String proposal) {
        return proposal==null || proposal.length()==0 ? "default" : proposal;
    }

    public void reset() {
        producers.clear();
    }

}