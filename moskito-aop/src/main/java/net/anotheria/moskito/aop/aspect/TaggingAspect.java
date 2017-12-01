package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.TagParameter;
import net.anotheria.moskito.aop.annotation.TagReturnValue;
import net.anotheria.moskito.core.context.MoSKitoContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;

/**
 * Aspect used to intercept @TagReturnValue/@TagParameter annotated method calls and their parameters.
 *
 * @author esmakula
 */
@Aspect
public class TaggingAspect {

	/**
	 * Do tagging for @TagReturnValue.
	 */
	@Around(value = "execution(* *(..)) && (@annotation(tagReturnValue))")
	public Object doTagging(ProceedingJoinPoint pjp, TagReturnValue tagReturnValue) throws Throwable {
		Object result = pjp.proceed();
		MoSKitoContext.addTag(tagReturnValue.name(), result == null ? null : result.toString());
		return result;
	}

	/**
	 * Do tagging for @TagParameter.
	 */
	@Around(value = "execution(* *(.., @TagParameter (*), ..))")
	public Object doTagging(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature methodSig = (MethodSignature) pjp.getSignature();
		Annotation[][] annotations = methodSig.getMethod().getParameterAnnotations();
		Object[] args = pjp.getArgs();

		for (int i = 0; i < args.length; i++) {
			for (Annotation annotation : annotations[i]) {
				if (TagParameter.class.isInstance(annotation)) {
					TagParameter tagParameter = (TagParameter) annotation;
					MoSKitoContext.addTag(tagParameter.name(), args[i] == null ? null : args[i].toString());
				}
			}
		}
		return pjp.proceed();
	}
}
