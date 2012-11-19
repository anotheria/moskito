package net.anotheria.moskito.annotation.callingAspect;

import net.anotheria.moskito.annotation.Count;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Aspect used to intercept @Count annotated classes.
 *
 * @author lrosenberg, vzhovtiuk
 */
@Aspect
public class CounterAspect extends  AbstractMoskitoAspect{
	
    /**
     * Map with created producers.
     */
    private ConcurrentMap<String, OnDemandStatsProducer<CounterStats>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<CounterStats>>();


    @Around(value = "execution(* *(..)) && (@annotation(method))")
    public Object doProfilingMethod(ProceedingJoinPoint pjp, Count method) throws Throwable {
    	return doProfiling(pjp, method.producerId(), method.subsystem(), method.category());
    }
    	
    @Around(value = "execution(* *.*(..)) && (@within(clazz))")
    public Object doProfilingClass(ProceedingJoinPoint pjp, Count clazz) throws Throwable {
    	return doProfiling(pjp, clazz.producerId(), clazz.subsystem(), clazz.category());
    }

    private Object doProfiling(ProceedingJoinPoint pjp, String aProducerId, String aSubsystem, String aCategory) throws Throwable {

		System.out.println("KIND: "+pjp.getKind());

    	String producerId = null;
    	if (aProducerId!=null && aProducerId.length()>0){
    		producerId = aProducerId;
    	}else{
            producerId = pjp.getSignature().getDeclaringTypeName();
        	try{
        		producerId = producerId.substring(producerId.lastIndexOf('.')+1);
        	}catch(RuntimeException ignored){/* ignored */}
    	}
    	OnDemandStatsProducer<CounterStats> producer = producers.get(producerId);
    	if (producer==null){
    		
    		producer = new OnDemandStatsProducer(producerId, getCategory(aCategory), getSubsystem(aSubsystem), new CounterStatsFactory());
    		OnDemandStatsProducer<CounterStats> p = producers.putIfAbsent(producerId, producer);
    		if (p==null){
    			ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
    		}else{
    			producer = p;
    		}
    	}
    	
    	String caseName = pjp.getSignature().getName();
    	CounterStats defaultStats = producer.getDefaultStats();
		CounterStats methodStats = producer.getStats(caseName);

        final Object[] args = pjp.getArgs();
        final String method = pjp.getSignature().getName();
        defaultStats.inc();
        if (methodStats != null) {
            methodStats.inc();
        }

        Object ret = null;
        try {
            ret = pjp.proceed();
            return ret;
        } catch (InvocationTargetException e) {
            throw e.getCause();
        } finally {
        }
    }

    public void reset() {
    	producers.clear();
    }
}
