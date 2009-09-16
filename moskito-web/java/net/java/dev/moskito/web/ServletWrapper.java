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

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.ServletStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class ServletWrapper implements Servlet, IStatsProducer{

	private Servlet target;
	private static Class targetClass;
	
	private ServletStats serviceStats;
	private List<IStats> cachedStatList;
	
	private String producerId;

	
	public static void setTargetClass(Class aClass){
		targetClass = aClass;
	}
	
	public ServletWrapper(){
		
		try{
			target = (Servlet)targetClass.newInstance();
			producerId = targetClass.getName()+"_prx";
		}catch(Exception e){
			throw new RuntimeException("Can't create wrapped servlet!");
		}
	}
	
	public void destroy() {
		target.destroy();
	}

	public ServletConfig getServletConfig() {
		return target.getServletConfig();
	}

	public String getServletInfo() {
		return target.getServletInfo();
	}

	public void init(ServletConfig arg0) throws ServletException {
		
		serviceStats = new ServletStats("service", Constants.DEFAULT_INTERVALS);
		cachedStatList = new ArrayList<IStats>(1);
		cachedStatList.add(serviceStats);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
		target.init(arg0);
	}

	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		serviceStats.addRequest();
		try{
			long startTime = System.nanoTime();
			target.service(req, res);
			long executionTime = System.nanoTime()-startTime;
			serviceStats.addExecutionTime(executionTime);
		}catch(ServletException e){
			serviceStats.notifyServletException();
			throw e;
		}catch(IOException e){
			serviceStats.notifyIOException();
			throw e;
		}catch(RuntimeException e){
			serviceStats.notifyRuntimeException();
			throw e;
		}catch(Error e){
			serviceStats.notifyError();
			throw e;
		}finally{
			serviceStats.notifyRequestFinished();
		}
	}

	
	

	public String getCategory() {
		return "servlet";
	}

	public String getSubsystem(){
		return "default";
	}
	
	public String getProducerId(){
		return producerId;
	}
	
	public List<IStats> getStats(){
		return cachedStatList;
	}
}
