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

/**
 * Predefined stats collection for servlets.
 * @author lrosenberg
 *
 */
public class ServletStats extends RequestOrientedStats{
	/**
	 * IOExceptions occured in this method / class and caught by the surrounding stub/skeleton.
	 */
	private StatValue ioExceptions;
	
	/**
	 * Servlet exceptions.
	 */
	private StatValue servletExceptions;
	/**
	 * Runtime exceptions.
	 */
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

	/**
	 * Initializes this object.
	 */
	private void initializeMe(){
		Long pattern = new Long(0);
		ioExceptions = StatValueFactory.createStatValue(pattern, "ioexceptions", getSelectedIntervals());
		servletExceptions = StatValueFactory.createStatValue(pattern, "servletExceptions", getSelectedIntervals());
		runtimeExceptions = StatValueFactory.createStatValue(pattern, "runtimeExceptions", getSelectedIntervals());
	}
	
	@Override public String toStatsString(String intervalName){
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

	/**
	 * Called if a servlet exception has been caught.
	 */
	public void notifyServletException(){
		servletExceptions.increase();
	}
	/**
	 * Called if an io exception has been caught.
	 */
	public void notifyIOException(){
		ioExceptions.increase();
	}
	/**
	 * Called if a runtime exception has been caught.
	 */
	public void notifyRuntimeException(){
		runtimeExceptions.increase();
	}
	/**
	 * Returns the number of io exceptions since start.
	 * @return
	 */
	public long getIoExceptions() {
		return getIoExceptions(null);
	}
	/**
	 * Returns the number of runtime exceptions since start.
	 * @return
	 */
	public long getRuntimeExceptions() {
		return getRuntimeExceptions(null);
	}

	/**
	 * Returns the number of servlet exceptions since start.
	 * @return
	 */
	public long getServletExceptions() {
		return getServletExceptions(null);
	}
	
	/**
	 * Returns the number of io exceptions for given interval.
	 * @param interval
	 * @return
	 */
	public long getIoExceptions(String interval) {
		return ioExceptions.getValueAsLong(interval);
	}

	/**
	 * Returns the number of runtime exceptions for given interval.
	 * @param interval
	 * @return
	 */
	public long getRuntimeExceptions(String interval) {
		return runtimeExceptions.getValueAsLong(interval);
	}

	/**
	 * Returns the number of servlet exceptions for given interval.
	 * @param interval
	 * @return
	 */
	public long getServletExceptions(String interval) {
		return servletExceptions.getValueAsLong(interval);
	}
}
