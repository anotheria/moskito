package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.CallExecution;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

	//this test tries to reproduce a rounding error i encountered at a live presentation, where
	//values stored by accumulator would differ badly.
	@Test public void testAverageAccumulation(){
		RequestOrientedStats stats = new RequestOrientedStats() {};
		stats.addExecutionTime(100000); //100 ms
		stats.addRequest();
		stats.addExecutionTime(150000); //100 ms
		stats.addRequest();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");
		assertEquals(""+stats.getAverageRequestDuration("1m", TimeUnit.MICROSECONDS), stats.getValueByNameAsString("avg", "1m", TimeUnit.MICROSECONDS));
		assertEquals(""+stats.getAverageRequestDuration("1m", TimeUnit.MILLISECONDS), stats.getValueByNameAsString("avg", "1m", TimeUnit.MILLISECONDS));
		assertEquals("125.0",  stats.getValueByNameAsString("avg", "1m", TimeUnit.MICROSECONDS));

		stats.addExecutionTime(77777777);
		stats.addRequest();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");
		assertEquals(""+stats.getAverageRequestDuration("1m", TimeUnit.MICROSECONDS), stats.getValueByNameAsString("avg", "1m", TimeUnit.MICROSECONDS));
		assertEquals(""+stats.getAverageRequestDuration("1m", TimeUnit.MILLISECONDS), stats.getValueByNameAsString("avg", "1m", TimeUnit.MILLISECONDS));
		assertEquals(""+stats.getAverageRequestDuration("1m", TimeUnit.SECONDS), stats.getValueByNameAsString("avg", "1m", TimeUnit.SECONDS));
		assertEquals("77777.0",  stats.getValueByNameAsString("avg", "1m", TimeUnit.MICROSECONDS));
		assertEquals("77.0",  stats.getValueByNameAsString("avg", "1m", TimeUnit.MILLISECONDS));
		assertEquals("0.0",  stats.getValueByNameAsString("avg", "1m", TimeUnit.SECONDS));

		stats.addExecutionTime(1077777);
		stats.addRequest();
		stats.addExecutionTime(100);
		stats.addRequest();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");
		System.out.println(stats.getAverageRequestDuration("1m", TimeUnit.MICROSECONDS));
		System.out.println(stats.getAverageRequestDuration("1m", TimeUnit.MILLISECONDS));
		System.out.println(stats.getValueByNameAsString("avg", "1m", TimeUnit.MICROSECONDS));
		System.out.println(stats.getValueByNameAsString("avg", "1m", TimeUnit.MILLISECONDS));
		System.out.println(stats.getAverageRequestDuration("1m", TimeUnit.SECONDS));
		System.out.println(stats.getValueByNameAsString("avg", "1m", TimeUnit.SECONDS));

	}

	@Test public void testPauseResumeCallExecution() throws InterruptedException{
		RequestOrientedStats stats = new RequestOrientedStats(){};
		CallExecution execution = stats.createCallExecution();
		execution.startExecution();
		Thread.sleep(20);
		execution.pauseExecution();
		Thread.sleep(20);
		execution.resumeExecution();
		Thread.sleep(20);
		execution.finishExecution();

		assertEquals(1, stats.getTotalRequests());
		//we add some 5 milliseconds on top, but ensure that total duration is below 60ms, which the test as such took.
		assertTrue(1000L*1000*45>stats.getTotalTime());
	}

	@Test public void testDoublePause()  throws InterruptedException {
		RequestOrientedStats stats = new RequestOrientedStats() {};
		CallExecution execution = stats.createCallExecution();
		execution.startExecution();
		Thread.sleep(20);
		execution.pauseExecution();
		execution.pauseExecution();
		Thread.sleep(20);
		execution.pauseExecution();
		execution.resumeExecution();
		Thread.sleep(20);
		execution.finishExecution();

		assertEquals(1, stats.getTotalRequests());
		//we add some 5 milliseconds on top, but ensure that total duration is below 60ms, which the test as such took.
		assertTrue(1000L * 1000 * 45 > stats.getTotalTime());
	}

	@Test public void testDoubleResume()  throws InterruptedException {
		RequestOrientedStats stats = new RequestOrientedStats() {};
		CallExecution execution = stats.createCallExecution();
		execution.startExecution();
		Thread.sleep(20);
		execution.pauseExecution();
		Thread.sleep(20);
		execution.resumeExecution();
		Thread.sleep(20);
		execution.resumeExecution();
		execution.finishExecution();

		assertEquals(1, stats.getTotalRequests());
		//we add some 5 milliseconds on top, but ensure that total duration is below 60ms, which the test as such took.
		assertTrue(1000L * 1000 * 45 > stats.getTotalTime());
	}

}
