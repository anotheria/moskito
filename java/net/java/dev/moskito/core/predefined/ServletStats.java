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
package net.java.dev.moskito.core.predefined;

import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

public class ServletStats extends RequestOrientedStats{
	/**
	 * Errors occured in this method / class and caught by the surrounding stub/skeleton.
	 */
	private StatValue ioExceptions;
	
	private StatValue servletExceptions;
	
	private StatValue runtimeExceptions;

	/**
	 * Creates a new MethodStats object with the given method name.
	 * @param aMethodName
	 */
	public ServletStats(String aMethodName){
		super(aMethodName);
		initializeMe();
	}
	
	public ServletStats(){
		super();
		initializeMe();
	}
	
	public ServletStats(String aMethodName, Interval[] intervals){
		super(aMethodName, intervals);
		initializeMe();
	}

	private void initializeMe(){
		Long pattern = new Long(0);
		ioExceptions = StatValueFactory.createStatValue(pattern, "ioexceptions", getSelectedIntervals());
		servletExceptions = StatValueFactory.createStatValue(pattern, "servletExceptions", getSelectedIntervals());
		runtimeExceptions = StatValueFactory.createStatValue(pattern, "runtimeExceptions", getSelectedIntervals());
	}
	
	public String toStatsString(String intervalName){
		String ret = "";
		ret += " TR: "+getTotalRequests(intervalName);
		ret += " TT: "+getTotalTime(intervalName);
		ret += " CR: "+getCurrentRequests(intervalName);
		ret += " MCR: "+getMaxCurrentRequests(intervalName);
		ret += " ERR: "+getErrors(intervalName);
		ret += " IOExc: "+ioExceptions.getValueAsLong(intervalName);
		ret += " SEExc: "+servletExceptions.getValueAsLong(intervalName);
		ret += " RTExc: "+runtimeExceptions.getValueAsLong(intervalName);
		ret += " Last: "+getLastRequest(intervalName);
		ret += " Min: "+getMinTime(intervalName);
		ret += " Max: "+getMaxTime(intervalName);
		ret += " Avg: "+getAverageRequestDuration(intervalName);
		return ret;
	}

	public void notifyServletException(){
		servletExceptions.increase();
	}
	
	public void notifyIOException(){
		ioExceptions.increase();
	}
	
	public void notifyRuntimeException(){
		runtimeExceptions.increase();
	}

	public long getIoExceptions() {
		return getIoExceptions(null);
	}

	public long getRuntimeExceptions() {
		return getRuntimeExceptions(null);
	}

	public long getServletExceptions() {
		return getServletExceptions(null);
	}
	
	public long getIoExceptions(String interval) {
		return ioExceptions.getValueAsLong(interval);
	}

	public long getRuntimeExceptions(String interval) {
		return runtimeExceptions.getValueAsLong(interval);
	}

	public long getServletExceptions(String interval) {
		return servletExceptions.getValueAsLong(interval);
	}
}
