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

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.dynamic.EntryCountLimitedOnDemandStatsProducer;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.dynamic.OnDemandStatsProducerException;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.predefined.FilterStatsFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;

public abstract class MoskitoFilter implements Filter{
	
	protected Logger log;
	
	public static final String INIT_PARAM_LIMIT = "limit";
	
	private OnDemandStatsProducer onDemandProducer;
	
	public MoskitoFilter(){
		log = Logger.getLogger(getClass());
	}
	

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		if (onDemandProducer==null){
			log.error("Access to filter before it's inited!");
			chain.doFilter(req, res);
			return;
		}
		
		FilterStats defaultStats = (FilterStats)onDemandProducer.getDefaultStats();
		FilterStats caseStats = null;
		String caseName = extractCaseName(req, res);
		try{
			if (caseName!=null)
				caseStats = (FilterStats)onDemandProducer.getStats(caseName);
		}catch(OnDemandStatsProducerException e){
			log.warn("Couldn't get stats for case : "+caseName+", probably limit reached");
		}
		
		defaultStats.addRequest();
		if (caseStats!=null)
			caseStats.addRequest();
		
		try{
			long startTime = System.nanoTime();
			chain.doFilter(req, res);
			long exTime = System.nanoTime() - startTime;
			defaultStats.addExecutionTime(exTime);
			if (caseStats!=null)
				caseStats.addExecutionTime(exTime);
		}catch(ServletException e){
			defaultStats.notifyServletException();
			if (caseStats!=null)
				caseStats.notifyServletException();
			throw e;
		}catch(IOException e){
			defaultStats.notifyIOException();
			if (caseStats!=null)
				caseStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			defaultStats.notifyRuntimeException();
			if (caseStats!=null)
				caseStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			defaultStats.notifyError();
			if (caseStats!=null)
				caseStats.notifyError();
			throw e;
		}finally{
			defaultStats.notifyRequestFinished();
			if (caseStats!=null)
				caseStats.notifyRequestFinished();
		}
	}

	public void init(FilterConfig config) throws ServletException {
		int limit = -1;
		String pLimit = config.getInitParameter(INIT_PARAM_LIMIT);
		if (pLimit!=null)
			try{
				limit = Integer.parseInt(pLimit);
			}catch(NumberFormatException ignored){
				log.warn("couldn't parse limit, assume -1",ignored);
			}
			
		onDemandProducer = limit == -1 ? 
				new OnDemandStatsProducer(getProducerId(), getCategory(), getSubsystem(), new FilterStatsFactory(getMonitoringIntervals())) :
				new EntryCountLimitedOnDemandStatsProducer(getProducerId(), getCategory(), getSubsystem(), new FilterStatsFactory(getMonitoringIntervals()), limit);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
		
	}
	
	public void destroy(){
		
	}
	
	protected abstract String extractCaseName(ServletRequest req, ServletResponse res );

	/**
	 * Returns the producer id. Override this method if you want a useful name in your logs. Default is class name.
	 */
	public String getProducerId() {
		return getClass().getName();
	}

	public String getCategory() {
		return "filter";
	}
	
	public String getSubsystem(){
		return "default";
	}
	
	protected Interval[] getMonitoringIntervals(){
		return Constants.DEFAULT_INTERVALS;
	}
}
