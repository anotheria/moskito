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
package net.anotheria.moskitodemo.logging;

import net.anotheria.moskito.core.logging.IntervalStatsLogger;
import net.anotheria.moskito.core.logging.Log4JOutput;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskitodemo.simpleservice.ISimpleService;
import net.anotheria.moskitodemo.simpleservice.test.MultiTest6M;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class TestLog4JLogger extends MultiTest6M {
		
	static{
		BasicConfigurator.configure();
	}
	
	@Override
	protected ISimpleService createService() {
		ISimpleService createdService = super.createService();
		
		//create a new 1minute logger, and connect it to the service and the logoutput.
		new IntervalStatsLogger((IStatsProducer)createdService, DefaultIntervals.ONE_MINUTE, new Log4JOutput(Logger.getLogger(TestLog4JLogger.class)));
		
		return createdService;
	}

	public static void main(String a[]){
		System.out.println("ExceptionPassingTest duration 6 Minutes...");
		System.out.println("You should see stats output in log4j every minute.");
		new TestLog4JLogger().executeTests();
	}
}
