package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.UserActivity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.11.20 16:49
 */
@Aspect
public class UserActivityAspect {

	private Object processUserActivity(ProceedingJoinPoint pjp, String activityName) throws Throwable{
		//System.out.println("UserActivity: "+activityName+" in "+pjp);
		try {
			return pjp.proceed();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Around(value = "execution(* *(..)) && (@annotation(annotation))")
	public Object userActivity(ProceedingJoinPoint pjp, UserActivity annotation) throws Throwable {
		return processUserActivity(pjp, annotation.name());
	}
}
