package net.java.dev.moskito.web.filters;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.calltrace.TracedCall;
import net.java.dev.moskito.core.journey.Journey;
import net.java.dev.moskito.core.journey.JourneyManager;
import net.java.dev.moskito.core.journey.JourneyManagerFactory;
import net.java.dev.moskito.core.journey.NoSuchJourneyException;



public class JourneyFilter implements Filter{
	
	/**
	 * Session attribute name for session record.
	 */
	private static final String SA_JOURNEY_RECORD = "mskJourneyRecord";
	
	private static final String PARAM_JOURNEY_RECORDING = "mskJourney";
	/**
	 * The value of the parameter for the session monitoring start.
	 */
	private static final String PARAM_VALUE_START = "start";
	/**
	 * The value of the parameter for the session monitoring stop.
	 */
	private static final String PARAM_VALUE_STOP = "stop";
	
	public static final String PARAM_JOURNEY_NAME = "mskJourneyName";

	/**
	 * JourneyManager instance.
	 */
	private JourneyManager journeyManager;

	@Override public void destroy() {
	}

	@Override public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest)sreq;
		processParameters(req);

		HttpSession session = req.getSession(false);
		JourneyRecord record = null;
		Journey journey = null;
		if (session!=null){
				record = (JourneyRecord)session.getAttribute(SA_JOURNEY_RECORD);
				if (record!=null){
					try{
						journey = journeyManager.getJourney(record.getName());
					}catch(NoSuchJourneyException e){
						journey = journeyManager.createJourney(record.getName());
					}
				}
		}
		
		if (record!=null){
			String url = req.getServletPath();
			if (req.getPathInfo()!=null)
				url += req.getPathInfo();
			if (req.getQueryString()!=null)
				url += "?"+req.getQueryString();
			RunningTraceContainer.startTracedCall(record.getUseCaseName()+"-"+url);
		}
		try{
			chain.doFilter(sreq, sres);
		}finally{
			if (record!=null){
				TracedCall last = RunningTraceContainer.endTrace();
				journey.addUseCase((CurrentlyTracedCall)last);
				
				//removes the running use case to cleanup the thread local. Otherwise tomcat will be complaining...
				RunningTraceContainer.cleanup();
			}
		}
			
	}
	
	private void processParameters(HttpServletRequest req){
		
		
		String command = req.getParameter(PARAM_JOURNEY_RECORDING);
		String name = req.getParameter(PARAM_JOURNEY_NAME);
		
		if (command==null)
			return;
		if (command.equals(PARAM_VALUE_STOP)){
			HttpSession session = req.getSession(false);
			if (session!=null){
				session.removeAttribute(SA_JOURNEY_RECORD);
			}
			try{
				journeyManager.getJourney(name).setActive(false);
			}catch(NoSuchJourneyException ignored){}
		}
		
		if (command.equals(PARAM_VALUE_START)){
			HttpSession session = req.getSession();
			if (name==null || name.length()==0)
				name = "unnamed"+System.currentTimeMillis();
			session.setAttribute(SA_JOURNEY_RECORD, new JourneyRecord(name));
			journeyManager.createJourney(name);
		}
	}
	

	@Override public void init(FilterConfig chain) throws ServletException {
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}
	
}

class JourneyRecord implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private AtomicInteger requestCount;
	
	JourneyRecord(String aName){
		name = aName;
		requestCount = new AtomicInteger(0);
	}
	
	public int getRequestCount(){
		return requestCount.incrementAndGet();
	}
	
	public String getName(){
		return name;
	}
	
	@Override public String toString(){
		return getName()+" - "+requestCount.get();
	}
	
	public String getUseCaseName(){
		return getName()+"-"+getRequestCount();
	}
}