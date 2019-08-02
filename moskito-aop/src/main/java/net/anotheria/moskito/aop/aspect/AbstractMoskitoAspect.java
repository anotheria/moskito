package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.StatName;
import net.anotheria.moskito.aop.annotation.withsubclasses.AccumulateWithSubClasses;
import net.anotheria.moskito.aop.annotation.withsubclasses.AccumulatesWithSubClasses;
import net.anotheria.moskito.aop.aspect.support.AccumulatorUtil;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.logging.DefaultStatsLogger;
import net.anotheria.moskito.core.logging.IntervalStatsLogger;
import net.anotheria.moskito.core.logging.SLF4JLogOutput;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.threshold.CustomThresholdProvider;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.util.annotation.AnnotationUtils;
import net.anotheria.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;
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
	public static final char DOT = '.';

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
	 * @return {@link OnDemandStatsProducer}
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
	 * @return {@link OnDemandStatsProducer}
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

		Class<?>[] intrfaces = producerClass.getInterfaces();
		for (Class<?> intrface : intrfaces){
			if (intrface.getName().equals(CustomThresholdProvider.class.getName())){
				setupCustomThresholds((CustomThresholdProvider)pjp.getTarget(), producer);
			}
		}



		return producer;

	}

	/**
	 * Returns method name or method stats name by aop signature considering {@link StatName} annotation.
	 *
	 * @param signature
	 *         aop signature
	 * @return method name or null if signature also null
	 */
	protected String getMethodStatName(Signature signature) {
		if (signature == null) {
			return null;
		}

		if (signature instanceof MethodSignature) {
			Method method = ((MethodSignature) signature).getMethod();
			StatName statName = method.getAnnotation(StatName.class);
			return statName == null ? method.getName() : statName.value();
		}

		return signature.getName();
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
		if (withMethod) {
			res += DOT + getMethodStatName(pjp.getSignature());
		}
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
		final Accumulates accAnnotationHolderMethods = AnnotationUtils.findAnnotation(method, Accumulates.class);
		if (accAnnotationHolderMethods != null)
			createAccumulators(producer, method, accAnnotationHolderMethods.value());

		//If there is no @Accumulates annotation but @Accumulate is present
		final Accumulate annotation = AnnotationUtils.findAnnotation(method, Accumulate.class);
		createAccumulators(producer, method, annotation);
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

		//several @AccumulateWithSubClasses in accumulators holder
		final AccumulatesWithSubClasses accWSCAnnotationHolder = AnnotationUtils.findAnnotation(producerClass, AccumulatesWithSubClasses.class);
		if (accWSCAnnotationHolder != null) {
			createAccumulators(producer, producerClass, accWSCAnnotationHolder.value());
		}

		//If there is no @AccumulatesWithSubClasses annotation but @Accumulate is present
		final AccumulateWithSubClasses accumulateWSC = AnnotationUtils.findAnnotation(producerClass, AccumulateWithSubClasses.class);
		createAccumulators(producer, producerClass, accumulateWSC);

		//several @Accumulate in accumulators holder
		final Accumulates accAnnotationHolder = AnnotationUtils.findAnnotation(producerClass, Accumulates.class);
		if (accAnnotationHolder != null) {
			createAccumulators(producer, null, accAnnotationHolder.value());
		}

		//If there is no @Accumulates annotation but @Accumulate is present
		final Accumulate annotation = AnnotationUtils.findAnnotation(producerClass, Accumulate.class);
		createAccumulators(producer, null, annotation);
	}

	/**
	 * Create method/class level accumulators from {@link Accumulate} annotations.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param method
	 * 		annotated method for method level accumulators or null for class level
	 * @param annotations
	 * 		{@link Accumulate} annotations to process
	 */
	private void createAccumulators(final OnDemandStatsProducer<S> producer, final Method method, Accumulate... annotations) {
		for (final Accumulate annotation : annotations)
			if (annotation != null)
				AccumulatorUtil.getInstance().createAccumulator(producer, annotation, method);
	}

	/**
	 * Create class level accumulators from {@link AccumulateWithSubClasses} annotations.
	 *
	 * @param producer
	 * 		{@link OnDemandStatsProducer}
	 * @param producerClass
	 * 		producer class
	 * @param annotations
	 * 		{@link AccumulateWithSubClasses} annotations to process
	 */
	private void createAccumulators(final OnDemandStatsProducer<S> producer, final Class producerClass, AccumulateWithSubClasses... annotations) {
		for (final AccumulateWithSubClasses annotation : annotations)
			if (annotation != null)
				AccumulatorUtil.getInstance(producerClass).createAccumulator(producer, annotation);
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


	private void setupCustomThresholds(CustomThresholdProvider customThresholdProvider, OnDemandStatsProducer<S> producer){
		List<String> thresholdNames = customThresholdProvider.getCustomThresholdNames();
		ThresholdRepository repository = ThresholdRepository.getInstance();
		for (String name :thresholdNames){
			repository.createCustomThreshold(name, customThresholdProvider, producer);

		}

	}

}
