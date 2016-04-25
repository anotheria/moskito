package net.anotheria.moskito.aop.util;

import org.junit.Test;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static net.anotheria.moskito.aop.util.AnnotationUtils.findAnnotation;
import static org.junit.Assert.assertTrue;

/**
 * @author bvanchuhov
 */
public class AnnotationUtilsTest {

    @Test
    public void testFindAnnotation_simpleAnnotation() throws Exception {
        @Config
        class MyClass {}

        assertTrue(findAnnotation(MyClass.class, Config.class).annotationType() == Config.class);
    }

    @Test
    public void testFindAnnotation_simpleAnnotation_classInheritance() throws Exception {
        @Config
        class Parent {}

        class Child extends Parent {}

        assertTrue(findAnnotation(Child.class, Config.class).annotationType() == Config.class);
    }

    @Test
    public void testFindAnnotation_annotationInheritance() throws Exception {
        @ChildConfig
        class MyClass {}

        assertTrue(findAnnotation(MyClass.class, Config.class).annotationType() == Config.class);
    }

    @Test
    public void testFindAnnotation_annotationInheritance_classInheritance() throws Exception {
        @ChildConfig
        class Parent {}

        class Child extends Parent {}

        assertTrue(findAnnotation(Child.class, Config.class).annotationType() == Config.class);
    }

    @Test
    public void testFindAnnotation_recursiveAnnotation() throws Exception {
        @RecursiveAnnotation
        class MyClass {}

        assertTrue(findAnnotation(MyClass.class, RecursiveAnnotation.class).annotationType() == RecursiveAnnotation.class);
    }

    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Config {
    }

    @Config
    @Inherited
    @Retention(RetentionPolicy.RUNTIME)
    private @interface ChildConfig {
    }

    @RecursiveAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    private @interface RecursiveAnnotation {
    }
}