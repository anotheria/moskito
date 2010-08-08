package net.java.dev.moskito.core.predefined;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import static junit.framework.Assert.*;

public class ServiceStatsParallelTestOld {
	private ServiceStats stats = new ServiceStats("test", Constants.getDefaultIntervals());
	
	@Test
	public void testConcurrentRequests(){
		
		int numberOfThreads = 10;
		
		for (int i=0; i<numberOfThreads; i++){
			new TestThread("t"+i, stats).start();
		}
		try{
			Thread.sleep(2500);
		}catch(Exception e){}
		
		
		System.out.println(stats.getTotalRequests());
		System.out.println(stats.getCurrentRequests());
		System.out.println(stats.getMaxCurrentRequests());
		System.out.println(stats.toStatsString());
		System.out.println(TestThread.counter);
		
		
		assertEquals(TestThread.counter.get(), stats.getTotalRequests());
		assertEquals(numberOfThreads, stats.getMaxCurrentRequests());
		
	}

	private static class TestThread extends Thread{
		@SuppressWarnings("unused")
		private String name;
		private ServiceStats stats;
		private static AtomicLong counter = new AtomicLong();
		
		public TestThread(String aName, ServiceStats someStats){
			this.name = aName;
			stats = someStats;
		}
		
		@Override public void run(){
			long timeToEnd = System.currentTimeMillis() + 2000;
			while(System.currentTimeMillis()<timeToEnd){
				stats.addRequest();
				counter.incrementAndGet();
				stats.notifyRequestFinished();
			}
		}
		
		
	}
}
