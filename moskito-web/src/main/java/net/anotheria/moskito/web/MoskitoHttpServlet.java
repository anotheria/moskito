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
package net.anotheria.moskito.web;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This servlet class is a base class which can be used for extension to become a monitorable servlet without proxying or any dynamisation.
 * Simply extend it and rename doXYZ Methods with moskitoDoXyz methods. 
 * @author lrosenberg
 *
 */
public class MoskitoHttpServlet extends HttpServlet implements IStatsProducer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -105379295937733815L;
	/**
	 * Stats for the http delete method.
	 */
	private transient ServletStats deleteStats;
	/**
	 * Stats for the http get method.
	 */
	private transient ServletStats getStats;
	/**
	 * Stats for the http head method.
	 */
	private transient ServletStats headStats;
	/**
	 * Stats for the http options method.
	 */
	private transient ServletStats optionsStats;
	/**
	 * Stats for the http post method.
	 */
	private transient ServletStats postStats;
	/**
	 * Stats for the http put method.
	 */
	private transient ServletStats putStats;
	/**
	 * Stats for the http trace method.
	 */
	private transient ServletStats traceStats;
	/**
	 * Stats for the calls of the last modified method.
	 */
	private transient ServletStats lastModifiedStats;
	/**
	 * Cached list with all stats.
	 */
	private transient List<IStats> cachedStatList;
	
	/**
	 * Creates the stats objects. Registers the servlet at the ProducerRegistry. 
	 */
	@Override public void init(ServletConfig config) throws ServletException{
		super.init(config);
		
		getStats          = new ServletStats("get", getMonitoringIntervals());
		postStats         = new ServletStats("post", getMonitoringIntervals());
		putStats          = new ServletStats("put", getMonitoringIntervals());
		headStats         = new ServletStats("head", getMonitoringIntervals());
		optionsStats      = new ServletStats("options", getMonitoringIntervals());
		traceStats        = new ServletStats("trace", getMonitoringIntervals());
		deleteStats       = new ServletStats("delete", getMonitoringIntervals());
		lastModifiedStats = new ServletStats("lastModified", getMonitoringIntervals());
		
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
			deleteStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			deleteStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			deleteStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			deleteStats.notifyError(e);
			throw e;
		}finally{
			deleteStats.notifyRequestFinished();
		}
	}

	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		getStats.addRequest();
		TracedCall aRunningUseCase = RunningTraceContainer.getCurrentlyTracedCall();
		TraceStep currentElement = null;
		CurrentlyTracedCall runningUseCase = aRunningUseCase.callTraced() ?
				(CurrentlyTracedCall)aRunningUseCase : null; 
		if (runningUseCase !=null)
			currentElement = runningUseCase.startStep(new StringBuilder(getProducerId()).append('.').append("doGet").toString(), this, "doGet");
		long startTime = System.nanoTime();

		try{
			moskitoDoGet(req, res);
		}catch(ServletException e){
			getStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			getStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){                
			getStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			getStats.notifyError(e);
			throw e;
		}finally{
			getStats.notifyRequestFinished();
			long executionTime = System.nanoTime()-startTime;
			getStats.addExecutionTime(executionTime);

			if (currentElement!=null)
				currentElement.setDuration(executionTime);
			if (runningUseCase !=null)
				runningUseCase.endStep();
			
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
			headStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			headStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			headStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			headStats.notifyError(e);
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
			optionsStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			optionsStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			optionsStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			optionsStats.notifyError(e);
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
			postStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			postStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			postStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			postStats.notifyError(e);
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
			putStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			putStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			putStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			putStats.notifyError(e);
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
			traceStats.notifyServletException(e);
			throw e;
		}catch(IOException e){
			traceStats.notifyIOException(e);
			throw e;
		}catch(RuntimeException e){
			traceStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			traceStats.notifyError(e);
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
			lastModifiedStats.notifyRuntimeException(e);
			throw e;
		}catch(Error e){
			lastModifiedStats.notifyError(e);
			throw e;
		}finally{
			lastModifiedStats.notifyRequestFinished();
		}
	}

	///////////////// Substitute methods. Override those to get called. ////////////////
	/**
	 * Override this method to react on http delete method.
	 */
	protected void moskitoDoDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doDelete(req, res);
	}

	/**
	 * Override this method to react on http get method.
	 */
	protected void moskitoDoGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doGet(req, res);
	}

	/**
	 * Override this method to react on http head method.
	 */
	protected void moskitoDoHead(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doHead(req, res);
	}

	/**
	 * Override this method to react on http options method.
	 */
	protected void moskitoDoOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doOptions(req, res);
	}

	/**
	 * Override this method to react on http post method.
	 */
	protected void moskitoDoPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doPost(req, res);
	}

	/**
	 * Override this method to react on http put method.
	 */
	protected void moskitoDoPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doPut(req, res);
	}

	/**
	 * Override this method to react on http trace method.
	 */
	protected void moskitoDoTrace(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.doTrace(req, res);
	}

	/**
	 * Override this method to implement lastModfied method.
	 */
	protected long moskitoGetLastModified(HttpServletRequest req) {
		return super.getLastModified(req);
	}


	///////////////////////////////////////////7
	
	/**
	 * Returns the producer id. Override this method if you want a useful name in your logs. Default is class name.
	 */
	@Override public String getProducerId() {
		return getClass().getName();
	}

	@Override public List<IStats> getStats() {
		return cachedStatList;
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
	
	/**
	 * Override this method if you want other monitoring intevals as default (from start, 1,5,15m, 1h and 1d).
	 * @return
	 */
	protected Interval[] getMonitoringIntervals(){
		return Constants.getDefaultIntervals();
	}

	@Override public String getCategory() {
		return "servlet";
	}
	
	@Override public String getSubsystem(){
		return "default";
	}
}
