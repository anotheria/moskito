package net.java.dev.moskito.core.dynamic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import net.anotheria.util.IdCodeGenerator;
import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskito.core.producers.IStats;

import org.junit.Test;

public class OnDemandStatsProducerTest {
	@Test public void testLimit() throws Exception {
		OnDemandStatsProducer p = new OnDemandStatsProducer("id", "aCategory", "aSubsystem", new ServiceStatsFactory()){
			
			private boolean limit = false;
			
			public boolean limitForNewEntriesReached(){	
				try{
					return limit;
				}finally{
					limit = !limit;
				}
			}
		};
		
		p.getStats("foo");
		try{
			p.getStats("this_will_throw_an_error");
			fail("Exception expected!");
		}catch(OnDemandStatsProducerException e){
			
		}
		
	}
	
	@Test public void testBasicStuff(){
		String category = IdCodeGenerator.generateCode(20);
		String subsystem = IdCodeGenerator.generateCode(20);
		String id = IdCodeGenerator.generateCode(20);
		
		OnDemandStatsProducer p = new OnDemandStatsProducer(id, category, subsystem, new ServiceStatsFactory());
		assertEquals(id, p.getProducerId());
		assertEquals(category, p.getCategory());
		assertEquals(subsystem, p.getSubsystem());
		assertNotNull(p.toString());
		
		CreationInfo info = p.getCreationInfo();
		assertNotNull(info);
		assertNotNull(info.getStackTrace());
	}

	@Test public void testNull(){
		String id = IdCodeGenerator.generateCode(20);
		
		OnDemandStatsProducer p = new OnDemandStatsProducer(id, null, null, new ServiceStatsFactory());
		assertEquals(id, p.getProducerId());
		assertEquals("default", p.getCategory());
		assertEquals("default", p.getSubsystem());
	}
	
	
	private static final int THREAD_COUNT = 5;
	private static final CountDownLatch startLatch = new CountDownLatch(1);
	private static final CountDownLatch endLatch = new CountDownLatch(THREAD_COUNT);
	private static final int REQUEST_COUNT = 10000;
	private static final int RANDOM_COUNT = 100;
	private static final Random rnd = new Random(System.currentTimeMillis());
	@Test public void testMultipleAccess() throws Exception{
		OnDemandStatsProducer p = new OnDemandStatsProducer("id", "aCategory", "aSubsystem", new ServiceStatsFactory());
		ArrayList<TestThread> threads = new ArrayList<TestThread>();
		for (int i=0; i<THREAD_COUNT; i++){
			TestThread t = new TestThread(p);
			t.start();
			threads.add(t);
		}
		
		long start = System.nanoTime();
		startLatch.countDown();
		endLatch.await();
		long end = System.nanoTime();
		long duration = end - start;
		System.out.println("Tests finished in "+duration/1000/1000+" ms");
		
		long totalRequests = 0;
		for (IStats s : p.getStats()){
			totalRequests += ((ServiceStats)s).getTotalRequests();
		}
		
		System.out.println("Total requests "+totalRequests+" in "+p.getStats().size()+" stats.");
		assertEquals(THREAD_COUNT*REQUEST_COUNT, totalRequests);
		//System.out.println(p.getStats());
	}
	
	private static class TestThread extends Thread{
		
		private OnDemandStatsProducer producer;
		
		public TestThread(OnDemandStatsProducer aProducer) {
			producer = aProducer;
		}
		
		public void run(){
			try{
				startLatch.await();
			}catch(InterruptedException e){}
			for (int i=0; i<REQUEST_COUNT; i++){
				int r = rnd.nextInt(RANDOM_COUNT);
				try{
					ServiceStats stats = (ServiceStats)producer.getStats(""+r);
					stats.addRequest();
				}catch(OnDemandStatsProducerException ignored){
					ignored.printStackTrace();
				}
			}
			endLatch.countDown();
		}
	}
}
