package net.anotheria.moskito.aop.annotation.withsubclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.core.stats.TimeUnit;


/**
 * This annotation is a signaling annotation for aop style integration.
 * If found it will lead to creation of an accumulator(s).
 * The created accumulator(s) will have either a specified name(followed with '#' and a class name)
 * or a name consisting of producerID.valueName.interval.
 *
 *  Inherited by sub-classes if they don't have their own annotation of this type.
 *  In case if some sub-classes should not inherit this annotation consider changing it to {@link AccumulatesWithSubClasses}.
 *
 * @author sshscp
 * @see Accumulate
 * @see AccumulatesWithSubClasses
 */
@Inherited
@Target (ElementType.TYPE)
@Retention (RetentionPolicy.RUNTIME)
public @interface AccumulateWithSubClasses {
	/**
	 * Name of the accumulator. Optional, if set will be concatenated with '#' and a class name, otherwise - calculated from producerID.valueName.interval.
	 *
	 * @return name of the accumulator.
	 */
	String name() default "";

	/**
	 * Name of the value. This is in fact the only required settings. Depends on the StatsFactory you are using.
	 * Typically this will be Service stats and typically this value should be one of "TR", "TT", "CR", "MCR", "ERR", "Last", "Min", "Max", "Avg", "ERate".
	 * Note that 'req' is alias for 'TR'.
	 *
	 * @return name of the accumulated value.
	 */
	String valueName();

	/**
	 * Name of the interval. Default is 5 minutes.
	 *
	 * @return name of the interval.
	 */
	String intervalName() default "5m";

	/**
	 * TimeUnit. Default is milliseconds.
	 *
	 * @return time unit
	 */
	TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
