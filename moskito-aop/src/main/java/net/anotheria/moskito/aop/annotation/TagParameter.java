package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Include method parameter to be tagged.
 *
 * @author esmakula
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagParameter {

	/**
	 * Tag name to be used.
	 *
	 * @return tag name
	 */
	String name();
}
