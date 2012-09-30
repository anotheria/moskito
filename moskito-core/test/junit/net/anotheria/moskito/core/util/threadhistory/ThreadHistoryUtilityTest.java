package net.anotheria.moskito.core.util.threadhistory;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

public class ThreadHistoryUtilityTest {
	
	private static final int THREADCOUNT = 5;

	@BeforeClass public static void setUpdate(){
		ThreadHistoryUtility.INSTANCE.setUpdateInterval(1000L);
	}
	
	@Test public void testOffMode() throws InterruptedException{
		ThreadHistoryUtility.INSTANCE.deactivate();
		for (int i=0; i<THREADCOUNT; i++){
			new Thread(new TestThread()).start();
		}
		
		Thread.currentThread().sleep(1500);
		
		assertEquals(0, ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents().size());
	}
	
	@Test public void testOnMode() throws InterruptedException{
		ThreadHistoryUtility.INSTANCE.activate();
		for (int i=0; i<THREADCOUNT; i++){
			new Thread(new TestThread()).start();
		}
		
		Thread.currentThread().sleep(1500);
		assertTrue(ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents().size()>0);
		int oldsize = ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents().size();
		
		for (int i=0; i<THREADCOUNT+1; i++){
			new Thread(new TestThread()).start();
		}
		
		Thread.currentThread().sleep(1500);
		assertTrue(oldsize<ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents().size());
	}

	static class TestThread implements Runnable{
		public void run(){
			try{
				Thread.sleep(1100);
			}catch(Exception ignored){}
		}
	}
}
