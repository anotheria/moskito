package net.anotheria.moskito.integration.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 16:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProducerDefinition {
	/**
	 * Id of the producer for producer registry.
	 * @return
	 */
	String producerId();

	/**
	 * Subsystem name. If null/unset default will be used.
	 * @return
	 */
	String subsystem() default "default";

	/**
	 * Category name. If null/unset annotated will be used.
	 */
	String category() default "default";
}
