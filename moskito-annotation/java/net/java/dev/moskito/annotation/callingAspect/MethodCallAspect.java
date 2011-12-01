package net.java.dev.moskito.annotation.callingAspect;

import net.java.dev.moskito.annotation.stat.MethodCallStats;
import net.java.dev.moskito.annotation.stat.MethodCallStatsFactory;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducerException;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Aspect used to intercept  SQL query calls.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:14 PM
 */
@Aspect
public class MethodCallAspect implements IStatsProducer {

    /**
     * List of jdbc calls for interception.
     */
    private static final String METHOD_CALLS = "call(@net.java.dev.moskito.annotation.MonitorMethod * *.*(..)) || call(* (@net.java.dev.moskito.annotation.MonitorClass *).*(..))";
    public static final String PRODUCER_ID = "AnnotatedCalls";
    private final Map<String, MethodCallStats> methodStatsMap;
    private List<IStats> _methodStatsList;
    private MethodCallStatsFactory factory;

    public MethodCallAspect() {
        methodStatsMap = new HashMap<String, MethodCallStats>();
        _methodStatsList = new CopyOnWriteArrayList<IStats>();
        factory = new MethodCallStatsFactory();
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
    }

    @Pointcut(METHOD_CALLS)
    public void methodService() {
    }

    @Around(value = "methodService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        MethodCallStats stats = (MethodCallStats) getStringQueryStats(pjp.getSignature().toShortString());
        long callTime = System.nanoTime();
        try {
            stats.addRequest();
            // stop stopwatch
            return pjp.proceed();
        } catch (Throwable e) {
            stats.notifyError();
            throw e;
        } finally {
            stats.addExecutionTime(System.nanoTime() - callTime);
            stats.notifyRequestFinished();
        }
    }

    public IStats getStringQueryStats(String signature) throws OnDemandStatsProducerException {
        MethodCallStats stringStats;
        synchronized (methodStatsMap) {
            stringStats = methodStatsMap.get(signature);
        }
        if (stringStats == null) {
            if (limitForNewEntriesReached())
                throw new OnDemandStatsProducerException("Limit reached");
            stringStats = (MethodCallStats) factory.createStatsObject(signature);
            synchronized (methodStatsMap) {
                //check whether another thread was faster
                if (methodStatsMap.get(signature) == null) {
                    methodStatsMap.put(signature, stringStats);
                    _methodStatsList.add(stringStats);
                } else {
                    //ok, another thread was faster, we have to throw away our object.
                    stringStats = methodStatsMap.get(signature);
                }
            }
        }

        return stringStats;
    }

    private boolean limitForNewEntriesReached() {
        return _methodStatsList.size() > 10000;
    }

    @Override
    public List<IStats> getStats() {
        return _methodStatsList;
    }

    @Override
    public String getProducerId() {
        return PRODUCER_ID;
    }

    @Override
    public String getCategory() {
        return "default";
    }

    @Override
    public String getSubsystem() {
        return "default";
    }

    public String toString() {
        return getProducerId() + ", Annotated methods: " + Arrays.toString(getStats().toArray()) ;
    }
}
