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
package net.java.dev.moskitodemo.simpleservice;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleServiceImpl implements ISimpleService, IStatsProducer {
	
	private ServiceStats serviceStats;
	private ServiceStats randomWaitStats;
	private ServiceStats waitForSoLongStats;
	private ServiceStats produceExceptionStats;
	
	private Random rnd;

	SimpleServiceImpl(){
		serviceStats = new ServiceStats("service");
		randomWaitStats = new ServiceStats("randomWait");
		waitForSoLongStats = new ServiceStats("waitForSoLong");
		produceExceptionStats = new ServiceStats("produceException");
		rnd = new Random(System.currentTimeMillis());
	}
	
	public List<IStats> getStats() {
		List<IStats> ret = new ArrayList<IStats>();
		ret.add(serviceStats);
		ret.add(randomWaitStats);
		ret.add(waitForSoLongStats);
		ret.add(produceExceptionStats);
		return ret;
	}
	
	public String getProducerId(){
		return "ISimpleService";
	}

	public void printStats() {
		printStats(null);
	}
	
	public void printStats(String interval) {
		System.out.println("=== STATS FOR "+(interval==null ? "DEFAULT" : interval)+" ====");
		List<IStats> stats = (List<IStats>)getStats();
		for (IStats stat : stats)
			System.out.println(stat.toStatsString(interval));
		System.out.println("=== END OF STATS ===");
	}

	public void randomWait() throws Exception{
		serviceStats.addRequest();
		randomWaitStats.addRequest();
		try{
			long startTime = System.currentTimeMillis();
			Thread.sleep(rnd.nextInt(1000));
			long exTime = System.currentTimeMillis()-startTime;
			serviceStats.addExecutionTime(exTime);
			randomWaitStats.addExecutionTime(exTime);
		}catch (Exception unexpectedError){
			serviceStats.notifyError();
			randomWaitStats.notifyError();
			throw unexpectedError;
		}finally{
			serviceStats.notifyRequestFinished();
			randomWaitStats.notifyRequestFinished();
		}
		
	}

	public void waitForSoLongInMillis(long parameter) throws Exception{
		serviceStats.addRequest();
		waitForSoLongStats.addRequest();
		try{
			long startTime = System.currentTimeMillis();
			Thread.sleep(parameter);
			long exTime = System.currentTimeMillis()-startTime;
			serviceStats.addExecutionTime(exTime);
			waitForSoLongStats.addExecutionTime(exTime);
		}catch (Exception unexpectedError){
			serviceStats.notifyError();
			waitForSoLongStats.notifyError();
			throw unexpectedError;
		}finally{
			serviceStats.notifyRequestFinished();
			waitForSoLongStats.notifyRequestFinished();
		}
		
	}

	public void produceExceptionIfParameterIsEven(int parameter) throws Exception{
		serviceStats.addRequest();
		produceExceptionStats.addRequest();
		try{
			long startTime = System.currentTimeMillis();
			if (parameter/2*2==parameter)
				throw new RuntimeException("Parameter "+parameter+" is even");
			long exTime = System.currentTimeMillis()-startTime;
			serviceStats.addExecutionTime(exTime);
			produceExceptionStats.addExecutionTime(exTime);
		}catch (Exception unexpectedError){
			serviceStats.notifyError();
			produceExceptionStats.notifyError();
			throw unexpectedError;
		}finally{
			serviceStats.notifyRequestFinished();
			produceExceptionStats.notifyRequestFinished();
		}
		
	}

	public String getCategory() {
		return "service";
	}

	public String getSubsystem() {
		return "default";
	}
	
}
