package net.anotheria.moskito.integration.cdi.count;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interceptor binding for MoSKito counter stats.
 *
 * @author Alex Osadchy
 * @author Leon Rosenberg
 */
@Inherited
@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Count {

    /**
     * Producer Id.
     *
     * @return producer id
     */
    @Nonbinding String producerId() default "";

    /**
     * Category.
     *
     * @return category name
     */
    @Nonbinding String category() default "default";

    /**
     * Subsystem.
     *
     * @return subsystem name
     */
    @Nonbinding String subsystem() default "default";

}
