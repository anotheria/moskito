package net.anotheria.moskito.annotation;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DontMonitorAnnotationTest {
	@Test public void testDontMonitorMethod(){
		MonitorableClass monitorable = new MonitorableClass();
		monitorable.monitoredMethod();
		monitorable.notMonitoredMethod();
		
		IStatsProducer producer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(MonitorableClass.class.getSimpleName());
		List<IStats> stats = producer.getStats();
		
		assertEquals(2, stats.size());//only default stats and the monitored method.
		assertEquals("monitoredMethod", stats.get(1).getName());
		assertEquals("cumulated", stats.get(0).getName());
		//notMonitoredMethod is not there.
	}
}
