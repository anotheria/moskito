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
package net.java.dev.moskito.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.calltrace.TraceStep;
import net.java.dev.moskito.core.calltrace.TracedCall;
import net.java.dev.moskito.core.predefined.ActionStats;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




/**
 * A base action for monitorable struts1 actions. Simply implement moskitoExecute method (standart stuts is execute) and your action will be monitored.
 * @author lrosenberg
 */
public abstract class MoskitoAction extends Action implements IStatsProducer {
	/**
	 * The action stats.
	 */
	private volatile ActionStats stats;
	/**
	 * Cached copy of the list.
	 */
	private List<IStats> statsList;

	/**
	 * Creates a new MoskitoAction.
	 */
	protected MoskitoAction() {
		
		stats = new ActionStats("execute", getMonitoringIntervals());
		statsList = new ArrayList<IStats>(1);
		statsList.add(stats);
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	}
	

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)
	 */
	@Override public final ActionForward execute(
		ActionMapping mapping,
		ActionForm bean,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
	
		
		stats.addRequest();
		long startTime = System.nanoTime();
		TracedCall aTracedCall = RunningTraceContainer.getCurrentlyTracedCall();
		TraceStep currentStep = null;
		CurrentlyTracedCall tracedCall = aTracedCall.callTraced() ? 
				(CurrentlyTracedCall)aTracedCall : null; 
		if (tracedCall !=null)
			currentStep = tracedCall.startStep(new StringBuilder(getProducerId()).append('.').append("execute").toString(), this);
		try {
			
			preProcessExecute(mapping, bean, req, res);
			ActionForward forward = moskitoExecute(mapping, bean, req, res);
			postProcessExecute(mapping, bean, req, res);
			return forward;
		}  catch (Exception e) {
			stats.notifyError();
			throw e;
		} finally {
			long duration = System.nanoTime() - startTime;
			stats.addExecutionTime(duration);
			stats.notifyRequestFinished();
			if (currentStep!=null)
				currentStep.setDuration(duration);
			if (tracedCall !=null)
				tracedCall.endStep();
		}
	}
	
	/**
	 * This method allows you to perform some tasks prior to call to the moskitoExecute.
	 * @param mapping
	 * @param af
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	protected void preProcessExecute(
			ActionMapping mapping,
			ActionForm af,
			HttpServletRequest req,
			HttpServletResponse res)
			throws Exception{
		
	}
	
	/**
	 * This method allows you to perform some tasks after the call of the moskitoExecute.
	 * @param mapping
	 * @param af
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	protected void postProcessExecute(
			ActionMapping mapping,
			ActionForm af,
			HttpServletRequest req,
			HttpServletResponse res)
			throws Exception{
		
	}

	/**
	 * Implement your functionality in this action.
	 * @param mapping
	 * @param af
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public abstract ActionForward moskitoExecute(
		ActionMapping mapping,
		ActionForm af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception;

	/**
	 * Override this method if you want to customize monitoring intervals of your action.
	 * @return
	 */
	protected Interval[] getMonitoringIntervals(){
		return Constants.getDefaultIntervals();
	}
	/**
	 * Override this for custom category. Default is 'action'.
	 */
	public String getCategory(){
		return "action";
	}
	/**
	 * Override this for custom subsystem. Default is 'default'.
	 */
	public String getSubsystem(){
		return "default";
	}
	/**
	 * Override this for custom producer id. Default is the name of the class.
	 */
	public String getProducerId(){
		return getClass().getName();
	}

	@Override public List<IStats> getStats(){
		return statsList;
	}
}
