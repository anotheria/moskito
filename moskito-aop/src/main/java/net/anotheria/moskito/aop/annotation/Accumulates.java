package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For adding multiple accumulators on class or method
 *
 * Created by Roman Stetsiuk on 9/29/15.
 */
@Target ( {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Accumulates {
    /**
     * Accumulators
     */
    Accumulate[] value();
}
