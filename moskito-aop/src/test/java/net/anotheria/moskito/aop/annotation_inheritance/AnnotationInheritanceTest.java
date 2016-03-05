package net.anotheria.moskito.aop.annotation_inheritance;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author bvanchuhov
 */
public class AnnotationInheritanceTest {

    @Test
    public void testAnnotationInheritance() throws Exception {
        MyClass monitirable = new MyClass();

        monitirable.execute();

        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
        assertThat(producerRegistry.getProducer(MoskitoUtils.producerName(MyClass.class.getName())), notNullValue());

        AccumulatorRepository accumulatorRepository = AccumulatorRepository.getInstance();
        assertThat(accumulatorRepository.getByName("Config.A"), notNullValue());
        assertThat(accumulatorRepository.getByName("Config.B"), notNullValue());
        assertThat(accumulatorRepository.getByName("Config.C"), notNullValue());
    }

    @Monitor
    @Accumulates({
            @Accumulate(name = "Config.A", valueName = "A", intervalName = "1m"),
            @Accumulate(name = "Config.B", valueName = "B", intervalName = "5m")
    })
    @Accumulate(name = "Config.C", valueName = "C", intervalName = "1m")
    @Retention(RetentionPolicy.RUNTIME)
    private @interface Config {}

    @Config
    private static class MyClass {
        private void execute() {}
    }
}
