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

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.producers.AbstractCallExecution;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.producers.CallExecution;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.anotheria.moskito.core.predefined.Constants.getDefaultIntervals;

/**
 * This is an abstract class for all request oriented stats.
 * @author lrosenberg
 */
public abstract class RequestOrientedStats extends AbstractStats {

	/**
	 * All currently selected intervals.
	 */
	private transient Interval selectedIntervals[];

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
	 * Errors occurred in this method / class and caught by the surrounding stub/skeleton.
	 */
	private StatValue errors;

	/**
	 * Max current requests in this method / class.
	 */
	private StatValue maxCurrentRequests;

	/**
	 * the last processed request.
	 */
	private StatValue lastRequest;

	/**
	 * Min request time.
	 */
	private StatValue minTime;

	/**
	 * Max request time.
	 */
	private StatValue maxTime;

	/**
	 * Value names for values collected by this stat.
	 */
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"TR",
			"TT",
			"CR",
			"MCR",
			"ERR",
			"Last",
			"Min",
			"Max",
			"Avg"
	));

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

	/**
	 * Creates a new request oriented stats object with given method name and intervals.
	 * @param aMethodName the method name.
	 * @param aSelectedIntervals supported intervals.
	 */
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
		minTime.setDefaultValueAsLong(Constants.MIN_TIME_DEFAULT);
		minTime.reset();

		maxTime = StatValueFactory.createStatValue(pattern, "maxTime", aSelectedIntervals);
		maxTime.setDefaultValueAsLong(Constants.MAX_TIME_DEFAULT);
		maxTime.reset();

		addStatValues(totalRequests, totalTime, currentRequests, maxCurrentRequests, errors, lastRequest, minTime, maxTime);
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

	/**
	 * Returns the average request duration for the given interval in nanoseconds.
	 * @param intervalName name of the interval.
	 * @return
	 */
	public double getAverageRequestDuration(String intervalName) {
		return totalTime.getValueAsDouble(intervalName) / totalRequests.getValueAsDouble(intervalName);
	}

	/**
	 * Returns the average request duration for the given interval and converted to the given timeunit.
	 * @param intervalName
	 * @param unit
	 * @return
	 */
	public double getAverageRequestDuration(String intervalName, TimeUnit unit) {
		return unit.transformNanos(totalTime.getValueAsLong(intervalName)) / totalRequests.getValueAsDouble(intervalName);
	}

	public double getErrorRate(String intervalName){
		long tr = getTotalRequests(intervalName);
		double errorRate = tr == 0? 0:((double)getErrors(intervalName))/tr;
		return (double)((int)((errorRate * 10000)))/100;
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
		ret.append(" ERate: ").append(getErrorRate(intervalName));
		return ret.toString();
	}
	
	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		if (valueName.equals("tr") || valueName.equals("req"))
			return "" + totalRequests.getValueAsLong(intervalName);
		if (valueName.equals("tt")|| valueName.equals("time") || valueName.equals("totaltime"))
			return "" + timeUnit.transformNanos(totalTime.getValueAsLong(intervalName));
		if (valueName.equals("cr"))
			return "" + currentRequests.getValueAsLong(intervalName);
		if (valueName.equals("mcr"))
			return "" + maxCurrentRequests.getValueAsLong(intervalName);
		if (valueName.equals("err"))
			return "" + errors.getValueAsLong(intervalName);
		if (valueName.equals("last"))
			return "" + timeUnit.transformNanos(lastRequest.getValueAsLong(intervalName));
		if (valueName.equals("min"))
			return "" + timeUnit.transformNanos(minTime.getValueAsLong(intervalName));
		if (valueName.equals("max"))
			return "" + timeUnit.transformNanos(maxTime.getValueAsLong(intervalName));
		if (valueName.equals("avg"))
			return "" + getAverageRequestDuration(intervalName, timeUnit);
		if (valueName.equals("erate") || valueName.equals("errorrate") || valueName.equals("errrate"))
			return "" + getErrorRate(intervalName);

		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}


	/**
	 * Returns a toString representation with values fo the given interval.
	 * @param intervalName the target interval.
	 * @return
	 */
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

	/**
	 * Returns total spent time for the given interal.
	 * @param intervalName the target interval.
	 * @return
	 */
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

	/**
	 * Returns the amount of errors for the given interval.
	 * @param intervalName
	 * @return
	 */
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

	/**
	 * Returns the name of this request (effectively method name).
	 * @return
	 */
	public String getName() {
		return methodName;
	}

	/**
	 * Creates a new call execution object.
	 * @return
	 */
	@Override public CallExecution createCallExecution(){
		return new RequestCallExecution();
	}
	
	/**
	 * A CallExecutionObject for RequestOrientedStats.
	 * @author lrosenberg
	 */
	private class RequestCallExecution extends AbstractCallExecution {

		/**
		 * Start time of the execution.
		 */
		private long startTime;
		/**
		 * Current trace step for journeys.
		 */
		private TraceStep currentStep = null;
		/**
		 * Currently traced call if present.
		 */
		private CurrentlyTracedCall currentlyTracedCall = null;

		/**
		 * Duration so far.
		 */
		private long duration = 0;
		
		@Override
		public void finishExecution(String result) {
			long exTime = System.nanoTime() - startTime;
			duration += exTime;
			addExecutionTime(duration);
			notifyRequestFinished();
			if (currentStep!=null){
				currentStep.setDuration(exTime);
				if (result!=null){
					currentStep.appendToCall(" = "+result);
				}
			}
			if (currentlyTracedCall !=null)
				currentlyTracedCall.endStep();
	
		}

		@Override
		public void notifyExecutionError() {
			notifyError();
		}

		@Override
		public void startExecution(boolean traceCall, String callDescription) {
			addRequest();
			startTime = System.nanoTime();
			
			if (traceCall){
				TracedCall tracedCall = RunningTraceContainer.getCurrentlyTracedCall();
				currentlyTracedCall = tracedCall.callTraced() ? 
						(CurrentlyTracedCall)tracedCall : null;
				if (currentlyTracedCall !=null){
					currentStep = currentlyTracedCall.startStep(callDescription == null ? getName():callDescription);
				}
			}
		}

		@Override
		public void pauseExecution() {
			if (startTime==0)
				return;
			duration += System.nanoTime() - startTime;
			startTime = 0;
		}

		@Override
		public void resumeExecution() {
			//if resume execution is called twice, the first resume should be handled.
			if (startTime!=0)
				pauseExecution();
			startTime = System.nanoTime();
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
