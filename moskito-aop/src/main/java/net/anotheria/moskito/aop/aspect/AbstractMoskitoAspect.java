package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.logging.DefaultStatsLogger;
import net.anotheria.moskito.core.logging.IntervalStatsLogger;
import net.anotheria.moskito.core.logging.SLF4JLogOutput;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.util.annotation.AnnotationUtils;
import net.anotheria.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Possible producer id className/method separator.
	 */
	private static final char DOT = '.';

	/**
	 * Map with created producers.
	 */
	private final ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<>();
	/**
	 * Common {@link MoskitoAspectConfiguration}.
	 */
	private final MoskitoAspectConfiguration config = MoskitoAspectConfiguration.getInstance();

	/**
	 * Logging UTIL.
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * See {@link #getProducer(ProceedingJoinPoint, String, String, String, boolean, IOnDemandStatsFactory, boolean, boolean)} - with attachLoggers -
	 * providen by {@link MoskitoAspectConfiguration#isAttachDefaultStatLoggers()}.
	 *
	 * @param pjp
	 * 		the pjp is used to obtain the producer id automatically if it's not submitted.
	 * @param aProducerId
	 * 		submitted producer id, used if configured in aop.
	 * @param aCategory
	 * 		submitted category.
	 * @param aSubsystem
	 * 		submitted subsystem.
	 * @param withMethod
	 * 		if true the name of the method will be part of the automatically generated producer id.
	 * @param factory
	 * 		OnDemandStatsProducer factory
	 * @param tracingSupported
	 * 		is tracing supported
	 * @return {@link OnDemandStatsProducer<S>}
	 */
	protected OnDemandStatsProducer<S> getProducer(final ProceedingJoinPoint pjp, final String aProducerId, final String aCategory, final String aSubsystem, final boolean withMethod,
												   final IOnDemandStatsFactory<S> factory, final boolean tracingSupported) {
		return getProducer(pjp, aProducerId, aCategory, aSubsystem, withMethod, factory, tracingSupported, config.isAttachDefaultStatLoggers());
	}

	/**
	 * Returns the producer for the given pjp and producerId. Registers the producer in the registry if it's not already registered.
	 *
	 * @param pjp
	 * 		the pjp is used to obtain the producer id automatically if it's not submitted.
	 * @param aProducerId
	 * 		submitted producer id, used if configured in aop.
	 * @param aCategory
	 * 		submitted category.
	 * @param aSubsystem
	 * 		submitted subsystem.
	 * @param withMethod
	 * 		if true the name of the method will be part of the automatically generated producer id.
	 * @param factory
	 * 		OnDemandStatsProducer factory
	 * @param tracingSupported
	 * 		is tracing supported
	 * @param attachDefaultStatsLoggers
	 * 		allow to attach default loggers
	 * @return {@link OnDemandStatsProducer<S>}
	 */
	protected OnDemandStatsProducer<S> getProducer(final ProceedingJoinPoint pjp, final String aProducerId, final String aCategory, final String aSubsystem, final boolean withMethod,
												   final IOnDemandStatsFactory<S> factory, final boolean tracingSupported, final boolean attachDefaultStatsLoggers) {
		final String producerId = getProducerId(pjp, aProducerId, withMethod);

		OnDemandStatsProducer<S> producer = producers.get(producerId);
		if (producer != null)
			return producer;

		producer = new OnDemandStatsProducer<>(producerId, getCategory(aCategory), getSubsystem(aSubsystem), factory);
		producer.setTracingSupported(tracingSupported);
		final OnDemandStatsProducer<S> p = producers.putIfAbsent(producerId, producer);
		if (p != null)
			return p;

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
		//now check for annotations.
		final Class producerClass = pjp.getSignature().getDeclaringType();

		createClassLevelAccumulators(producer, producerClass);
		for (Method method : producerClass.getMethods())
			createMethodLevelAccumulators(producer, method);
		if (attachDefaultStatsLoggers)
			attachLoggers(producer, factory);

		return producer;

	}

	/**
	 * Attach moskito loggers.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param factory
	 * 		{@link IOnDemandStatsFactory}
	 */
	private void attachLoggers(final OnDemandStatsProducer<S> producer, final IOnDemandStatsFactory<S> factory) {
		//default moskito loggers initialization!
		new DefaultStatsLogger(producer, new SLF4JLogOutput(LoggerFactory.getLogger(config.getDefaultMoskitoLoggerName())));
		//for further improvement... mb.
		if (!(factory instanceof AbstractStatsFactory))
			return;
		AbstractStatsFactory<?> common = AbstractStatsFactory.class.cast(factory);

		for (final Interval interval : common.getIntervals()) {
			//only in case if found! (configured)
			final String loggerName = config.getMoskitoLoggerName(interval.getName());
			if (!StringUtils.isEmpty(loggerName))
				new IntervalStatsLogger(producer, interval, new SLF4JLogOutput(LoggerFactory.getLogger(loggerName)));
		}
	}


	/**
	 * Fetch producer id - for further usage.
	 *
	 * @param pjp
	 * 		{@link ProceedingJoinPoint}
	 * @param aPId
	 * 		provided producer id
	 * @param withMethod
	 * 		{@code true} in case if methodName should be used in produce id, {@code false} otheriwse
	 * @return producer identifier
	 */
	private String getProducerId(final ProceedingJoinPoint pjp, final String aPId, final boolean withMethod) {
		if (!StringUtils.isEmpty(aPId))
			return aPId;

		String res = pjp.getSignature().getDeclaringTypeName();
		try {
			res = MoskitoUtils.producerName(res);
		} catch (final RuntimeException e) {
			if (logger.isTraceEnabled())
				logger.trace(e.getMessage(), e);
		}
		if (withMethod)
			res += DOT + pjp.getSignature().getName();
		return res;
	}

	/**
	 * Create method level accumulators.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param method
	 * 		annotated method
	 */
	private void createMethodLevelAccumulators(final OnDemandStatsProducer<S> producer, final Method method) {
		//several @Accumulators in accumulators holder
		final Accumulates accAnnotationHolderMethods = AnnotationUtils.findAnnotation(method,Accumulates.class);
		if (accAnnotationHolderMethods != null)
			for (Accumulate accAnnotation : accAnnotationHolderMethods.value())
				createAccumulator(
						producer.getProducerId(),
						accAnnotation,
						formAccumulatorNameForMethod(producer, accAnnotation, method),
						method.getName()
				);



		//If there is no @Accumulates annotation but @Accumulate is present
		final Accumulate annotation = AnnotationUtils.findAnnotation(method, Accumulate.class);
		createAccumulator(
				producer.getProducerId(),
				annotation,
				formAccumulatorNameForMethod(producer, annotation, method),
				method.getName()
		);
	}

	/**
	 * Create accumulators for class.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param producerClass
	 * 		producer class
	 */
	private void createClassLevelAccumulators(final OnDemandStatsProducer<S> producer, final Class producerClass) {
		//several @Accumulators in accumulators holder
		final Accumulates accAnnotationHolder = AnnotationUtils.findAnnotation(producerClass, Accumulates.class);
		if (accAnnotationHolder != null)
			for (final Accumulate accAnnotation : accAnnotationHolder.value())
				createAccumulator(
						producer.getProducerId(),
						accAnnotation,
						formAccumulatorNameForClass(producer, accAnnotation),
						"cumulated");



		//If there is no @Accumulates annotation but @Accumulate is present
		final Accumulate annotation = AnnotationUtils.findAnnotation(producerClass, Accumulate.class);
		createAccumulator(
				producer.getProducerId(),
				annotation,
				formAccumulatorNameForClass(producer, annotation),
				"cumulated"
		);
	}

	/**
	 * Creates accumulator name for method.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param annotation
	 * 		{@link Accumulate}
	 * @param m
	 * 		{@link Method}
	 * @return accumulator name
	 */
	private String formAccumulatorNameForMethod(final OnDemandStatsProducer<S> producer, final Accumulate annotation, final Method m) {
		if (producer == null || annotation == null || m == null)
			return "";
		return producer.getProducerId() + DOT + m.getName() + DOT + annotation.valueName() + DOT + annotation.intervalName();
	}

	/**
	 * Creates accumulator name for class.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param annotation
	 * 		{@link Accumulate}
	 * @return accumulator name
	 */
	private String formAccumulatorNameForClass(final OnDemandStatsProducer<S> producer, final Accumulate annotation) {
		if (producer == null || annotation == null)
			return "";
		return producer.getProducerId() + DOT + annotation.valueName() + DOT + annotation.intervalName();
	}

	/**
	 * Create accumulator and register it.
	 *
	 * @param producerId
	 * 		id of the producer
	 * @param annotation
	 * 		Accumulate annotation
	 * @param accName
	 * 		Accumulator name
	 * @param statsName
	 * 		Statistics name
	 */
	private void createAccumulator(final String producerId, final Accumulate annotation, final String accName, final String statsName) {
		if (annotation == null || StringUtils.isEmpty(producerId) || StringUtils.isEmpty(accName) || StringUtils.isEmpty(statsName))
			return;

		final AccumulatorDefinition definition = new AccumulatorDefinition();
		definition.setName(StringUtils.isEmpty(annotation.name()) ? accName : annotation.name());
		definition.setIntervalName(annotation.intervalName());
		definition.setProducerName(producerId);
		definition.setStatName(statsName);
		definition.setValueName(annotation.valueName());
		definition.setTimeUnit(annotation.timeUnit());

		//create and register
		AccumulatorRepository.getInstance().createAccumulator(definition);
	}


	/**
	 * Returns the category to use for the producer registration.
	 *
	 * @param proposal
	 * 		proposal
	 * @return category
	 */
	public String getCategory(final String proposal) {
		return StringUtils.isEmpty(proposal) ? "annotated" : proposal;
	}

	/**
	 * Returns the subsystem for registration.
	 *
	 * @param proposal
	 * 		proposal
	 * @return subsystem
	 */
	public String getSubsystem(final String proposal) {
		return StringUtils.isEmpty(proposal) ? "default" : proposal;
	}

	/**
	 * Perform inner storage cleanUp.
	 */
	public void reset() {
		producers.clear();
	}

}