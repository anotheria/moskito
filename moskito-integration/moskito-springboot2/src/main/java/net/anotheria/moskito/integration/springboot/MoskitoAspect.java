package net.anotheria.moskito.integration.springboot;

import static net.anotheria.moskito.core.util.annotation.AnnotationUtils.findAnnotation;
import static net.anotheria.moskito.core.util.annotation.AnnotationUtils.findTypeAnnotation;

import java.lang.reflect.Method;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.aspect.MonitoringBaseAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
//@Component
public class MoskitoAspect extends MonitoringBaseAspect {

  /**
   * Common method profiling entry-point.
   *
   * @param pjp    {@link ProceedingJoinPoint}
   * @param method {@link Monitor}
   * @return call result
   * @throws Throwable in case of error during {@link ProceedingJoinPoint#proceed()}
   */
  @Around(value = "execution(* *(..)) && (@annotation(method))")
  public Object doProfilingMethod(ProceedingJoinPoint pjp, Monitor method) throws Throwable {
    return doProfiling(pjp, method.producerId(), method.subsystem(), method.category());
  }

  /**
   * Common class profiling entry-point.
   *
   * @param pjp     {@link ProceedingJoinPoint}
   * @param monitor {@link Monitor}
   * @return call result
   * @throws Throwable in case of error during {@link ProceedingJoinPoint#proceed()}
   */
  @Around(value = "execution(* *.*(..)) && @within(monitor) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
  public Object doProfilingClass(ProceedingJoinPoint pjp, Monitor monitor) throws Throwable {
    return doProfiling(pjp, monitor.producerId(), monitor.subsystem(), monitor.category());
  }

  /**
   * Special method profiling entry-point, which allow to fetch {@link Monitor} from one lvl up of
   * method annotation. Pattern will pre-select 2-nd lvl annotation on method scope.
   *
   * @param pjp {@link ProceedingJoinPoint}
   * @return call result
   * @throws Throwable in case of error during {@link ProceedingJoinPoint#proceed()}
   */
  @Around(value = "execution(@(@net.anotheria.moskito.aop.annotation.Monitor *) * *(..)) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
  public Object doProfilingMethod(final ProceedingJoinPoint pjp) throws Throwable {
    return doProfilingMethod(pjp, resolveAnnotation(pjp));
  }

  /**
   * Special class profiling entry-point, which allow to fetch {@link Monitor} from one lvl up of
   * class annotation. Pattern will pre-select 2-nd lvl annotation on class scope.
   *
   * @param pjp {@link ProceedingJoinPoint}
   * @return call result
   * @throws Throwable in case of error during {@link ProceedingJoinPoint#proceed()}
   */
  @Around(value = "execution(* (@(@net.anotheria.moskito.aop.annotation.Monitor *) *).*(..)) && !@annotation(net.anotheria.moskito.aop.annotation.DontMonitor)")
  public Object doProfilingClass(final ProceedingJoinPoint pjp) throws Throwable {
    return doProfilingClass(pjp, resolveAnnotation(pjp));
  }

  /**
   * Trying to resolve {@link Monitor} annotation first in method annotations scope, then in class
   * scope. Note - method will also check if {@link Monitor} is Placed to some other annotation as
   * meta! Search order : - 1 method; - 2 type.
   *
   * @param pjp {@link ProceedingJoinPoint}
   * @return {@link Monitor} or {@code null}
   */
  private Monitor resolveAnnotation(final ProceedingJoinPoint pjp) {
    final Signature signature = pjp.getSignature();
    final Class<?> type = signature.getDeclaringType();
    final Method method =
        (signature instanceof MethodSignature) ? MethodSignature.class.cast(signature).getMethod() :
            null;
    final Monitor methodMonitorAnn = method != null ? findAnnotation(method, Monitor.class) : null;
    if (methodMonitorAnn != null) {
      return methodMonitorAnn;
    }
    return findTypeAnnotation(type, Monitor.class);
  }
}