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
package net.anotheria.moskito.core.logging;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.util.Date;
import net.anotheria.util.IdCodeGenerator;

/**
 * This helper class logs the stats of a producer with a ILogOutput object at selected interval (as soon as the interval update is fired).
 * @author lrosenberg
 */
public class IntervalStatsLogger implements IIntervalListener {
	/**
	 * The target producer.
	 */
	private IStatsProducer<?> target;
	/**
	 * The target interval
	 */
	private Interval interval;
	/**
	 * The output
	 */
	private ILogOutput output;
	/**
	 * A dynamic random id (created at object creation time) to separate evtl. similar output in the same logfile.
	 */
	private String id;
	
	/**
	 * The timeunit for measurements
	 */
	private TimeUnit unit;
	
	/**
	 * Creates a new IntervalStatsLogger and connects it to the given target, interval and output. 
	 * @param aTarget the producer to log 
	 * @param anInterval the interval for which we are logging
	 * @param anOutput logging output target
	 * @param unit time unit for nanoseconds conversion
	 */
	public IntervalStatsLogger(IStatsProducer aTarget, Interval anInterval, ILogOutput anOutput, TimeUnit unit){
		target = aTarget;
		interval = anInterval;
		output = anOutput;
		interval.addSecondaryIntervalListener(this);
		id = IdCodeGenerator.generateCode(10);
		output.out("Started interval logger for "+interval.getName()+" for "+aTarget.getProducerId()+" / "+id);
	}

	/**
	 * Creates a new IntervalStatsLogger and connects it to the given target, interval and output. Milliseconds are used as intervals. 
	 * @param aTarget the producer to log 
	 * @param anInterval the interval for which we are logging
	 * @param anOutput logging output target
	 */
	public IntervalStatsLogger(IStatsProducer aTarget, Interval anInterval, ILogOutput anOutput){
		this(aTarget, anInterval, anOutput, TimeUnit.MILLISECONDS);
	}
	
	
	/**
	 * Called by the timer. Writes the current status to the logger.
	 */
	@Override public void intervalUpdated(Interval caller) {
		output.out("===============================================================================");
		output.out("=== SNAPSHOT Interval "+interval.getName()+" updated, Entity: "+id);
		output.out("=== Timestamp: "+Date.currentDate()+", ServiceId: "+target.getProducerId());
		output.out("===============================================================================");
		
		for (IStats stat: target.getStats()){
			output.out(stat.toStatsString(interval.getName()));
		}
		
		output.out("===============================================================================");
		output.out("== END: Interval "+interval.getName()+", Entity: "+id);
		output.out("===============================================================================");
		
	}

	public TimeUnit getUnit() {
		return unit;
	}

	public void setUnit(TimeUnit unit) {
		this.unit = unit;
	}

}
