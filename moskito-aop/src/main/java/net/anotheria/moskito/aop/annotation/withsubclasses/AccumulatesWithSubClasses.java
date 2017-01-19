package net.anotheria.moskito.aop.annotation.withsubclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sshscp
 */
@Target ( {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention (RetentionPolicy.RUNTIME)
public @interface AccumulatesWithSubClasses {
	AccumulateWithSubClasses[] value();
}
