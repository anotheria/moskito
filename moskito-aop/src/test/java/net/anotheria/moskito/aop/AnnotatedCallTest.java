package net.anotheria.moskito.aop;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class AnnotatedCallTest {
    public static final int ANNOTATED_METHOD_CALLS = 10000;

    @Test
    public void shouldInterceptAnnotatedMethod() throws Exception {


        // given
        AnnotatedMethod annotatedMethod = new AnnotatedMethod();
        // when
        for (int i = 0; i < ANNOTATED_METHOD_CALLS; i++) {
            annotatedMethod.doSomething();
        }
        // then
        IStatsProducer<?> producer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(AnnotatedMethod.class.getSimpleName());
        IStats doSmtgStats = producer.getStats().get(1);
        assertEquals("doSomething", doSmtgStats.getName());
        assertEquals(ANNOTATED_METHOD_CALLS + "", doSmtgStats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS), "Should be 10K calls");
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
        assertEquals(550 + "", doSomeStats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS), "Should be 550 calls");
        IStats doSome2Stats = producer.getStats().get(2);
        assertEquals(750 + "", doSome2Stats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS), "Should be 750 calls");
        IStats doSome3Stats = producer.getStats().get(3);
        assertEquals(1750 + "", doSome3Stats.getValueByNameAsString("TR", null, TimeUnit.MICROSECONDS), "Should be 1750 calls");
    }
}
