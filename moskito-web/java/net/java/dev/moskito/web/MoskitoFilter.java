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
	
	/**
	 * Logger instance, available for all subclasses.
	 */
	protected Logger log;
	/**
	 * Parameter name for the init parameter for the limit of dynamic case names (number of names) in the filter config. If the number of cases will exceed this limit,
	 * the new cases will be ignored (to prevent memory leakage).
	 */
	public static final String INIT_PARAM_LIMIT = "limit";
	
	/**
	 * Constant for use-cases which are over limit. In case we gather all request urls and we set a limit of 1000 it may well happen, that we actually have more than the set limit.
	 * In this case it is good to know how many requests those, 'other' urls produce.
	 */
	public static final String OTHER = "-other-";
	
	private FilterStats otherStats = null;
	
	/**
	 * The internal producer instance.
	 */
	private OnDemandStatsProducer onDemandProducer;
	
	protected MoskitoFilter(){
		log = Logger.getLogger(getClass());
	}
	

	@Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

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
			log.info("Couldn't get stats for case : "+caseName+", probably limit reached");
			caseStats = otherStats;
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

	@Override public void init(FilterConfig config) throws ServletException {
		int limit = -1;
		String pLimit = config.getInitParameter(INIT_PARAM_LIMIT);
		if (pLimit!=null)
			try{
				limit = Integer.parseInt(pLimit);
			}catch(NumberFormatException ignored){
				log.warn("couldn't parse limit \""+pLimit+"\", assume -1 aka no limit.");
			}
			
		onDemandProducer = limit == -1 ? 
				new OnDemandStatsProducer(getProducerId(), getCategory(), getSubsystem(), new FilterStatsFactory(getMonitoringIntervals())) :
				new EntryCountLimitedOnDemandStatsProducer(getProducerId(), getCategory(), getSubsystem(), new FilterStatsFactory(getMonitoringIntervals()), limit);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
	
		//force request uri filter to create 'other' stats.
		try{
			if (limit!=-1)
				otherStats = (FilterStats)onDemandProducer.getStats(OTHER);
		}catch(OnDemandStatsProducerException e){
			log.error("Can't create default stats for limit excess", e);
		}
	}
	
	@Override public void destroy(){
		
	}
	
	/**
	 * Overwrite this to provide a name allocation mechanism to make request -> name mapping.
	 * @param req
	 * @param res
	 * @return
	 */
	protected abstract String extractCaseName(ServletRequest req, ServletResponse res );

	/**
	 * Returns the producer id. Override this method if you want a useful name in your logs. Default is class name.
	 */
	protected String getProducerId() {
		String name = getClass().getName();
		int index = name.lastIndexOf('.');
		return index == -1 ? 
				name : name.substring(index+1);
	}

	/**
	 * Overwrite this method to register the filter in a category of your choice. Default is 'filter'.
	 * @return
	 */
	protected String getCategory() {
		return "filter";
	}
	
	/**
	 * Override this to register the filter as specially defined subsystem. Default is 'default'.
	 * @return
	 */
	protected String getSubsystem(){
		return "default";
	}
	
	protected Interval[] getMonitoringIntervals(){
		return Constants.getDefaultIntervals();
	}
}
