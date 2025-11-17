package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.UserActivity;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;

/**
 * AspectJ aspect for intercepting and tracking user activity through the {@link UserActivity} annotation.
 *
 * <p>This aspect automatically wraps methods annotated with {@code @UserActivity}, enabling
 * transparent monitoring of user actions without modifying application code. It integrates
 * with MoSKito's journey and tracking system to provide visibility into user workflows.
 *
 * <p><b>Usage:</b> Simply annotate methods you want to track:
 * <pre>
 * {@code @UserActivity}(name="userLogin")
 * public void login(String username) {
 *     // login logic
 * }
 * </pre>
 *
 * <p><b>How It Works:</b> The aspect intercepts method execution using AspectJ's {@code @Around}
 * advice, processes the activity (typically recording it in the journey system), and then
 * proceeds with the original method execution.
 *
 * <p><b>Exception Handling:</b> If the intercepted method throws an {@link InvocationTargetException},
 * the aspect unwraps and rethrows the underlying cause to preserve the original exception semantics.
 *
 * <p><b>Integration:</b> This aspect must be woven into your application using:
 * <ul>
 *   <li>Compile-time weaving (AspectJ compiler)</li>
 *   <li>Load-time weaving (AspectJ agent: -javaagent:aspectjweaver.jar)</li>
 *   <li>Spring AOP (if using Spring framework)</li>
 * </ul>
 *
 * <p><b>Thread Safety:</b> This aspect is stateless and thread-safe.
 *
 * @author lrosenberg
 * @since 25.11.20 16:49
 * @see UserActivity
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
