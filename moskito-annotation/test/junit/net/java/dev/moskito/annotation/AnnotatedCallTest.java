package net.java.dev.moskito.annotation;

import net.java.dev.moskito.annotation.callingAspect.MethodCallAspect;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class AnnotatedCallTest {
    @Test
    public void shouldInterceptAnnotatedMethod() throws Exception {
        // given
        AnnotatedMethod annotatedMethod = new AnnotatedMethod();
        // when
        annotatedMethod.doSomething();
        annotatedMethod.doSomething();
        annotatedMethod.doSomething();
        // then
        System.out.println(ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MethodCallAspect.PRODUCER_ID).toString());

    }

    @Test
    public void shouldInterceptAnnotatedClass() throws Exception {
        // given
        AnnotatedClass annotatedClass = new AnnotatedClass();
        // when
        annotatedClass.doSome();
        annotatedClass.doSome2();
        annotatedClass.doSome2();

        // then
        System.out.println(ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MethodCallAspect.PRODUCER_ID).toString());
    }
}
