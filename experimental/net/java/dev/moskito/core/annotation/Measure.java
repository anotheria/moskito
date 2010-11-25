package net.java.dev.moskito.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Experimental annotation for annotation based measuring. DO NOT USE.
 * @author lrosenberg
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Measure {

}
