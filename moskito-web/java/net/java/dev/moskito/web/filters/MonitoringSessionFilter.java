package net.java.dev.moskito.web.filters;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;
import net.java.dev.moskito.core.usecase.session.IMonitoringSessionManager;
import net.java.dev.moskito.core.usecase.session.MonitoringSession;
import net.java.dev.moskito.core.usecase.session.MonitoringSessionManagerFactory;
import net.java.dev.moskito.core.usecase.session.NoSuchMonitoringSessionException;



public class MonitoringSessionFilter implements Filter{
	
	private static final String SA_SESSION_RECORD = "mskSessionRecord";
	
	private static final String PARAM_SESSION_RECORDING = "mskMonitoringSession";
	private static final String PARAM_VALUE_START = "start";
	private static final String PARAM_VALUE_STOP = "stop";
	
	public static final String PARAM_SESSION_NAME = "mskSessionName";

	private IMonitoringSessionManager monitoringSessionManager;

	public void destroy() {
	}

	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest)sreq;
		processParameters(req);

		HttpSession session = req.getSession(false);
		SessionRecord record = null;
		MonitoringSession msession = null;
		if (session!=null){
				record = (SessionRecord)session.getAttribute(SA_SESSION_RECORD);
				if (record!=null){
					try{
						msession = monitoringSessionManager.getSession(record.getName());
					}catch(NoSuchMonitoringSessionException e){
						msession = monitoringSessionManager.createSession(record.getName());
					}
				}
		}
		
		if (record!=null){
			String url = req.getServletPath();
			if (req.getPathInfo()!=null)
				url += req.getPathInfo();
			if (req.getQueryString()!=null)
				url += "?"+req.getQueryString();
			RunningUseCaseContainer.startUseCase(record.getUseCaseName()+"-"+url);
		}
		try{
			chain.doFilter(sreq, sres);
		}finally{
			if (record!=null){
				RunningUseCase last = RunningUseCaseContainer.endUseCase();
				msession.addUseCase((ExistingRunningUseCase)last);
			}
		}
			
	}
	
	private void processParameters(HttpServletRequest req){
		String command = req.getParameter(PARAM_SESSION_RECORDING);
		String name = req.getParameter(PARAM_SESSION_NAME);
		if (command==null)
			return;
		if (command.equals(PARAM_VALUE_STOP)){
			HttpSession session = req.getSession(false);
			if (session!=null){
				session.removeAttribute(SA_SESSION_RECORD);
			}
			try{
				monitoringSessionManager.getSession(name).setActive(false);
			}catch(NoSuchMonitoringSessionException ignored){}
		}
		
		if (command.equals(PARAM_VALUE_START)){
			HttpSession session = req.getSession();
			if (name==null || name.length()==0)
				name = "unnamed"+System.currentTimeMillis();
			session.setAttribute(SA_SESSION_RECORD, new SessionRecord(name));
			monitoringSessionManager.createSession(name);
		}
	}
	

	public void init(FilterConfig chain) throws ServletException {
		monitoringSessionManager = MonitoringSessionManagerFactory.getMonitoringSessionManager();
	}
	
}

class SessionRecord{
	private String name;
	private AtomicInteger requestCount;
	
	SessionRecord(String aName){
		name = aName;
		requestCount = new AtomicInteger(0);
	}
	
	public int getRequestCount(){
		return requestCount.incrementAndGet();
	}
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return getName()+" - "+requestCount.get();
	}
	
	public String getUseCaseName(){
		return getName()+"-"+getRequestCount();
	}
}