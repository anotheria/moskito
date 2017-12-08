package net.anotheria.moskito.core.util.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static net.anotheria.moskito.core.util.annotation.AnnotationUtils.findAnnotation;
import static net.anotheria.moskito.core.util.annotation.AnnotationUtils.findTypeAnnotation;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
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
        assertTrue(findAnnotation(MyClass.class, SimpleMarkerAnnotation.class).annotationType() == SimpleMarkerAnnotation.class);
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

	@Retention (RetentionPolicy.RUNTIME)
	@Target(ElementType.ANNOTATION_TYPE)
    private @interface SimpleMarkerAnnotation {
	}

	@SimpleMarkerAnnotation
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


	@Retention(RetentionPolicy.RUNTIME)
	private @interface TypeAnnotation {
	}
	@Retention(RetentionPolicy.RUNTIME)
	private @interface ParentTypeAnnotation {
	}
	@Retention(RetentionPolicy.RUNTIME)
	private @interface SuperParentTypeAnnotation {
	}
	interface SimpleMarker {
	}

	@Test
	public void testFindAnnotation_typeAnnotation() throws Exception {
		@SuperParentTypeAnnotation
		@ChildConfig
		class SuperParentTestClass {}
		@ParentTypeAnnotation
		class ParentTestClass extends SuperParentTestClass{}
		@TypeAnnotation
		class MyTestClass extends ParentTestClass {}

		checkFoundAnnotation(MyTestClass.class, TypeAnnotation.class);
		checkFoundAnnotation(MyTestClass.class, ParentTypeAnnotation.class);
		checkFoundAnnotation(MyTestClass.class, SuperParentTypeAnnotation.class);

		assertThat("Found absent annotation!", findTypeAnnotation(MyTestClass.class, Deprecated.class), nullValue());
		assertThat("Found absent annotation!", findTypeAnnotation(TypeAnnotation.class, Deprecated.class), nullValue());
		assertThat("Found absent annotation!", findTypeAnnotation(SimpleMarker.class, Retention.class), nullValue());

		checkFoundAnnotation(ChildConfig.class, Config.class);
		checkFoundAnnotation(MyTestClass.class, Config.class);
	}

	private static <A extends Annotation> void checkFoundAnnotation(final Class<?> type, final Class<A> targetAnnotationClass) {
    	A annotation = findTypeAnnotation(type, targetAnnotationClass);
    	assertThat("Annotation not found!", annotation, notNullValue());
    	assertTrue(annotation.annotationType() == targetAnnotationClass);
	}
}