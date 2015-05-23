package net.anotheria.moskito.aop.annotation;

import net.anotheria.moskito.core.stats.TimeUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.05.15 00:09
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Accumulate {
	/**
	 * Name of the accumulator.
	 * @return
	 */
	String name();

	/**
	 * Name of the value.
	 * @return
	 */
	String valueName();

	/**
	 * Name of the interval.
	 * @return
	 */
	String intervalName() default "5m";

	/**
	 * Timeunit.
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;


}
