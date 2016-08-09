package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Count;
import net.anotheria.moskito.aop.annotation.CountByParameter;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;

/**
 * Aspect used to intercept @Count annotated classes.
 *
 * @author lrosenberg, vzhovtiuk
 */
@Aspect
public class CounterAspect extends AbstractMoskitoAspect<CounterStats> {

	private static final IOnDemandStatsFactory FACTORY = new CounterStatsFactory();

	@Around(value = "execution(* *(..)) && (@annotation(method))")
    public Object countMethod(ProceedingJoinPoint pjp, Count method) throws Throwable {
    	return count(pjp, method.producerId(), method.subsystem(), method.category());
    }

	@Around(value = "execution(* *(..)) && (@annotation(method))")
	public Object countByParameter(ProceedingJoinPoint pjp, CountByParameter method) throws Throwable {
		return countByParameter(pjp, method.producerId(), method.subsystem(), method.category());
	}

	@Around(value = "execution(* *.*(..)) && (@within(clazz))")
    public Object countClass(ProceedingJoinPoint pjp, Count clazz) throws Throwable {
    	return count(pjp, clazz.producerId(), clazz.subsystem(), clazz.category());
    }

	private Object countByParameter(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		OnDemandStatsProducer<CounterStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, true, FACTORY, false);

		final Object[] args = pjp.getArgs();

		String caseName = null;
		if (args!=null && args[0]!=null)
			caseName = args[0].toString();


		CounterStats defaultStats = producer.getDefaultStats();
		CounterStats methodStats = null;
		if (caseName!=null){
			methodStats = producer.getStats(caseName);
		}

		defaultStats.inc();
		if (methodStats != null) {
			methodStats.inc();
		}

		try {
			return pjp.proceed();
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}


	private Object count(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		OnDemandStatsProducer<CounterStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, false, FACTORY, false);

		String caseName = pjp.getSignature().getName();
    	CounterStats defaultStats = producer.getDefaultStats();
		CounterStats methodStats = producer.getStats(caseName);

        final Object[] args = pjp.getArgs();
        final String method = pjp.getSignature().getName();
        defaultStats.inc();
        if (methodStats != null) {
            methodStats.inc();
        }

        try {
            return pjp.proceed();
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
