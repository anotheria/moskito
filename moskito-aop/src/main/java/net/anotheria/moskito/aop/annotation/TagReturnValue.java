package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Include method return value to be tagged.
 *
 * @author esmakula
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TagReturnValue {

	/**
	 * Tag name to be used.
	 *
	 * @return tag name
	 */
	String name();

}
