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

import net.java.dev.moskito.core.predefined.ActionStats;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;




/**
 * A base action for monitorable actions.
 * @author lrosenberg
 */
public abstract class MoskitoAction extends Action implements IStatsProducer {

	private volatile ActionStats stats;
	private List<IStats> statsList;

	protected MoskitoAction() {
		
		stats = new ActionStats("execute", getMonitoringIntervals());
		statsList = new ArrayList<IStats>(1);
		statsList.add(stats);
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	}
	

	/**
	 * @see org.apache.struts.action.Action#execute(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)
	 */
	public final ActionForward execute(
		ActionMapping mapping,
		ActionForm bean,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception {
	
		
		stats.addRequest();
		long startTime = System.nanoTime();
		RunningUseCase aRunningUseCase = RunningUseCaseContainer.getCurrentRunningUseCase();
		PathElement currentElement = null;
		ExistingRunningUseCase runningUseCase = aRunningUseCase.useCaseRunning() ? 
				(ExistingRunningUseCase)aRunningUseCase : null; 
		if (runningUseCase !=null)
			currentElement = runningUseCase.startPathElement(new StringBuilder(getProducerId()).append('.').append("execute").toString());
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
			if (currentElement!=null)
				currentElement.setDuration(duration);
			if (runningUseCase !=null)
				runningUseCase.endPathElement();
		}
	}
	
	protected void preProcessExecute(
			ActionMapping mapping,
			ActionForm af,
			HttpServletRequest req,
			HttpServletResponse res)
			throws Exception{
		
	}
	
	protected void postProcessExecute(
			ActionMapping mapping,
			ActionForm af,
			HttpServletRequest req,
			HttpServletResponse res)
			throws Exception{
		
	}

	public abstract ActionForward moskitoExecute(
		ActionMapping mapping,
		ActionForm af,
		HttpServletRequest req,
		HttpServletResponse res)
		throws Exception;

	protected Interval[] getMonitoringIntervals(){
		return Constants.DEFAULT_INTERVALS;
	}
	
	public String getCategory(){
		return "action";
	}
	
	public String getSubsystem(){
		return "default";
	}
	
	public String getProducerId(){
		return getClass().getName();
	}

	public List<IStats> getStats(){
		return statsList;
	}
}
