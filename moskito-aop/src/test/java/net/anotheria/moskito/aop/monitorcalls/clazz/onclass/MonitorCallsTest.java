package net.anotheria.moskito.aop.monitorcalls.clazz.onclass;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class MonitorCallsTest {
    @Test public void testMonitorCallsMethod(){

        MonitorableForCalls monitorableForCalls = new MonitorableForCallsA();
        // should call monitored method
        monitorableForCalls.monitored();

        MonitorableForCallsA monitorableForCallsA = new MonitorableForCallsA();
        // should call monitored method
        monitorableForCallsA.monitored();

        MonitorableForCallsB monitorableForCallsB = new MonitorableForCallsB();
        // shouldn't call monitored method
        monitorableForCallsB.monitored();

        // should call monitored method
        MonitorableForCalls monitorableForCallsB2 = new MonitorableForCallsB();
        monitorableForCallsB2.monitored();


        IStatsProducer producer1 = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableForCalls.class.getSimpleName());
        assertNull(producer1);

        IStatsProducer producer2 = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableForCallsA.class.getSimpleName());
        assertNull(producer2);

        IStatsProducer producer3 = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableForCallsB.class.getSimpleName());
        assertNull(producer3);
    }
}
