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
package net.java.dev.moskito.core.logging;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import net.anotheria.util.Date;
import net.anotheria.util.IdCodeGenerator;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.timing.IUpdateable;
import net.java.dev.moskito.core.timing.UpdateTriggerServiceFactory;


/**
 * The DefaultStatsLogger class providing a logging utility for stats default value. The default value of a IStat is the 
 * value from begin of counting (usually start of VM or first request).
 * @author lrosenberg
 *
 */
public class DefaultStatsLogger implements IUpdateable{
	/**
	 * StatsProducer to which this logger is attached.
	 */
	private IStatsProducer target;
	/**
	 * Output to log to.
	 */
	private ILogOutput output;
	/**
	 * Unique id of the logger.
	 */
	private String id;
	/**
	 * Rate at which an output is performed.
	 */
	private int outputIntervalInSeconds;

	/**
	 * Creates a new DefaultStatsLogger with 60 seconds output interval.
	 * @param aTarget the stat producer to monitor.
	 * @param anOutput output logger.
	 */
	public DefaultStatsLogger(IStatsProducer aTarget, ILogOutput anOutput){
		this(aTarget, anOutput, 60);
	}
	
	/**
	 * Creates a new DefaultStatsLogger.
	 * @param aTarget the stats producer to monitor.
	 * @param anOutput the output logger.
	 * @param anOutputIntervalInSeconds output interval in seconds.
	 */
	public DefaultStatsLogger(IStatsProducer aTarget, ILogOutput anOutput, int anOutputIntervalInSeconds){
		target = aTarget;
		output = anOutput;
		id = IdCodeGenerator.generateCode(10);
		outputIntervalInSeconds = anOutputIntervalInSeconds;
		UpdateTriggerServiceFactory.getUpdateTriggerService().addUpdateable(this, outputIntervalInSeconds);
		output.out("Started default interval logger for "+aTarget.getProducerId()+" / "+id);
	}

	@Override public void update(){
		output.out("===============================================================================");
		output.out("=== SNAPSHOT Interval DEFAULT "+outputIntervalInSeconds+"s, Entity: "+id);
		output.out("=== Timestamp: "+Date.currentDate()+", ServiceId: "+target.getProducerId());
		output.out("===============================================================================");
		try{
			for (IStats stat : target.getStats()){
				output.out(stat.toStatsString());
			}
		}catch(ConcurrentModificationException e){
			Logger.getLogger(DefaultStatsLogger.class).warn("Error during iteration over stats from producer "+target+" ("+target.getClass()+"), concurrent modification of stats detected.");
		}
		
		output.out("===============================================================================");
		output.out("== END: DEFAULT Interval "+outputIntervalInSeconds+"s update, Entity: "+id);
		output.out("===============================================================================");
		
	}

}
