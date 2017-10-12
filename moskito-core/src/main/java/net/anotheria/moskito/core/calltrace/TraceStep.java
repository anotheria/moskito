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
import net.anotheria.moskito.core.tracer.Trace;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

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

	public int getNumberOfIncludedSteps() {
		int sum = 1;
		for (TraceStep s : children )
			sum += s.getNumberOfIncludedSteps();
		return sum;
	}

	/**
	 * Returns associated producer.
	 * @return associated producer.
	 */
	public IStatsProducer getProducer(){
		return producer;
	}

	/**
	 * Returns iterator that
	 * iterates over this traced step
	 * and all it substeps tree
	 *
	 * @return iterator to iterate trace step tree
	 */
	public TraceStepsIterator traceStepsIterator(){
		return new TraceStepsIterator();
	}

	/**
	 * Iterates over this trace step and all it child steps.
	 * Tree traversal is made up by depth-first pre-order method
	 * without recursion to prevent stack overflow.
	 */
	public class TraceStepsIterator implements Iterator<TraceStep>, Iterable<TraceStep> {

		/**
		 * Current iteration step item
		 * be returned on next() call
		 */
		private TraceStep current;

		/**
		 * Indicates is any nodes left to iterate
		 */
		private boolean hasNext = true;

		private TraceStepsIterator(){
			current = TraceStep.this;
		}

		/**
		 * Returns child of parent node next to
		 * previous child.
		 *
		 * @param parent trace step parent to search next child
		 * @param child child of first method argument to search next child in list
		 * @return next child in children list or null if there is no next child or
		 * 			first argument is not a parent to second
		 */
		private TraceStep getNextChild(TraceStep parent, TraceStep child){

			for (int i = 0; i < parent.getChildren().size() - 2; i++) {

				if(parent.getChildren().get(i) == child)
					return parent.getChildren().get(i + 1);

			}

			return null;

		}

		/**
		 * Moves over trace steps tree to next element.
		 * Sets `hasNext` property to false if there
		 * is no more unvisited steps left
		 */
		private void moveToNext() {

			// trying to move deeper in tree
			if(!current.getChildren().isEmpty()){
				current = current.getChildren().get(0);
				return;
			}

			// Check in case there is only one element in tree
			if(current == TraceStep.this){
				hasNext = false;
				return;
			}

			// If method execution goes to
			// next lines it means current node is in bottom of the tree.
			// Now going to parent elements for moving in breadth
			TraceStep step = current;
			TraceStep nextChild = null;

			// Moving up in tree to find node
			// where going breadth is possible
			while (nextChild == null) {

				TraceStep parentStep = step.getParent();

				// Trying to get child of parent element next to current step
				nextChild = getNextChild(parentStep, step);

				// If current step parent is root and no root children left
				// traversing is done
				if(nextChild == null && parentStep == TraceStep.this){
					hasNext = false;
					return;
				}

				// If no unvisited nodes found in current parent
				// moving up to tree
				step = parentStep;

			}

			current = nextChild;

		}

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public TraceStep next() {
			TraceStep toReturn = current;
			moveToNext();
			return toReturn;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove");
		}

		@Override
		public Iterator<TraceStep> iterator() {
			return this;
		}

	}

}
