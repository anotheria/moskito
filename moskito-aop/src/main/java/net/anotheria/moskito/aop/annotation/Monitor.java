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
@Target ( {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {
	/**
	 * Id of the producer for producer registry. If null/unset the class name is extracted.
	 * @return producer id itsekf
	 */
	String producerId() default "";
	
	/**
	 * Subsystem name. If null/unset default will be used.
	 * @return sub-system string
	 */
	String subsystem() default "";
	
	/**
	 * Category name. If null/unset annotated will be used.
	 * @return category string
	 */
	String category() default "";
}
