package net.anotheria.moskito.aop.annotation.withsubclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.anotheria.moskito.core.stats.TimeUnit;


/**
 * This annotation is a signaling annotation for aop style integration.
 * If found it will lead to creation of an accumulator(s). The created accumulator(s) will have
 * either a specified name(followed with class name) or a name consisting of producerID.statName.valueName.interval.
 *
 * @author sshscp
 */
@Inherited
@Target (ElementType.TYPE)
@Retention (RetentionPolicy.RUNTIME)
public @interface AccumulateWithSubClasses {
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
