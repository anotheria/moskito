package net.java.dev.moskito.core.logging;

import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

import org.junit.Test;
import static org.junit.Assert.*;

public class StatsLoggerTest {
	
	
	@Test public void testDefaultStatsLogger() throws Exception{
		Interval myInterval = IntervalRegistry.getInstance().getInterval("TEST", 100000);
		Interval[] INTERVALS = new Interval[1]; INTERVALS[0] = myInterval;
		
		OnDemandStatsProducer producer = new OnDemandStatsProducer("MyProducer", "MyCategory", "MySubsystem", new ServiceStatsFactory());
		ByteArrayLogOutput output = new ByteArrayLogOutput();
		DefaultStatsLogger logger = new DefaultStatsLogger(producer, output, 100000000); //Ensure update will never be called automatically
		
		
		ServiceStats first = (ServiceStats)producer.getStats("first");
		first.addRequest();
		first.addExecutionTime(123456789); //nanos
		first.notifyRequestFinished();
		
		ServiceStats second = (ServiceStats)producer.getStats("second");
		second.addRequest();
		second.addExecutionTime(111111111); //nanos
		second.notifyRequestFinished();
		second.addRequest();
		second.addExecutionTime(333333333); //nanos
		second.notifyRequestFinished();
		

		//force logger to writeout logs
		logger.update();

		String message = output.getMessage();
		//System.out.println(message);
		
		assertTrue(message.indexOf("first")>-1);
		assertTrue(message.indexOf("second")>-1);
		assertTrue(message.indexOf("TT: 123")>-1);
		assertTrue(message.indexOf("TT: 444")>-1);
		assertTrue(message.indexOf("TR: 1")>-1);
		assertTrue(message.indexOf("TR: 2")>-1);
	}
}
