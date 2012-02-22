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
package net.java.dev.moskito.core.calltrace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * A trace step along the monitoring points in a traced call.
 * @author lrosenberg
 *
 */
public class TraceStep implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public TraceStep(String aCall){
		this(aCall, null);
	}
	
	public TraceStep(String aCall, IStatsProducer aProducer){
		call = aCall;
		children = new ArrayList<TraceStep>();
		producer = aProducer;
	}
	
	public String getCall(){
		return call;
	}
	
	public List<TraceStep> getChildren(){
		return children;
	}
	
	public void setParent(TraceStep step){
		parent = step;
	}
	
	public TraceStep getParent(){
		return parent;
	}
	
	public TraceStep getLastStep(){
		if (children==null || children.size()==0)
			return this;
		return children.get(children.size()-1).getLastStep();
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder(getCall()).append(" D: ").append(getDuration()).append(" ns");
		if (isAborted())
			ret.append(" aborted.");
		else
			ret.append(".");
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
	
	public void addChild(TraceStep p){
		children.add(p);
		p.setParent(this);
	}
	
	public String generateTrace(){
		return _generateTrace().toString();
	}
	
	private StringBuilder _generateTrace(){
		StringBuilder b = new StringBuilder(call);
		if (children.size()>0){
			b.append('[');
			for (int i=0; i<children.size(); i++){
				b.append(children.get(i)._generateTrace());
				if (i<children.size()-1)
					b.append(", ");
			}
			b.append(']');
		}
	
		
		return b;
	}

	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}

	public void setAborted(){
		aborted = true;
	}
	
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * Appends additional string to the call description.
	 * @param s
	 */
	public void appendToCall(String s){
		call += s;
	}

	public int getNumberOfIncludedSteps() {
		int sum = 1;
		for (TraceStep s : children )
			sum += s.getNumberOfIncludedSteps();
		return sum;
	}
	
	public IStatsProducer getProducer(){
		return producer;
	}
}
