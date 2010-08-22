package net.java.dev.moskito.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;


/**
 * Experimental Code - Do Not Touch
 * @author another
 *
 */
public class TestThreadProducer {
	private ThreadMXBean threadMxBean;
	
	public static void main(String a[]){
		ThreadMXBean bean = ManagementFactory.getThreadMXBean();
		
		for (int i =0; i<10; i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					int a = 1234;					
				}
			}).start();
		}
		
		for (int i =0; i<10; i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						Thread.sleep(1000);
					}catch(Exception e){}
				}
			}).start();
		}

		System.out.println("Total: "+bean.getTotalStartedThreadCount());
		System.out.println("Current: "+bean.getThreadCount());
		System.out.println("Daemon: "+bean.getDaemonThreadCount());
		
		
		
		long ids[] = bean.getAllThreadIds();
		System.out.println(Arrays.toString(ids));
		
		for (int i = 0; i<ids.length; i++){
			long id = ids[i];
			ThreadInfo info = bean.getThreadInfo(id);
			System.out.println("Running thread: "+info);
			
			System.out.println(info.getThreadState());
			
			System.out.println("running "+bean.getThreadCpuTime(id));
			System.out.println("user "+bean.getThreadUserTime(id));
			
			System.out.println("waiting count "+info.getWaitedCount());
			System.out.println("waiting time "+info.getWaitedTime());

			System.out.println("blocked count "+info.getBlockedCount());
			System.out.println("blocked time "+info.getBlockedTime());
		}
	}
}
