package net.anotheria.moskito.integration.cdi.monitor;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interceptor binding annotation for MoSKito interceptors.
 *
 * @author Alex Osadchy
 */
@Inherited
@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Monitor {

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
