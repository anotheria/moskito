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

import static net.java.dev.moskito.core.predefined.Constants.getDefaultIntervals;
import static net.java.dev.moskito.core.predefined.Constants.MAX_TIME_DEFAULT;
import static net.java.dev.moskito.core.predefined.Constants.MIN_TIME_DEFAULT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.producers.CallExecution;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;

/**
 * This is an abstract class for all request oriented stats.
 * @author lrosenberg
 */
public abstract class RequestOrientedStats extends AbstractStats {

	/**
	 * All currently selected intervals.
	 */
	private Interval selectedIntervals[];

	/**
	 * Name of the method.
	 */
	private String methodName;

	/**
	 * The number of total requests to this method / class.
	 */
	private StatValue totalRequests;

	/**
	 * The total time spent in this method / class.
	 */
	private StatValue totalTime;

	/**
	 * Requests in this method / class right now.
	 */
	private StatValue currentRequests;

	/**
	 * Errors occured in this method / class and caught by the surrounding stub/skeleton.
	 */
	private StatValue errors;

	/**
	 * Max current requests in this method / class.
	 */
	private StatValue maxCurrentRequests;

	/**
	 * the last processed request
	 */
	private StatValue lastRequest;

	/**
	 * Min request time
	 */
	private StatValue minTime;

	/**
	 * Max request time
	 */
	private StatValue maxTime;
	
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			new String[]{
				"TR",
				"TT",
				"CR",
				"MCR",
				"ERR",
				"Last",
				"Min",
				"Max",
				"Avg",
			})); 

	/**
	 * Creates a new object with the given method name.
	 * 
	 * @param aMethodName
	 */
	public RequestOrientedStats(String aMethodName) {
		this(aMethodName, getDefaultIntervals());
	}

	/**
	 * Creates a new anonymous stats.
	 */
	public RequestOrientedStats() {
		this("unnamed", getDefaultIntervals());
	}

	public RequestOrientedStats(String aMethodName, Interval[] aSelectedIntervals) {
		methodName = aMethodName;

		Long pattern = Long.valueOf(0);
		selectedIntervals = Arrays.copyOf(aSelectedIntervals, aSelectedIntervals.length);

		totalRequests = StatValueFactory.createStatValue(pattern, "requests", aSelectedIntervals);
		totalTime = StatValueFactory.createStatValue(pattern, "totalTime", aSelectedIntervals);
		currentRequests = StatValueFactory.createStatValue(pattern, "currentRequests", aSelectedIntervals);
		maxCurrentRequests = StatValueFactory.createStatValue(pattern, "maxCurrentRequests", aSelectedIntervals);
		errors = StatValueFactory.createStatValue(pattern, "errors", aSelectedIntervals);
		lastRequest = StatValueFactory.createStatValue(pattern, "last", aSelectedIntervals);
		minTime = StatValueFactory.createStatValue(pattern, "minTime", aSelectedIntervals);
		minTime.setDefaultValueAsLong(MIN_TIME_DEFAULT);
		minTime.reset();

		maxTime = StatValueFactory.createStatValue(pattern, "maxTime", aSelectedIntervals);
		maxTime.setDefaultValueAsLong(MAX_TIME_DEFAULT);
		maxTime.reset();
	}

	/**
	 * Notifies about start of a new request.
	 */
	public void addRequest() {
		totalRequests.increase();
		currentRequests.increase();
		maxCurrentRequests.setValueIfGreaterThanCurrentAsLong(currentRequests.getValueAsLong());
	}

	/**
	 * Notifies that current request leaves the method body.
	 * 
	 */
	public void notifyRequestFinished() {
		currentRequests.decrease();
	}

	/**
	 * Notifies about an uncaught error.
	 */
	public void notifyError() {
		errors.increase();
	}

	/**
	 * Adds messed execution time to the total execution time.
	 * 
	 * @param time
	 */
	public void addExecutionTime(long time) {
		totalTime.increaseByLong(time);
		lastRequest.setValueAsLong(time);
		minTime.setValueIfLesserThanCurrentAsLong(time);
		maxTime.setValueIfGreaterThanCurrentAsLong(time);

	}

	/**
	 * Returns the average time of the request execution duration.
	 * 
	 * @return
	 */
	public double getAverageRequestDuration() {
		return getAverageRequestDuration(null);
	}

	public double getAverageRequestDuration(String intervalName) {
		return totalTime.getValueAsDouble(intervalName) / totalRequests.getValueAsDouble(intervalName);
	}

	public double getAverageRequestDuration(String intervalName, TimeUnit unit) {
		return unit.transformNanos(totalTime.getValueAsLong(intervalName)) / totalRequests.getValueAsDouble(intervalName);
	}

	/**
	 * A string representation of this object. It contains of a set of labels with according values.
	 * The labels are: TR, TT, CR, MCR, ERR and Avg Request Time.<br>
	 * TR - number of total requests.<br>
	 * TT - total execution time.<br>
	 * CR - current requests.<br>
	 * MCR - max concurrent requests.<br>
	 * ERR - number errors.<br>
	 * SL - number of sleeping requests.<br>
	 * MSL - max number of sleeping requests.<br>
	 * Avg Request Time - average request duration for this method.
	 */
	@Override public String toString() {
		return toString(null);
	}

	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder ret = new StringBuilder();
		ret.append(getMethodName());
		ret.append(" TR: ").append(totalRequests.getValueAsLong(intervalName));
		ret.append(" TT: ").append(timeUnit.transformNanos(totalTime.getValueAsLong(intervalName)));
		ret.append(" CR: ").append(currentRequests.getValueAsLong(intervalName));
		ret.append(" MCR: ").append(maxCurrentRequests.getValueAsLong(intervalName));
		ret.append(" ERR: ").append(errors.getValueAsLong(intervalName));
		ret.append(" Last: ").append(timeUnit.transformNanos(lastRequest.getValueAsLong(intervalName)));
		long minTimeTmp = minTime.getValueAsLong(intervalName);
		ret.append(" Min: ");
		if (minTimeTmp == Long.MAX_VALUE)
			ret.append("NoR");
		else
			ret.append(timeUnit.transformNanos(minTimeTmp));
		
		long maxTimeTmp = maxTime.getValueAsLong(intervalName);
		ret.append(" Max: ");
		if (maxTimeTmp == Long.MIN_VALUE)
			ret.append("NoR");
		else
			ret.append(timeUnit.transformNanos(maxTimeTmp));
		
		ret.append(" Avg: ").append(getAverageRequestDuration(intervalName, timeUnit));
		return ret.toString();
	}
	
	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		if (valueName.equals("tr") || valueName.equals("req"))
			return ""+totalRequests.getValueAsLong(intervalName);
		if (valueName.equals("tt")|| valueName.equals("time") || valueName.equals("totaltime"))
			return ""+timeUnit.transformNanos(totalTime.getValueAsLong(intervalName));
		if (valueName.equals("cr"))
			return ""+currentRequests.getValueAsLong(intervalName);
		if (valueName.equals("mcr"))
			return ""+maxCurrentRequests.getValueAsLong(intervalName);
		if (valueName.equals("err"))
			return ""+errors.getValueAsLong(intervalName);
		if (valueName.equals("last"))
			return ""+timeUnit.transformNanos(lastRequest.getValueAsLong(intervalName));
		if (valueName.equals("min"))
			return ""+timeUnit.transformNanos(minTime.getValueAsLong(intervalName));
		if (valueName.equals("max"))
			return ""+timeUnit.transformNanos(maxTime.getValueAsLong(intervalName));
		if (valueName.equals("avg"))
			return ""+getAverageRequestDuration(intervalName, timeUnit);
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

 
	public String toString(String intervalName) {
		String ret = methodName;
		ret += toStatsString(intervalName);
		return ret;
	}

	/**
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param string
	 */
	public void setMethodName(String string) {
		methodName = string;
	}

	/**
	 * @return
	 */
	public long getTotalRequests(String intervalName) {
		return totalRequests.getValueAsLong(intervalName);
	}

	public long getTotalRequests() {
		return totalRequests.getValueAsLong(null);
	}

	/**
	 * @return
	 */
	public long getTotalTime() {
		return getTotalTime(null);
	}

	public long getTotalTime(String intervalName) {
		return totalTime.getValueAsLong(intervalName);
	}

	/**
	 * @return
	 */
	public long getCurrentRequests(String intervalName) {
		return currentRequests.getValueAsLong(intervalName);
	}

	public long getCurrentRequests() {
		return getCurrentRequests(null);
	}

	/**
	 * @return
	 */
	public long getMaxCurrentRequests(String intervalName) {
		return maxCurrentRequests.getValueAsLong(intervalName);
	}

	public long getMaxCurrentRequests() {
		return getMaxCurrentRequests(null);
	}

	/**
	 * @return
	 */
	public long getErrors() {
		return getErrors(null);
	}

	public long getErrors(String intervalName) {
		return errors.getValueAsLong(intervalName);
	}

	public long getLastRequest() {
		return getLastRequest(null);
	}

	public long getLastRequest(String intervalName) {
		return lastRequest.getValueAsLong(intervalName);
	}

	public Interval[] getSelectedIntervals() {
		return Arrays.copyOf(selectedIntervals, selectedIntervals.length);
	}

	public void setSelectedIntervals(Interval[] selectedIntervals) {
		this.selectedIntervals = Arrays.copyOf(selectedIntervals, selectedIntervals.length);
	}

	public long getMinTime(String intervalName) {
		return minTime.getValueAsLong(intervalName);
	}

	public long getMinTime() {
		return minTime.getValueAsLong(null);
	}

	public long getMaxTime(String intervalName) {
		return maxTime.getValueAsLong(intervalName);
	}

	public long getMaxTime() {
		return maxTime.getValueAsLong(null);
	}

	public String getName() {
		return methodName;
	}
	
	public CallExecution createCallExecution(){
		return new RequestCallExecution();
	}
	
	/**
	 * A CallExecutionObject for RequestOrientedStats.
	 * @author lrosenberg
	 */
	private class RequestCallExecution implements CallExecution{

		/**
		 * Starttime of the execution.
		 */
		private long startTime;
		private PathElement currentElement = null;
		private ExistingRunningUseCase runningUseCase = null;
		@Override
		public void finishExecution() {
			long exTime = System.nanoTime() - startTime;
			addExecutionTime(exTime);
			notifyRequestFinished();
			if (currentElement!=null)
				currentElement.setDuration(exTime);
			if (runningUseCase !=null)
				runningUseCase.endPathElement();
	
		}

		@Override
		public void notifyExecutionError() {
			notifyError();
		}

		@Override public void startExecution() {
			startExecution(true);
		}

		@Override
		public void startExecution(boolean recordUseCase) {
			addRequest();
			startTime = System.nanoTime();
			
			if (recordUseCase){
				RunningUseCase aRunningUseCase = RunningUseCaseContainer.getCurrentRunningUseCase();
				runningUseCase = aRunningUseCase.useCaseRunning() ? 
						(ExistingRunningUseCase)aRunningUseCase : null; 
				if (runningUseCase !=null)
					currentElement = runningUseCase.startPathElement(getName());
			}
		}
		
		@Override public void abortExecution() {
			notifyError();
			finishExecution();
		}
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}
	
	@Override
	public boolean isEmpty(String intervalName){
		return getCurrentRequests(intervalName) == 0;
	}

}
