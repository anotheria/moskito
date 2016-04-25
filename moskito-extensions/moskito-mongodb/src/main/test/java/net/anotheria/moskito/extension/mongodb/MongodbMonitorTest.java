package net.anotheria.moskito.extension.mongodb;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Test for mongodb monitor
 */
public class MongodbMonitorTest {

    @BeforeClass
    public static void setup(){
        /* to disable builtin producers */
        System.setProperty("JUNITTEST", Boolean.TRUE.toString());
    }

    @Test
    public void testCreation(){
        OnDemandStatsProducer<MongodbStats>producer = new OnDemandStatsProducer<>("MongoMonitor", "Monitor", "db", new MongodbStatsFactory());
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
        assertNotNull(ProducerRegistryFactory.getProducerRegistryInstance().getProducer("MongoMonitor"));
    }
}
