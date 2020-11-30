package net.anotheria.moskito.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates something as a user activity. The execution won't be monitored as performance (although
 * you still can add other annotations or annotate producers), but will be part of a user session.
 *
 * @author lrosenberg
 * @since 25.11.20 16:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})

public @interface UserActivity {
	String name();
}
