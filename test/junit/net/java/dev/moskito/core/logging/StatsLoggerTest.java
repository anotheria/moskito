package net.java.dev.moskito.core.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

import org.junit.Test;

public class StatsLoggerTest {
	
	
	@Test public void testStatsLogger() throws Exception{
		Interval myInterval = IntervalRegistry.getInstance().getInterval("TESTINTERVAL", 100000);
		Interval[] INTERVALS = new Interval[1]; INTERVALS[0] = myInterval;
		
		OnDemandStatsProducer producer = new OnDemandStatsProducer("MyProducer", "MyCategory", "MySubsystem", new ServiceStatsFactory(INTERVALS));
		ByteArrayLogOutput output = new ByteArrayLogOutput();
		ByteArrayLogOutput output2 = new ByteArrayLogOutput();
		DefaultStatsLogger logger = new DefaultStatsLogger(producer, output, 100000000); //Ensure update will never be called automatically
		IntervalStatsLogger logger2 = new IntervalStatsLogger(producer, myInterval, output2); 
		
		
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
		
		assertEquals("Word TESTINTERVAL shouldn't be present in output", message.indexOf("TESTINTERVAL"), -1);//The word TESTINTERVAL shouldn't occure there
		assertTrue("Word default should be present in output", message.indexOf("default")>-1);//The word default should occure there
		assertTrue(message.indexOf("first")>-1);
		assertTrue(message.indexOf("second")>-1);
		assertTrue(message.indexOf("TT: 123")>-1);
		assertTrue(message.indexOf("TT: 444")>-1);
		assertTrue(message.indexOf("TR: 1")>-1);
		assertTrue(message.indexOf("TR: 2")>-1);
		
		
		//check the second logger
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes(myInterval.getName());
		String message2 = output2.getMessage();
		assertTrue(message2.indexOf("TESTINTERVAL")>-1);//The word TESTINTERVAL should occure there
		assertEquals(message2.indexOf("default"), -1);//The word default shouldn't occure there
		assertTrue(message2.indexOf("first")>-1);
		assertTrue(message2.indexOf("second")>-1);
		assertTrue(message2.indexOf("TT: 123")>-1);
		assertTrue(message2.indexOf("TT: 444")>-1);
		assertTrue(message2.indexOf("TR: 1")>-1);
		assertTrue(message2.indexOf("TR: 2")>-1);
		
	}
}
