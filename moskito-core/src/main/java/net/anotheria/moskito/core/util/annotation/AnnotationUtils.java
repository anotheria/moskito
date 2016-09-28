package net.anotheria.moskito.core.util.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
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

	private AnnotationUtils() {
		throw new IllegalAccessError("CAn't be instantiated");
	}

	/**
	 * Deep search of specified annotation considering "annotation as meta annotation" case (annotation annotated with specified annotation).
	 *
	 * @param source
	 * 		specified annotated element
	 * @param targetAnnotationClass
	 * 		specified annotation class
	 * @param <A>
	 * 		the type of the annotation to query for and return if present
	 * @return specified element's annotation for the specified annotation type if deeply present on this element, else null
	 */
	public static <A extends Annotation> A findAnnotation(final AnnotatedElement source, final Class<A> targetAnnotationClass) {
		Objects.requireNonNull(source, "incoming 'source' is not valid");
		Objects.requireNonNull(targetAnnotationClass, "incoming 'targetAnnotationClass' is not valid");

		final A result = source.getAnnotation(targetAnnotationClass);
		if (result != null)
			return result;

		return findAnnotationInAnnotations(source, targetAnnotationClass);
	}

	/**
	 * Tries to find required annotation in scope of all annotations of incoming 'source'.
	 *
	 * @param source
	 * 		{@link AnnotatedElement}
	 * @param targetAnnotationClass
	 * 		{@link Class} - actually required annotation class
	 * @param <A>
	 * 		- type param
	 * @return {@link A} in case if found, {@code null} otherwise
	 */
	private static <A extends Annotation> A findAnnotationInAnnotations(final AnnotatedElement source, final Class<A> targetAnnotationClass) {
		final Annotation[] allAnnotations = source.getAnnotations();
		for (final Annotation annotation : allAnnotations) {
			final A result = findAnnotation(annotation, targetAnnotationClass);
			if (result != null)
				return result;

		}
		return null;
	}

	/**
	 * Deep search of specified source considering "annotation as meta annotation" case (source annotated with specified source).
	 *
	 * @param source
	 * 		{@link Annotation} - source
	 * @param targetAnnotationClass
	 * 		represents class of the required annotaiton
	 * @param <A>
	 * 		type param
	 * @return {@link A} in case if found, {@code null} otherwise
	 */
	public static <A extends Annotation> A findAnnotation(final Annotation source, final Class<A> targetAnnotationClass) {
		Objects.requireNonNull(source, "incoming 'source' is not valid");
		Objects.requireNonNull(targetAnnotationClass, "incoming 'targetAnnotationClass' is not valid");
		return findAnnotation(source, targetAnnotationClass, new HashSet<Class<? extends Annotation>>());
	}

	/**
	 * Recursive annotation search, with check in all assigned {@link ElementType#ANNOTATION_TYPE} - annotations.
	 *
	 * @param sourceAnnotation
	 * 		incoming / source {@link Annotation}
	 * @param targetAnnotationClass
	 * 		target / required annotation class
	 * @param alreadyCheckedAnnotations
	 * 		annotations which were already checked
	 * @param <A>
	 * 		type param
	 * @return * @return {@link A} in case if found, {@code null} otherwise
	 */
	private static <A extends Annotation> A findAnnotation(final Annotation sourceAnnotation,
														   final Class<A> targetAnnotationClass, final Set<Class<? extends Annotation>> alreadyCheckedAnnotations) {

		final Class<? extends Annotation> currentAnnotationType = sourceAnnotation.annotationType();
		alreadyCheckedAnnotations.add(currentAnnotationType);

		final A result = currentAnnotationType.getAnnotation(targetAnnotationClass);
		if (result != null)
			return result;

		final Annotation[] allAnnotations = currentAnnotationType.getAnnotations();
		for (final Annotation annotation : allAnnotations) {
			final Class<? extends Annotation> annotationType = annotation.annotationType();
			if (alreadyCheckedAnnotations.contains(annotationType))
				continue;
			final A found = findAnnotation(annotation, targetAnnotationClass, alreadyCheckedAnnotations);
			if (found != null)
				return found;
		}

		return null;
	}

}
