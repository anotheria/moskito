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

import net.anotheria.moskito.core.producers.IStatsProducer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A trace step along the monitoring points in a traced call.
 * @author lrosenberg
 *
 */
public class TraceStep implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5125788088153070555L;
	/**
	 * Call description, might be a method call or whatever suits best.
	 */
	private String call;
	/**
	 * Sub steps.
	 */
	private List<TraceStep> children;
	/**
	 * Parent step.
	 */
	private TraceStep parent;
	/**
	 * Duration of this step.
	 */
	private long duration;
	/**
	 * If true the execution was aborted (by an exception).
	 */
	private boolean aborted;
	
	/**
	 * The producer that was called in this step.
	 */
	private transient IStatsProducer producer;

	/**
	 * Creates a new trace step.
	 * @param aCall string description of the call.
	 */
	public TraceStep(String aCall){
		this(aCall, null);
	}

	/**
	 * Creates a new trace step.
	 * @param aCall call description.
	 * @param aProducer the executing producer.
	 */
	public TraceStep(String aCall, IStatsProducer aProducer){
		call = aCall;
		children = new ArrayList<>();
		producer = aProducer;
	}
	
	public String getCall(){
		return call;
	}
	
	public List<TraceStep> getChildren(){
		return children;
	}

	/**
	 * Sets the parent step of this step.
	 * @param step
	 */
	public void setParent(TraceStep step){
		parent = step;
	}

	/**
	 * Returns the parent step. Parent step is the caller.
	 * @return the parent step of this step.
	 */
	public TraceStep getParent(){
		return parent;
	}

	/**
	 * Returns the last step in this execution.
	 *
	 * @return this step if it has no chidlren or the last step from the children.
	 */
	public TraceStep getLastStep() {
		TraceStep result = this;
		while (true) {
			if (result.children == null || result.children.size() == 0)
				return result;
			result = result.children.get(result.children.size() - 1);
		}
	}

	@Override
	public String toString(){
		StringBuilder ret = new StringBuilder(getCall()).append(" D: ").append(getDuration()).append(" ns");
		if (isAborted())
			ret.append(" aborted.");
		else
			ret.append('.');
		return ret.toString();
	}
	
	public String toDetails(int ident){
		StringBuilder ret = getIdent(ident).append(this);
		for (TraceStep p : children)
			ret.append('\n').append(p.toDetails(ident+1));
		return ret.toString();
	}
	
	private static StringBuilder getIdent(int ident){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<ident; i++)
			ret.append('\t');
		return ret;
	}

	/**
	 * Adds a new child to this step.
	 * @param p
	 */
	public void addChild(TraceStep p){
		children.add(p);
		p.setParent(this);
	}
	
	public String generateTrace(){
		return internalGenerateTrace().toString();
	}
	
	private StringBuilder internalGenerateTrace(){
		StringBuilder b = new StringBuilder(call);
		if (children.size()>0){
			b.append('[');
			for (int i=0; i<children.size(); i++){
				b.append(children.get(i).internalGenerateTrace());
				if (i<children.size()-1)
					b.append(", ");
			}
			b.append(']');
		}
	
		
		return b;
	}

	/**
	 * Returns true if this step is marked as aborted/exceptional.
	 * @return true if the step has been aborted by an exception.
	 */
	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}

	public void setAborted(){
		aborted = true;
	}

	/**
	 * Returns the total duration of this step.
	 * @return
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Returns the net duration, which means total duration minus the duration of all children.
	 * @return
	 */
	public long getNetDuration(){
		long ret = duration;
		for (TraceStep s : children )
			ret -= s.getDuration();
		return ret;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * Appends additional string to the call description.
	 * @param s string to append.
	 */
	public void appendToCall(String s){
		call += s;
	}

	/**
	 * Returns associated producer.
	 * @return associated producer.
	 */
	public IStatsProducer getProducer(){
		return producer;
	}

	public String toJSON(){
		StringBuilder ret = new StringBuilder();
		ret.append("{");
		ret.append(quote("call")).append(":").append(quote(call)).append(',');
		if (producer!=null) {
			ret.append(quote("producer")).append(":").append(quote(producer.getProducerId())).append(',');
			ret.append(quote("category")).append(":").append(quote(producer.getCategory())).append(',');
			ret.append(quote("subsystem")).append(":").append(quote(producer.getSubsystem())).append(',');
		}
		ret.append(quote("duration")).append(": ").append(duration).append(',');
		ret.append(quote("netDuration")).append(": ").append(getNetDuration()).append(',');

		ret.append(quote("children")).append(": ").append("[");
		for (int i=0; i<getChildren().size(); i++){
			if (i>0) {
				ret.append(',');
			}
			ret.append(getChildren().get(i).toJSON());
		}
		ret.append("]");
		ret.append("}");
		return ret.toString();
	}

	private String quote(String s){
		return "\"" + s + "\"";
	}

}
