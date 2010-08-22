package net.java.dev.moskito.core.annotation.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Experimental test code, do not use!
 * @author lrosenberg
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface Time {
    public enum TimeInterval { MILLISECOND, NANOSECOND };
    TimeInterval interval() default TimeInterval.MILLISECOND;
    String format() default "Elapsed %s";
}
 