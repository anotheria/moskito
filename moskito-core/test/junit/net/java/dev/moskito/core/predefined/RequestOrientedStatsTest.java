package net.java.dev.moskito.core.predefined;

import java.util.Random;

import net.java.dev.moskito.core.producers.CallExecution;

import org.junit.Test;
import static org.junit.Assert.*;

public class RequestOrientedStatsTest {
	@Test public void testCalculation(){
		RequestOrientedStats stats = new RequestOrientedStats(){};
		
		long duration = 0;
		int times = 1000;
		
		Random rnd = new Random(System.currentTimeMillis());
		
		for (int i=0; i<times; i++){
			stats.addRequest();
			long exTime = (long)rnd.nextInt(1000)+100;
			stats.addExecutionTime(exTime);
			duration += exTime;
			stats.notifyRequestFinished();
		}
		
		stats.addRequest(); stats.addExecutionTime(5); stats.notifyRequestFinished();
		stats.addRequest(); stats.addExecutionTime(1200); stats.notifyRequestFinished();
		
		times += 2;
		duration += 1200 + 5;
		
		assertEquals(times, stats.getTotalRequests());
		assertEquals(duration, stats.getTotalTime());
		assertEquals(1, stats.getMaxCurrentRequests());
		assertEquals(0, stats.getCurrentRequests());
		assertEquals(5, stats.getMinTime());
		assertEquals(1200, stats.getMaxTime());
		assertEquals(0, stats.getErrors());
		assertEquals((double)duration/times, stats.getAverageRequestDuration(), 0.0001);
		
		assertNotNull(stats.toStatsString());
		assertNotNull(stats.toString());
	}

	@Test public void testCallExecutor() throws Exception{
		RequestOrientedStats stats = new RequestOrientedStats(){};
		
		int times = 10;
		
		
		for (int i=0; i<times; i++){
			CallExecution call = stats.createCallExecution();
			call.startExecution();
			Thread.sleep(10);
			call.finishExecution();
		}
		
		
		assertEquals(times, stats.getTotalRequests());
		assertEquals(1, stats.getMaxCurrentRequests());
		assertEquals(0, stats.getCurrentRequests());
		assertEquals(0, stats.getErrors());
		
		assertNotNull(stats.toStatsString());
		assertNotNull(stats.toString());
	}
}
