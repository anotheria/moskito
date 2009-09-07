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
package net.java.dev.moskito.test.plain.atomiclong;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Test {
	
	ConcurrentMap<String,TestRunner> threadMap;
	private Counter counter;
	
	public static final int THREAD_COUNT = 200;
	public static final int OPERATIONS_EACH_THREAD = 10000;
	
	long startTime;
	
	Test(Counter counter){
		this.counter = counter;
	}
	
	public void start(){
		//create threads
		threadMap = new ConcurrentHashMap<String, TestRunner>(THREAD_COUNT);
		for (int i=0; i<THREAD_COUNT; i++){
			TestRunner runner = new TestRunner(this, counter, OPERATIONS_EACH_THREAD);
			threadMap.put(runner.getName(), runner);
		}
		startTime = System.currentTimeMillis();
		Collection<TestRunner> threads = threadMap.values();
		for (Thread t: threads)
			t.start();
	}
	
	private void end(){
		long endTime = System.currentTimeMillis();
		System.out.println("all threads finished. Counter value is "+counter.getValue());
		long expected = THREAD_COUNT* OPERATIONS_EACH_THREAD;
		if (counter.getValue()==expected)
			System.out.println("Test successful.");
		else
			System.out.println("Test FAILED.");
		System.out.println("Execution lasted: "+(endTime-startTime)+" ms.");
	}
	
	public void notifyRunnerFinished(TestRunner aRunner){
		threadMap.remove(aRunner.getName());
		if (threadMap.isEmpty())
			end();
	}
}
