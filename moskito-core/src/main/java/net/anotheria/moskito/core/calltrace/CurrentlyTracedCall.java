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
package net.anotheria.moskito.core.calltrace;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.journey.JourneyConfig;
import net.anotheria.moskito.core.producers.IStatsProducer;

import java.io.Serializable;
import java.util.Map;

/**
 * A currently being traced call.
 * @author lrosenberg
 *
 */
public class CurrentlyTracedCall implements TracedCall, Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 3099062979275211864L;
	/**
	 * Name of the call.
	 */
	private String name;
	/**
	 * Root step of the call.
	 */
	private TraceStep root = new TraceStep("");
	/**
	 * Current step in the call.
	 */
	private TraceStep current;
	/**
	 * Creation timestamp.
	 */
	private long created;

	private long createdNanos;

	/**
	 * End timestamp;
	 */
	private long endedNanos;

	/**
	 * Tags.
	 */
	private Map<String, String> tags;

	/**
	 * The journey configuration at start of the journey.
	 */
	private transient JourneyConfig journeyConfig = MoskitoConfigurationHolder.getConfiguration().getJourneyConfig();
	
	/**
	 * Creates a new CurrentlyTracedCall.
	 * @param aName
	 */
	public CurrentlyTracedCall(String aName){
		name = aName;
		current = root;
		created = System.currentTimeMillis();
		createdNanos = System.nanoTime();
	}
	
	@Override public boolean callTraced(){
		return true;
	}
	
	@Override public String toString(){
		return "CurrentlyTracedCall: "+name;
	}
	
	public String toDetails(){
		return toString()+": \n"+root.toDetails(1);
	}

	/**
	 * Creates a new sub step in current call.
	 */
	public TraceStep startStep(String call, IStatsProducer producer){
		TraceStep last = current;
		current = new TraceStep(call, producer);
		//it actually happened in production, we are still investigating why, but this fix should at least prevent the NPE for now.
		if (last!=null)
			last.addChild(current);
		return current;
	}

	/**
	 * Creates a new sub step in current call.
	 */
	@Deprecated
	public TraceStep startStep(String call){
		return startStep(call, null);
	}
	
	public void endStep(){
		current = current.getParent(); 
	}

	public String getTrace(){
		return root.generateTrace();
	}
	
	public String getName(){
		return name;
	}

	public long getCreated() {
		return created;
	}
	
	public TraceStep getRootStep(){
		return root;
	}
	/**
	 * Returns the first step..
	 * @return
	 */
	public TraceStep getFirstStep(){
		return root.getChildren().get(0);
	}
	
	public TraceStep getLastStep(){
		return root.getLastStep();
	}
	
	public int getNumberOfSteps(){
		return root.getNumberOfIncludedSteps();
	}
	
	public TraceStep getCurrentStep(){
		return current;
	}

	public JourneyConfig getJourneyConfig(){
		return journeyConfig;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public void setEnded(){
		endedNanos = System.nanoTime();
	}

	public long getDurationNanos() {
		return endedNanos - createdNanos;
	}
}
