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
package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.errorhandling.BuiltInErrorProducer;

/**
 * Predefined stats collection for servlets.
 * @author lrosenberg
 *
 */
public class ServletStats extends RequestOrientedStats{
	/**
	 * IOExceptions occurred in this method / class and caught by the surrounding stub/skeleton.
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
		Long pattern = Long.valueOf(0);
		ioExceptions = StatValueFactory.createStatValue(pattern, "ioexceptions", getSelectedIntervals());
		servletExceptions = StatValueFactory.createStatValue(pattern, "servletExceptions", getSelectedIntervals());
		runtimeExceptions = StatValueFactory.createStatValue(pattern, "runtimeExceptions", getSelectedIntervals());
		addStatValues(ioExceptions, servletExceptions, runtimeExceptions);
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit){
		StringBuilder ret = new StringBuilder();
		ret.append(" TR: ").append(getTotalRequests(intervalName));
		ret.append(" TT: ").append(timeUnit.transformNanos(getTotalTime(intervalName)));
		ret.append(" CR: ").append(getCurrentRequests(intervalName));
		ret.append(" MCR: ").append(getMaxCurrentRequests(intervalName));
		ret.append(" ERR: ").append(getErrors(intervalName));
		
		ret.append(" IOExc: ").append(ioExceptions.getValueAsLong(intervalName));
		ret.append(" SEExc: ").append(servletExceptions.getValueAsLong(intervalName));
		ret.append(" RTExc: ").append(runtimeExceptions.getValueAsLong(intervalName));
		
		ret.append(" Last: ").append(timeUnit.transformNanos(getLastRequest(intervalName)));
		ret.append(" Min: ").append(timeUnit.transformNanos(getMinTime(intervalName)));
		ret.append(" Max: ").append(timeUnit.transformNanos(getMaxTime(intervalName)));
		ret.append(" Avg: ").append(getAverageRequestDuration(intervalName, timeUnit));
		return ret.toString();

	}

	/**
	 * Called if a servlet exception has been caught.
	 */
	public void notifyServletException(Throwable t){
		servletExceptions.increase();
		BuiltInErrorProducer.getInstance().notifyError(t);

	}
	/**
	 * Called if an io exception has been caught.
	 */
	public void notifyIOException(Throwable t){
		ioExceptions.increase();
		BuiltInErrorProducer.getInstance().notifyError(t);

	}
	/**
	 * Called if a runtime exception has been caught.
	 */
	public void notifyRuntimeException(Throwable t){
		runtimeExceptions.increase();
		BuiltInErrorProducer.getInstance().notifyError(t);

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

	@Override
	public String getValueByNameAsString(String valueName, String intervalName,
			TimeUnit timeUnit) {
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();

		if (valueName.equals("ioexc"))
			return String.valueOf(ioExceptions.getValueAsLong(intervalName));
		if (valueName.equals("seexc"))
			return String.valueOf(servletExceptions.getValueAsLong(intervalName));
		if (valueName.equals("rtexc"))
			return String.valueOf(runtimeExceptions.getValueAsLong(intervalName));
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}
}
