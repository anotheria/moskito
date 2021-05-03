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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
	private final String name;
	/**
	 * Root step of the call.
	 */
	private final TraceStep root = new TraceStep("");
	/**
	 * Current step in the call.
	 */
	private TraceStep current;
	/**
	 * Creation timestamp.
	 */
	private final long created;

	private final long createdNanos;

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
	private final transient JourneyConfig journeyConfig = MoskitoConfigurationHolder.getConfiguration().getJourneyConfig();
	
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
		return "CurrentlyTracedCall: "+name+", started: "+createdNanos+", ended: "+endedNanos+", durationNanos: "+getDurationNanos();
	}
	
	/**
	 * Creates a new sub step in current call.
	 */
	public TraceStep startStep(String call, IStatsProducer producer, String methodName){
		TraceStep last = current;
		current = new TraceStep(call, producer, methodName);
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
		return startStep(call, null, null);
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
		getRootStep().setDuration(getDurationNanos());
	}

	public long getDurationNanos() {
		return endedNanos - createdNanos;
	}

	/**
	 * Calculates the number of steps.
	 *
	 * @return the number of steps
	 */
	public int getNumberOfSteps() {
		int result = 0;

		final Queue<TraceStep> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			final TraceStep currentTraceStep = queue.poll();

			result++;

			final List<TraceStep> children = currentTraceStep.getChildren();
			if (!children.isEmpty()) {
				queue.addAll(children);
			}
		}

		return result;
	}

	/**
	 * This is a debug method used to dump out the call into stdout.
	 */
	public void dumpOut(){
		System.out.println(this.toString());
		TraceStep root = getRootStep();
		dumpOut(root, 1);
	}

	private void dumpOut(TraceStep step, int ident){
		StringBuilder prfx = new StringBuilder(); for (int i=0; i<ident;i++)prfx.append(' ');
		String prefix = prfx.toString();

		System.out.println(prefix+" "+step.toString());
		for (TraceStep child : step.getChildren()){
			dumpOut(child, ident+1);
		}
	}


}
