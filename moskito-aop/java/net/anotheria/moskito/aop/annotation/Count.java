package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Count all accesses to the target. This is a 'light' version of @MonitorClass, @MonitorMethod annotations.
 *
 * @author lrosenberg
 * @since 18.11.12 23:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Count {
	/**
	 * Id of the producer for producer registry. If null/unset the class name is extracted.
	 * @return
	 */
	String producerId() default "";

	/**
	 * Subsystem name. If null/unset default will be used.
	 * @return
	 */
	String subsystem() default "";

	/**
	 * Category name. If null/unset annotated will be used.
	 */
	String category() default "";
}
