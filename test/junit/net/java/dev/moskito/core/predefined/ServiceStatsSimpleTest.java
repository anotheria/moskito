package net.java.dev.moskito.core.predefined;

import org.junit.Test;
import static junit.framework.Assert.*;

public class ServiceStatsSimpleTest {

	private ServiceStats stats = new ServiceStats("test", Constants.getDefaultIntervals());
	
	@Test
	public void increaseRequestCount(){
		for (int i=0; i<10; i++)
			stats.addRequest();
		assertEquals(stats.getTotalRequests(), 10);
	}
	@Test
	public void increaseErrorCount(){
		for (int i=0; i<10; i++)
			stats.notifyError();
		assertEquals(stats.getErrors(), 10);
	}
	
	@Test
	public void testCurrentRequests(){
		stats.addRequest();
		stats.addRequest();
		assertEquals(2, stats.getCurrentRequests());
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		assertEquals(0, stats.getCurrentRequests());
		assertEquals(2, stats.getMaxCurrentRequests());
	}
	
	@Test
	public void testExecutionTime(){
		stats.addExecutionTime(1000);
		stats.addExecutionTime(1000);
		stats.addExecutionTime(1000);
		assertEquals(3000, stats.getTotalTime());
	}

	
	
}
