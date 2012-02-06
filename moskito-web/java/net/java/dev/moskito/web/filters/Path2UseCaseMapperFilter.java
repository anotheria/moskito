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
package net.java.dev.moskito.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.anotheria.util.StringUtils;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.usecase.UseCase;
import net.java.dev.moskito.core.usecase.UseCaseManager;
import net.java.dev.moskito.core.usecase.UseCaseManagerFactory;

import org.apache.log4j.Logger;

public class Path2UseCaseMapperFilter implements Filter{
	
	private static Logger log;
	static{
		log = Logger.getLogger(Path2UseCaseMapperFilter.class);
	}
	
	private Map<String,String> path2useCaseMap;
	
	private UseCaseManager useCaseManager;

	public void destroy() {
		
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}
		HttpServletRequest req = (HttpServletRequest)sreq;
		String pathInfo = req.getPathInfo();
		String useCaseName = path2useCaseMap.get(pathInfo);
		if (useCaseName==null){
			chain.doFilter(sreq, sres);
			return;
		}
		
		UseCase useCase = useCaseManager.getUseCase(useCaseName);
		if (useCase==null){
			log.warn("UseCase "+useCaseName+" not found, albeit the path mapping is stored for: "+pathInfo);
			chain.doFilter(sreq, sres);
			return;
		}
		
		System.out.println("Found use case: "+useCaseName);
		RunningTraceContainer.startTracedCall(useCaseName);
		try{
			chain.doFilter(sreq, sres);
		}finally{
			CurrentlyTracedCall finishedUseCase = (CurrentlyTracedCall)RunningTraceContainer.endTrace();
			
			System.out.println(finishedUseCase.toDetails());
			System.out.println("Path: "+finishedUseCase.getTrace());
			
			useCase.addExecution(finishedUseCase);
			System.out.println("UseCase: "+useCase);
		}
		
		
		
		
		
	}

	public void init(FilterConfig config) throws ServletException {
		String useCases = config.getInitParameter("usecases");
		useCases = StringUtils.removeChar(useCases,'\r');
		String t[] = StringUtils.tokenize(useCases,'\n');
		path2useCaseMap = new HashMap<String,String>(t.length);
		useCaseManager = UseCaseManagerFactory.getUseCaseManager();
		for (int i=0; i<t.length; i++){
			String usecase = t[i].trim();
			if (usecase.length()==0)
				continue;
			String tt[] = StringUtils.tokenize(usecase,':');
			try{
				String useCaseName = tt[1];
				String useCasePath = tt[0];
				if (useCaseManager.getUseCase(useCaseName)==null)
					useCaseManager.addUseCase(useCaseName);
				path2useCaseMap.put(useCasePath, useCaseName);
				log.info("Added path to usecase mapping: "+useCasePath+" -> "+useCaseName);
			}catch(ArrayIndexOutOfBoundsException e){
				log.error("can't parse \""+usecase+"\", probably missing \":\" as delimiter between path and name.");
			}
			System.out.println("UseCase: "+usecase);
		}
		
	}

}
