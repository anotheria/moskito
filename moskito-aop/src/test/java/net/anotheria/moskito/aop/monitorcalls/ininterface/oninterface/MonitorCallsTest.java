package net.anotheria.moskito.aop.monitorcalls.ininterface.oninterface;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
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
        List<IStats> stats1 = producer1.getStats();
        assertEquals(2, stats1.size());
        assertEquals("monitored", stats1.get(1).getName());
        assertEquals("cumulated", stats1.get(0).getName());


        IStatsProducer producer2 = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableForCallsA.class.getSimpleName());
        List<IStats> stats2 = producer2.getStats();
        assertEquals(2, stats2.size());
        assertEquals("monitored", stats2.get(1).getName());
        assertEquals("cumulated", stats2.get(0).getName());

        IStatsProducer producer3 = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableForCallsB.class.getSimpleName());
        assertNull(producer3);
    }
}
