package net.anotheria.moskito.annotation;

import net.anotheria.moskito.annotation.callingAspect.MethodCallAspect;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class AnnotatedCallTest {
    public static final int ANNOTATED_METHOD_CALLS = 10000;
    MethodCallAspect callAspect;

    @Test
    public void shouldInterceptAnnotatedMethod() throws Exception {


        // given
        AnnotatedMethod annotatedMethod = new AnnotatedMethod();
        // when
        for (int i = 0; i < ANNOTATED_METHOD_CALLS; i++) {
            annotatedMethod.doSomething();
        }
        // then
        IStatsProducer<?> producer = (IStatsProducer) ProducerRegistryFactory.getProducerRegistryInstance().getProducer(AnnotatedMethod.class.getSimpleName());
        IStats doSmtgStats = producer.getStats().get(1);
        assertEquals("doSomething", doSmtgStats.getName());
        assertEquals("Should be 10K calls", ANNOTATED_METHOD_CALLS + "", doSmtgStats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS));
    }

    @Test
    public void shouldInterceptAnnotatedClass() throws Exception {

        // given
        AnnotatedClass annotatedClass = new AnnotatedClass();
        // when
        for (int i = 0; i < 550; i++) {
            annotatedClass.doSome();
        }
        for (int i = 0; i < 750; i++) {
            annotatedClass.doSome2();
        }

        for (int i = 0; i < 1750; i++) {
            annotatedClass.doSome3();
        }

        // then
        IStatsProducer<?> producer = (IStatsProducer)  ProducerRegistryFactory.getProducerRegistryInstance().getProducer(AnnotatedClass.class.getSimpleName());
        IStats doSomeStats = producer.getStats().get(1);
        assertEquals("Should be 550 calls", 550 + "", doSomeStats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS));
        IStats doSome2Stats = producer.getStats().get(2);
        assertEquals("Should be 750 calls", 750 + "", doSome2Stats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS));
        IStats doSome3Stats = producer.getStats().get(3);
        assertEquals("Should be 1750 calls", 1750 + "", doSome3Stats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS));
    }
    @After
    public void tearDown() throws Exception {
        if (callAspect != null) {
            callAspect.reset();
        }
    }
}
