package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.withsubclasses.MonitorWithSubClasses;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author bvanchuhov
 */
public class MonitorWithSubClassesTest {

    @Test
    public void testMonitorWithSubClasses() throws Exception {
        Child child = new Child();

        child.execute();

        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
        assertThat(producerRegistry.getProducer(MoskitoUtils.producerName(Child.class.getName())), notNullValue());
    }

    @MonitorWithSubClasses
    private static class Parent {}

    private static class Child extends Parent {
        public void execute() {}
    }
}
