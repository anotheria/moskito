package net.java.dev.moskito.util.threadhistory;

import org.junit.BeforeClass;
import org.junit.Test;

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
	}
	
	@Test public void testOnMode() throws InterruptedException{
		ThreadHistoryUtility.INSTANCE.activate();
		for (int i=0; i<THREADCOUNT; i++){
			new Thread(new TestThread()).start();
		}
		
		Thread.currentThread().sleep(1500);

		for (int i=0; i<THREADCOUNT; i++){
			new Thread(new TestThread()).start();
		}
		
		Thread.currentThread().sleep(1500);
	}

	static class TestThread implements Runnable{
		public void run(){
			try{
				Thread.sleep(1100);
			}catch(Exception ignored){}
		}
	}
}
