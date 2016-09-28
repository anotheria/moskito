package net.anotheria.moskito.aop.annotation;

import net.anotheria.moskito.core.stats.TimeUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is a signaling annotation for aop style integration. If found it will lead to creation of an
 * accumulator. The created accumulator will have either a specified name or a name consisting of producerID.statName.valueName.interval.
 *
 * @author lrosenberg
 * @since 24.05.15 00:09
 */
@Target ( {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Accumulate {
	/**
	 * Name of the accumulator. Optional, if not set a name is calculated from producerID.statName.valueName.interval.
	 * StatName will be '' for class-wide accumulator.
	 * @return
	 */
	String name() default "";

	/**
	 * Name of the value. This is in fact the only required settings. Depends on the StatsFactory you are using. Typically this will be Service stats and typically this value should be one of
	 * "TR", "TT", "CR", "MCR", "ERR", "Last", "Min", "Max", "Avg", "ERate".
	 * Note that 'req' is alias for 'TR'.
	 * @return
	 */
	String valueName();

	/**
	 * Name of the interval. Default is 5 minutes.
	 * @return
	 */
	String intervalName() default "5m";

	/**
	 * Timeunit. Default is milliseconds.
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;


}
