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
package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for requested oriented stats - extending stats objects decorators.
 */
public abstract class RequestOrientedStatsDecorator extends AbstractDecorator {

	/**
	 * Captions.
	 */
	static final String CAPTIONS[] = {
		"Req",
		"Time",
		"CR",
		"MCR",
		"Min",
		"Max",
		"Avg",
		"Last",
		"Err",
		"ERate"
	};

	/**
	 * Short explanations.
	 */
	static final String SHORT_EXPLANATIONS[] = {
		"Number of requests",
		"Time spent in millis (duration)",
		"Concurrent requests",
		"Max concurrent requests",
		"Minimal duration in ms",
		"Maximal duration in ms",
		"Average duration in ms",
		"Last request durations",
		"Number of errors",
		"Error rate in %"
	};

	/**
	 * Explanations.
	 */
	static final String EXPLANATIONS[] = {
		"Total number of requests to a method/interace (in the defined interval or since start, depending on your interval selection).",
		"Total amount of time spent in the method / interface. Although, if the called method is waiting for something to be transported from net or from disk, the value is not equal to spent processor time, this value is usually an important indicator to determine how much some functionality costs.",
		"Number of concurrent requests to a method / interface. This value is not of much interest for time intervals, since it will be any value which was set at the moment of the interval update (and can be even negative since interval updates are fires unsynchronized to prevent performance loss). However, with the default interval (since start) selected it will tell you how many requests are served in the moment.",
		"Max concurrent requests. Unlike the CR value, this value is interesting for intervals; it gives you the info how much parallel load a method / interface suffers.",
		"The minimum amount of time (in milliseconds) spent in a method / interface. For most use-cases you can expect this value to be pretty low or even zero.",
		"The maximum amount of time (in milliseconds) spent in a method / interface.",
		"The average amount of time spent in a method / interface. This method will give you the average duration of a request. This is especially interesting if you have different load through the day, by comparing or drawing for example the 5 mins value of AVG you can determine how well your system handles different load. This value is calculated by simple division time / requests and can be slightly incorrent, if you have very many requests which have short duration. ",
		"The duration of the last request.",
		"Total number of uncaught errors of the method / interface.",
		"The number of uncaught errors as percent of total requests."
	};

	/**
	 * Creates a new decorator object with given name.
	 * @param name name of the object.
	 */
	protected RequestOrientedStatsDecorator(String name){
		super(name, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}

	/**
	 * Creates a new decorator with given name and parameters.
	 * @param name name of the decorator.
	 * @param someCaptions captions.
	 * @param someShortExplanations short explanations.
	 * @param someExplanations explanations.
	 */
	protected RequestOrientedStatsDecorator(String name, String[] someCaptions, String[] someShortExplanations, String[] someExplanations){
		super(name, someCaptions, someShortExplanations, someExplanations);
	}

	@Override public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
		RequestOrientedStats stats = (RequestOrientedStats)statsObject;
		List<StatValueAO> ret = new ArrayList<StatValueAO>(CAPTIONS.length);
		int i = 0;
		long totalRequests = stats.getTotalRequests(interval);
		ret.add(new LongValueAO(CAPTIONS[i++], totalRequests));
		ret.add(new LongValueAO(CAPTIONS[i++], unit.transformNanos(stats.getTotalTime(interval))));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getCurrentRequests(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getMaxCurrentRequests(interval)));
		long min = stats.getMinTime(interval);
		ret.add(new LongValueAO(CAPTIONS[i++], min == Constants.MIN_TIME_DEFAULT ? min : unit.transformNanos(min)));
		long max = stats.getMaxTime(interval);
		ret.add(new LongValueAO(CAPTIONS[i++], max == Constants.MAX_TIME_DEFAULT ? max : unit.transformNanos(max)));
		ret.add(new DoubleValueAO(CAPTIONS[i++], stats.getAverageRequestDuration(interval, unit)));
		ret.add(new LongValueAO(CAPTIONS[i++], unit.transformNanos(stats.getLastRequest(interval))));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getErrors(interval)));
		ret.add(new DoubleValueAO(CAPTIONS[i++], stats.getErrorRate(interval)));
		
		return ret;
	}

	/**
	 * Returns number of total errors for given interval.
	 * @param statsObject target object.
	 * @param interval interval.
	 * @return
	 */
	protected long getTotalErrors(IStats statsObject, String interval){
		return ((RequestOrientedStats)statsObject).getErrors(interval);
	}

}
