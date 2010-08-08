package net.java.dev.moskito.core.predefined;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
import static junit.framework.Assert.*;

public class ServiceStatsParallelTest {
	private ServiceStats stats = new ServiceStats("test", Constants.getDefaultIntervals());
	
	int numberOfThreads = 5;
	
	final CountDownLatch ready = new CountDownLatch(numberOfThreads);
	final CountDownLatch start = new CountDownLatch(1);
	final CountDownLatch done = new CountDownLatch(numberOfThreads);

	private static AtomicLong counter = new AtomicLong();

	@Test
	public void testConcurrentRequests() throws InterruptedException{
		
		for (int i=0; i<numberOfThreads; i++){
			new TestThread("t"+i, stats).start();
		}
		
		ready.await();
		long startTime = System.nanoTime();
		start.countDown();
		done.await();
		long duration = System.nanoTime() - startTime;
		
		
		/*
		System.out.println(stats.getTotalRequests());
		System.out.println(stats.getCurrentRequests());
		System.out.println(stats.getMaxCurrentRequests());
		System.out.println(stats.toStatsString());
		System.out.println(counter);
		System.out.println("Duration in ms "+(duration/1000000));
		//*/
		
		assertEquals(counter.get(), stats.getTotalRequests());
		assertEquals("Number of threads must be equal to max concurrent requests", numberOfThreads, stats.getMaxCurrentRequests());
		
	}

	private class TestThread extends Thread{
		@SuppressWarnings("unused")
		private String name;
		private ServiceStats stats;
		
		public TestThread(String aName, ServiceStats someStats){
			this.name = aName;
			stats = someStats;
		}
		
		@Override public void run(){
			ready.countDown();
			try{
				start.await();
				executeTest();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}finally{
				done.countDown();
			}
		}
		
		private void executeTest(){
			long timeToEnd = System.currentTimeMillis() + 3000;
			while(System.currentTimeMillis()<timeToEnd){
				stats.addRequest();
				counter.incrementAndGet();
				stats.notifyRequestFinished();
			}
		}
	}
}
