package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Count all accesses to the target. The difference between this annotation and the @Count annotation is that it can
 * only be applied on methods and uses the return value of the method (as string) as stat name.
 *
 * @author lrosenberg
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CountByReturnValue {
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
