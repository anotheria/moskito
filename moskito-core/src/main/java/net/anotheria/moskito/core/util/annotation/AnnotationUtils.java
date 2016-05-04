package net.anotheria.moskito.core.util.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class contains utility methods with annotations
 *
 * @author bvanchuhov
 */
public final class AnnotationUtils {

    private AnnotationUtils() {}

    /**
     * Deep search of specified annotation considering "annotation inheritance" (annotation annotated with specified annotation).
     *
     * @param annotatedElement specified annotated element
     * @param annotationClass specified annotation class
     * @param <A> the type of the annotation to query for and return if present
     * @return specified element's annotation for the specified annotation type if deeply present on this element, else null
     */
    public static <A extends Annotation> A findAnnotation(AnnotatedElement annotatedElement, Class<A> annotationClass) {
        Objects.requireNonNull(annotatedElement);
        Objects.requireNonNull(annotationClass);

        A result = annotatedElement.getAnnotation(annotationClass);
        if (result != null) {
            return result;
        }

        return findAnnotationInAnnotations(annotatedElement, annotationClass);
    }

    private static <A extends Annotation> A findAnnotationInAnnotations(AnnotatedElement annotatedElement, Class<A> annotationClass) {
        Annotation[] allAnnotations = annotatedElement.getAnnotations();
        for (Annotation annotation : allAnnotations) {
            A result = findAnnotation(annotation, annotationClass);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * Deep search of specified annotation considering "annotation inheritance" (annotation annotated with specified annotation).
     *
     * @param annotation
     * @param annotationClass
     * @param <A>
     * @return
     */
    public static  <A extends Annotation> A findAnnotation(Annotation annotation, Class<A> annotationClass) {
        Set<Class<? extends Annotation>> visitedAnnotations = new HashSet<Class<? extends Annotation>>();

        return findAnnotation(annotation, annotationClass, visitedAnnotations);
    }

    private static <A extends Annotation> A findAnnotation(Annotation currentAnnotation,
                                                           Class<A> annotationClass, Set<Class<? extends Annotation>> visitedAnnotations) {

        Class<? extends Annotation> currentAnnotationType = currentAnnotation.annotationType();
        visitedAnnotations.add(currentAnnotationType);

        A result = currentAnnotationType.getAnnotation(annotationClass);
        if (result != null) {
            return result;
        }

        Annotation[] allAnnotations = currentAnnotationType.getAnnotations();
        for (Annotation annotation : allAnnotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (visitedAnnotations.contains(annotationType)) {
                continue;
            }

            result = findAnnotation(annotation, annotationClass, visitedAnnotations);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

}
