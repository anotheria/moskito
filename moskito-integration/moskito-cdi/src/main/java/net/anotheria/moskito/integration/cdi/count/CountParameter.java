package net.anotheria.moskito.integration.cdi.count;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to override counter parameter name.
 *
 * @author Alex Osadchy
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountParameter {

    /**
     * Counter parameter name.
     *
     * @return counter parameter name
     */
    String value();

}
