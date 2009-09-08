package net.java.dev.moskito.webui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.predefined.ServletStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;

public class MoskitoUIFIlter implements Filter, IStatsProducer{
	
	private ServletStats getStats;
	
	private List<IStats> cachedStatList;
	
	private String path;

	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		getStats          = new ServletStats("get", getMonitoringIntervals());
		cachedStatList = new ArrayList<IStats>();
		cachedStatList.add(getStats);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres,	FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest)sreq;
		HttpServletResponse res = (HttpServletResponse)sres;
		
		String pathInfo = req.getPathInfo();
		System.out.println("Path info: "+pathInfo);
			
	}

	public final void doPerform(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		getStats.addRequest();
		long startTime = System.nanoTime();
		try{
			// PERFORM
			if (1==1)
				throw new ServletException();
			else
				throw new IOException();
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
			long executionTime = System.nanoTime()-startTime;
			getStats.addExecutionTime(executionTime);
			getStats.notifyRequestFinished();
		}
	}


	public List<IStats> getStats() {
		return (List<IStats>)cachedStatList;
	}

	/**
	 * Override this method to setup custom monitoring intervals.
	 * @return
	 */
	protected Interval[] getMonitoringIntervals(){
		return Constants.DEFAULT_INTERVALS;
	}
	
	/**
	 * Override this operation to perform access control to moskitoUI. Default is yes (true).
	 * @param req the HttpServletRequest
	 * @param res the HttpServletResponse
	 * @return true if the operation is allowed (post or get).
	 */
	protected boolean operationAllowed(HttpServletRequest req, HttpServletResponse res){
		return true;
	}

	public String getProducerId() {
		return "moskitoUI";
	}

	public String getSubsystem() {
		return "monitoring";
	}

	public String getCategory() {
		return "filter";
	}
	
}
