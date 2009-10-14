/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskitodemo.simpleservice.test;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskitodemo.simpleservice.ISimpleService;
import net.java.dev.moskitodemo.simpleservice.SimpleServiceFactory;

public abstract class AbstractMultiTestWithDuration {
	
	private long duration;
	private ISimpleService service;
	
	public static final String[] INTERVALS_TO_CHECK = {
		null,
		"1m",
		"5m"
	};
	
	protected ISimpleService createService(){
		return SimpleServiceFactory.createSimpleService();
	}
	
	protected AbstractMultiTestWithDuration(long aDuration){
		duration = aDuration;
		service = createService();
	}
	
	protected void executeTests(){
		List<TestRunner> runners = new ArrayList<TestRunner>();
		runners.add(new TestRunner(new TestControlledWait()));
		runners.add(new TestRunner(new TestRandomWait()));
		runners.add(new TestRunner(new TestExceptionProduction()));
		for (TestRunner runner : runners)
			runner.start();
		System.out.println("Tests started ... ");
		
		long sleepStarted = System.currentTimeMillis();
		try{
			Thread.sleep(duration);
		}catch(InterruptedException ignored){}
		long sleepEnded = System.currentTimeMillis();
		long realSleepDuration = sleepEnded - sleepStarted;
		
		System.out.println("stoping, slept "+realSleepDuration+" ms, please wait further 12 seconds.");
		//stop tests
		for (TestRunner runner : runners)
			runner.setRunning(false);
		
		sleepStarted = System.currentTimeMillis();
		try{
			Thread.sleep(12000);
		}catch(InterruptedException ignored){}
		sleepEnded = System.currentTimeMillis();
		realSleepDuration += (sleepEnded-sleepStarted);
		System.out.println("Finished, total sleep time: "+realSleepDuration);
		for (String interval : INTERVALS_TO_CHECK){
			System.out.println("\tPrinting stats for interval: "+(interval == null ? "DEFAULT (Since start)" : interval));
			service.printStats(interval);
		}
		
	}
	
	class TestRunner extends Thread{
		private AbstractTest myTestCase;
		private boolean running;
		
		public TestRunner(AbstractTest aTestCase){
			myTestCase = aTestCase;
		}
		
		public void run(){
			setRunning(true);
			long testRunCount = 0;
			long exceptionCount = 0;
			while(isRunning()){
				testRunCount++;
				
				try{
					myTestCase.test(service, false);
				}catch(Exception e){
					exceptionCount++;
				}
			}
			System.out.println("Performed "+testRunCount+" test runs on "+myTestCase.getClass().getName()+", caught "+exceptionCount+" exceptions");
		}
		
		private synchronized boolean isRunning(){
			return running;
		}
		
		private synchronized void setRunning(boolean value){
			running = value;
		}
	}
}
