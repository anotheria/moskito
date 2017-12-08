package net.anotheria.moskito.aop.annotation.withsubclasses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.core.accumulation.Accumulator;

/**
 * Annotation holder for several {@link AccumulateWithSubClasses} annotations on same class.
 * This annotation is inherited by sub-classes of the annotated class, if they don't have their own annotation of this type.
 * Can be combined with single {@link AccumulateWithSubClasses} annotation and with non-inheritable {@link Accumulate}/{@link Accumulates}.
 * <p>
 * If value array is empty, no {@link Accumulator} will be created from this annotation. This can be used to stop propagating
 * annotation present on super-class.
 *
 * @author sshscp
 * @see AccumulateWithSubClasses
 */
@Inherited
@Target ( {ElementType.TYPE})
@Retention (RetentionPolicy.RUNTIME)
public @interface AccumulatesWithSubClasses {
	/**
	 * Returns an array of {@link AccumulateWithSubClasses} annotations.
	 *
	 * @return an array of {@link AccumulateWithSubClasses} annotations.
	 */
	AccumulateWithSubClasses[] value();
}
