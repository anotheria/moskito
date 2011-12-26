package net.java.dev.moskito.control.monitor.core.history;

import java.util.concurrent.LinkedBlockingDeque;

public class HistoryRepositoryImplTEST {

	private static LinkedBlockingDeque<ThresholdsHistoryBeanTEST> limited = new LinkedBlockingDeque<ThresholdsHistoryBeanTEST>(200);
	private static LinkedBlockingDeque<ThresholdsHistoryBeanTEST> noLimit = new LinkedBlockingDeque<ThresholdsHistoryBeanTEST>();
	static int limit = 200;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int x = 9000000;
		
//		for (ThresholdsHistoryBean b : limited) {
//			System.out.println(limited.);
//		}
		
		System.out.println("Remaining capacity = "+limited.remainingCapacity());
			System.out.println("Total size = "+ limited.size());
		
		long start = System.currentTimeMillis();
 		for (int i=0; i< x; i++) {
 			
 			
 			limited.offerFirst(new ThresholdsHistoryBeanTEST());
 			
 		}
 		System.out.println("Limit time ms="+ (System.currentTimeMillis() - start));

	
 		start = System.currentTimeMillis();
		for (int i=0; i< x; i++) {
			putElement(new ThresholdsHistoryBeanTEST());
		}
		System.out.println("Limit time 2 ms="+ (System.currentTimeMillis() - start));
		

}
	
	static void putElement(ThresholdsHistoryBeanTEST t) {
		boolean limitAchieved = noLimit.size() >= limit;
		noLimit.offerFirst(t);
		   if (limitAchieved) {
			   noLimit.pollLast();
		   }
	}

}
