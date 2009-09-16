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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.ServletStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;


public class MoskitoHttpServlet extends HttpServlet implements IStatsProducer{
	
	private ServletStats deleteStats;
	private ServletStats getStats;
	private ServletStats headStats;
	private ServletStats optionsStats;
	private ServletStats postStats;
	private ServletStats putStats;
	private ServletStats traceStats;
	private ServletStats lastModifiedStats;
	
	private List<IStats> cachedStatList;
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		
		getStats          = new ServletStats("get", getMonitoringIntervals());
		postStats         = new ServletStats("post", getMonitoringIntervals());
		putStats          = new ServletStats("put", getMonitoringIntervals());
		headStats         = new ServletStats("head", getMonitoringIntervals());
		optionsStats      = new ServletStats("options", getMonitoringIntervals());
		traceStats        = new ServletStats("trace", getMonitoringIntervals());
		deleteStats       = new ServletStats("delete", getMonitoringIntervals());
		lastModifiedStats = new ServletStats("lastModified", getMonitoringIntervals());
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	}
	
	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		deleteStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoDelete(req, res);
			long executionTime = System.nanoTime()-startTime;
			deleteStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			deleteStats.notifyServletException();
			throw e;
		}catch(IOException e){
			deleteStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			deleteStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			deleteStats.notifyError();
			throw e;
		}finally{
			deleteStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		getStats.addRequest();
		RunningUseCase aRunningUseCase = RunningUseCaseContainer.getCurrentRunningUseCase();
		PathElement currentElement = null;
		ExistingRunningUseCase runningUseCase = aRunningUseCase.useCaseRunning() ? 
				(ExistingRunningUseCase)aRunningUseCase : null; 
		if (runningUseCase !=null)
			currentElement = runningUseCase.startPathElement(new StringBuilder(getProducerId()).append('.').append("doGet").toString());
		long startTime = System.nanoTime();

		try{
			moskitoDoGet(req, res);
		}catch(ServletException e){
			getStats.notifyServletException();
			throw e;
		}catch(IOException e){
			getStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			getStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			getStats.notifyError();
			throw e;
		}finally{
			getStats.notifyRequestFinished();
			long executionTime = System.nanoTime()-startTime;
			getStats.addExecutionTime(executionTime);

			if (currentElement!=null)
				currentElement.setDuration(executionTime);
			if (runningUseCase !=null)
				runningUseCase.endPathElement();
			
		}
	}

	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		headStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoHead(req, res);
			long executionTime = System.nanoTime()-startTime;
			headStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			headStats.notifyServletException();
			throw e;
		}catch(IOException e){
			headStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			headStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			headStats.notifyError();
			throw e;
		}finally{
			headStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		optionsStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoOptions(req, res);
			long executionTime = System.nanoTime()-startTime;
			optionsStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			optionsStats.notifyServletException();
			throw e;
		}catch(IOException e){
			optionsStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			optionsStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			optionsStats.notifyError();
			throw e;
		}finally{
			optionsStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		postStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoPost(req, res);
			long executionTime = System.nanoTime()-startTime;
			postStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			postStats.notifyServletException();
			throw e;
		}catch(IOException e){
			postStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			postStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			postStats.notifyError();
			throw e;
		}finally{
			postStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		putStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoPut(req, res);
			long executionTime = System.nanoTime()-startTime;
			putStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			putStats.notifyServletException();
			throw e;
		}catch(IOException e){
			putStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			putStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			putStats.notifyError();
			throw e;
		}finally{
			putStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		traceStats.addRequest();
		try{
			long startTime = System.nanoTime();
			moskitoDoTrace(req, res);
			long executionTime = System.nanoTime()-startTime;
			traceStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			traceStats.notifyServletException();
			throw e;
		}catch(IOException e){
			traceStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			traceStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			traceStats.notifyError();
			throw e;
		}finally{
			traceStats.notifyRequestFinished();
		}
	}

	@Override
	protected final long getLastModified(HttpServletRequest req) {
		lastModifiedStats.addRequest();
		try{
			long startTime = System.nanoTime();
			long retValue = moskitoGetLastModified(req);
			long executionTime = System.nanoTime()-startTime;
			lastModifiedStats.addExecutionTime(executionTime);
			return retValue;
		}catch(RuntimeException e){
			lastModifiedStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			lastModifiedStats.notifyError();
			throw e;
		}finally{
			lastModifiedStats.notifyRequestFinished();
		}
	}

	///////////////// Substitute methods. Override those to get called. ////////////////
	protected void moskitoDoDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doDelete(req, res);
	}

	protected void moskitoDoGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
	}

	protected void moskitoDoHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doHead(req, res);
	}

	protected void moskitoDoOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doOptions(req, res);
	}

	protected void moskitoDoPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doPost(req, res);
	}

	protected void moskitoDoPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doPut(req, res);
	}

	protected void moskitoDoTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doTrace(req, res);
	}

	protected long moskitoGetLastModified(HttpServletRequest req) {
		return super.getLastModified(req);
	}


	///////////////////////////////////////////7
	
	/**
	 * Returns the producer id. Override this method if you want a useful name in your logs. Default is class name.
	 */
	public String getProducerId() {
		return getClass().getName();
	}

	public List<IStats> getStats() {
		if (cachedStatList==null){
			synchronized(this){
				if (cachedStatList==null){
					cachedStatList = new ArrayList<IStats>(useShortStatList()? 2 : 8);
					cachedStatList.add(getStats);
					cachedStatList.add(postStats);
					if (!useShortStatList()){
						cachedStatList.add(deleteStats);
						cachedStatList.add(headStats);
						cachedStatList.add(optionsStats);
						cachedStatList.add(putStats);
						cachedStatList.add(traceStats);
						cachedStatList.add(lastModifiedStats);
					}
				}
			}
		}
		return (List<IStats>)cachedStatList;
	}
	
	/**
	 * Override this method and return true, if you want the short list of stats (doGet and doPost only). Return 
	 * false if you want the full list, containing get, post, lastModified, put, trace, options, head and delete.
	 * Default is true.
	 * @return
	 */
	protected boolean useShortStatList(){
		return true;
	}
	
	protected Interval[] getMonitoringIntervals(){
		return Constants.DEFAULT_INTERVALS;
	}

	public String getCategory() {
		return "servlet";
	}
	
	public String getSubsystem(){
		return "default";
	}
	
	////////////////////////////
	

}
