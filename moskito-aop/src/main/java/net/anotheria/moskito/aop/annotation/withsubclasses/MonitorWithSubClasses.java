package net.anotheria.moskito.aop.annotation.withsubclasses;

import net.anotheria.moskito.aop.annotation.Monitor;

import java.lang.annotation.*;

/**
 * @author bvanchuhov
 */
@Inherited
@Monitor
@Target (ElementType.TYPE)
@Retention (RetentionPolicy.RUNTIME)
public @interface MonitorWithSubClasses {
}
