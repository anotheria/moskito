package net.anotheria.moskito.aop.aspect;

import net.anotheria.moskito.aop.annotation.Count;
import net.anotheria.moskito.aop.annotation.CountByParameter;
import net.anotheria.moskito.aop.annotation.CountByReturnValue;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
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

	private static final CounterStatsFactory FACTORY = new CounterStatsFactory();

	/**
	 * Pointcut definition for @Count annotation at method level.
	 * @param pjp ProceedingJoinPoint.
	 * @param annotation Count annotation.
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(* *(..)) && (@annotation(annotation))")
    public Object countMethod(ProceedingJoinPoint pjp, Count annotation) throws Throwable {
    	return count(pjp, annotation.producerId(), annotation.subsystem(), annotation.category());
    }

	/**
	 * Pointcut definition for @CountByParameter annotation.
	 * @param pjp ProceedingJoinPoint.
	 * @param annotation CountByParameter annotation.
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(* *(..)) && (@annotation(annotation))")
	public Object countByParameter(ProceedingJoinPoint pjp, CountByParameter annotation) throws Throwable {
		return countByParameter(pjp, annotation.producerId(), annotation.subsystem(), annotation.category());
	}

	/**
	 * Pointcut definition for @CountByReturnValue annotation.
	 * @param pjp ProceedingJoinPoint.
	 * @param annotation CountByReturnValue annotation.
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(* *(..)) && (@annotation(annotation))")
	public Object countByReturnValue(ProceedingJoinPoint pjp, CountByReturnValue annotation) throws Throwable {
		return countByReturnValue(pjp, annotation.producerId(), annotation.subsystem(), annotation.category());
	}

	/**
	 * Pointcut definition for @Count annotation at class level.
	 * @param pjp ProceedingJoinPoint.
	 * @param annotation Count annotation.
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(* *.*(..)) && (@within(annotation))")
    public Object countClass(ProceedingJoinPoint pjp, Count annotation) throws Throwable {
    	return count(pjp, annotation.producerId(), annotation.subsystem(), annotation.category());
    }

	private Object countByParameter(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		OnDemandStatsProducer<CounterStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, true, FACTORY, false);

		final Object[] args = pjp.getArgs();

		String caseName = null;
		if (args!=null && args[0]!=null)
			caseName = args[0].toString();

		return perform(true, caseName, pjp, aProducerId, aCategory, aSubsystem);
	}

	/**
	 * This method is similar to the inner-life of perform, but since we calculate after call, the work is reversed.
	 * @param pjp
	 * @param aProducerId
	 * @param aSubsystem
	 * @param aCategory
	 * @return
	 * @throws Throwable
	 */
	private Object countByReturnValue(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		OnDemandStatsProducer<CounterStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, true, FACTORY, false);

		String statName = null;

		try {
			Object returnValue = pjp.proceed();
			try{
				statName = returnValue == null ? "null" : returnValue.toString();
			}catch(Exception any){
				statName = "exception in return value";
			}
			return returnValue;
		} catch (Throwable e) {
			try{
				statName = e.getClass().getSimpleName()+": "+e.getMessage();
			}catch(Exception any){
				statName = "unreadable exception";
			}
			throw e;
		}finally {
			CounterStats defaultStats = producer.getDefaultStats();
			CounterStats methodStats = null;
			if (statName != null)
				methodStats = producer.getStats(statName);

			defaultStats.inc();
			if (methodStats != null) {
				methodStats.inc();
			}

		}
	}


	/**
	 * Implementation for the @Count pointcut.
	 * @param pjp ProceedingJoinPoint.
	 * @param aProducerId producerId from annotation.
	 * @param aSubsystem subsystem configured by annotation-
	 * @param aCategory category configured by annotation.
	 * @return
	 * @throws Throwable
	 */
	private Object count(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {
		return perform(false,
				getMethodStatName(pjp.getSignature()),
				pjp,
				aProducerId,
				aCategory,
				aSubsystem);
				
	}

    private Object perform(boolean withMethod, String caseName, ProceedingJoinPoint pjp, String aProducerId, String aCategory, String aSubsystem) throws Throwable{
		OnDemandStatsProducer<CounterStats> producer = getProducer(pjp, aProducerId, aCategory, aSubsystem, withMethod, FACTORY, false);

		CounterStats defaultStats = producer.getDefaultStats();
		CounterStats methodStats = null;
		if (caseName != null)
			methodStats = producer.getStats(caseName);

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
