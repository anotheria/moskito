package net.anotheria.moskito.aop.aspect.support;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.withsubclasses.AccumulateWithSubClasses;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.util.StringUtils;

import java.lang.reflect.Method;

import static net.anotheria.moskito.aop.aspect.AbstractMoskitoAspect.DOT;

/**
 * Utility class for creating accumulators.
 *
 * @param <A>
 * 		type of source annotation - {@link Accumulate} or {@link AccumulateWithSubClasses}
 * @author sshscp
 */
public abstract class AccumulatorUtil<A> {

	private AccumulatorUtil() {
	}

	/**
	 * Get AccumulatorUtil instance designed to create accumulators from {@link Accumulate} annotations.
	 *
	 * @return new instance of AccumulatorUtil.
	 */
	public static AccumulatorUtil<Accumulate> getInstance() {
		return new AccumulatorHelper();
	}

	/**
	 * Get AccumulatorUtil instance designed to create accumulators from {@link AccumulateWithSubClasses} annotations.
	 *
	 * @return new instance of AccumulatorUtil.
	 */
	public static AccumulatorUtil<AccumulateWithSubClasses> getInstance(final Class producerClass) {
		return new AccumulatorWSCHelper(producerClass);
	}

	/**
	 * Create and register new {@link Accumulator} for class-level stats accumulation.
	 * In case producer or annotation is null does nothing.
	 *
	 * @param producer
	 * 		stats producer
	 * @param annotation
	 * 		{@link Accumulate} or {@link AccumulateWithSubClasses} annotation used to create {@link Accumulator}.
	 */
	public void createAccumulator(final OnDemandStatsProducer producer, final A annotation) {
		createAccumulator(producer, annotation, null);
	}

	/**
	 * Create and register new {@link Accumulator} for class/method level stats accumulation.
	 * In case producer or annotation is null does nothing.
	 *
	 * @param producer
	 * 		stats producer
	 * @param annotation
	 * 		{@link Accumulate} or {@link AccumulateWithSubClasses} annotation used to create {@link Accumulator}.
	 * @param method
	 * 		{@link Method} that was annotated or null in case ot class-level accumulator.
	 */
	public void createAccumulator(final OnDemandStatsProducer producer, final A annotation, final Method method) {
		if (producer != null && annotation != null) {

			final String statsName = (method == null) ? OnDemandStatsProducer.CUMULATED_STATS_NAME : method.getName();

			String accumulatorName = getName(annotation);
			if (StringUtils.isEmpty(accumulatorName))
				accumulatorName = method == null ? formAccumulatorNameForClass(producer, annotation) :
						formAccumulatorNameForMethod(producer, annotation, method);

			createAccumulator(
					producer.getProducerId(),
					annotation,
					accumulatorName,
					statsName
			);
		}
	}

	private void createAccumulator(final String producerId, final A annotation, final String accName, final String statsName) {
		if (annotation == null || StringUtils.isEmpty(producerId) || StringUtils.isEmpty(accName) || StringUtils.isEmpty(statsName)) {
			return;
		}

		final AccumulatorDefinition definition = new AccumulatorDefinition();
		definition.setName(accName);
		definition.setIntervalName(getIntervalName(annotation));
		definition.setProducerName(producerId);
		definition.setStatName(statsName);
		definition.setValueName(getValueName(annotation));
		definition.setTimeUnit(getTimeUnit(annotation));

		//create and register
		AccumulatorRepository.getInstance().createAccumulator(definition);
	}

	private String formAccumulatorNameForClass(final OnDemandStatsProducer<?> producer, final A annotation) {
		if (producer == null || annotation == null) {
			return "";
		}
		return generateAccumulatorName(producer.getProducerId(), getValueName(annotation), getIntervalName(annotation));
	}

	private String formAccumulatorNameForMethod(final OnDemandStatsProducer<?> producer, final A annotation, final Method m) {
		if (producer == null || annotation == null || m == null) {
			return "";
		}
		return generateAccumulatorName(producer.getProducerId(), m.getName(), getValueName(annotation), getIntervalName(annotation));
	}

	private static String generateAccumulatorName(String... args) {
		return (args == null || args.length == 0) ? "" : StringUtils.combineStrings(args, DOT);
	}

	abstract String getName(A annotation);

	abstract String getValueName(A annotation);

	abstract String getIntervalName(A annotation);

	abstract TimeUnit getTimeUnit(A annotation);

	/**
	 * AccumulatorUtil child restricted to {@link Accumulate} annotations.
	 */
	private static final class AccumulatorHelper extends AccumulatorUtil<Accumulate> {
		String getName(Accumulate annotation) {
			return annotation.name();
		}

		String getValueName(Accumulate annotation) {
			return annotation.valueName();
		}

		String getIntervalName(Accumulate annotation) {
			return annotation.intervalName();
		}

		TimeUnit getTimeUnit(Accumulate annotation) {
			return annotation.timeUnit();
		}
	}

	/**
	 * AccumulatorUtil child restricted to {@link AccumulateWithSubClasses} annotations.
	 */
	private static final class AccumulatorWSCHelper extends AccumulatorUtil<AccumulateWithSubClasses> {
		/**
		 * Producer class name is added to generated accumulator name to make accumulator names more informative.
		 */
		private final String producerClassName;

		AccumulatorWSCHelper(final Class producerClass) {
			producerClassName = producerClass.isAnonymousClass() ? producerClass.getName() : producerClass.getSimpleName();
		}

		String getName(AccumulateWithSubClasses annotation) {
			return StringUtils.isEmpty(annotation.name()) ? "" : annotation.name() + "#" + producerClassName;
		}

		String getValueName(AccumulateWithSubClasses annotation) {
			return annotation.valueName();
		}

		String getIntervalName(AccumulateWithSubClasses annotation) {
			return annotation.intervalName();
		}

		TimeUnit getTimeUnit(AccumulateWithSubClasses annotation) {
			return annotation.timeUnit();
		}
	}
}
