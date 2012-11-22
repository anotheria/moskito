package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Include the whole class or its method into monitoring.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {
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
