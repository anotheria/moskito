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
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.predefined.ServletStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.webui.action.ActionForward;
import net.java.dev.moskito.webui.action.ActionMapping;
import net.java.dev.moskito.webui.action.MoskitoUIAction;

public class MoskitoUIFilter implements Filter, IStatsProducer{
	
	private ServletStats getStats;
	
	private List<IStats> cachedStatList;
	
	private String path;
	private String pathToImages = "../img/";
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		getStats          = new FilterStats("cumulated", getMonitoringIntervals());
		cachedStatList = new ArrayList<IStats>();
		cachedStatList.add(getStats);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
		
		path = config.getInitParameter("path");
		if (path==null)
			path = "";
		
		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter!=null && pathToImagesParameter.length()>0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);
	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres,	FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}
		
		HttpServletRequest req = (HttpServletRequest)sreq;
		HttpServletResponse res = (HttpServletResponse)sres;
		
		String servletPath = req.getServletPath();
		if (servletPath==null || servletPath.length()==0)
			servletPath = req.getPathInfo();
		
		if (!(servletPath==null)){
			if (path.length()==0 || servletPath.startsWith(path)){
				doPerform(req, res, servletPath);
				//optionaly allow the chain to run further?
				return;
			}
		}
		
		chain.doFilter(req, res);
			
	}
 
	public final void doPerform(HttpServletRequest req, HttpServletResponse res, String servletPath) throws ServletException, IOException {
		getStats.addRequest();
		long startTime = System.nanoTime();
		try{
			String actionPath = servletPath.substring(path.length());
			ActionMapping mapping = ActionMappings.findMapping(actionPath);
			if (mapping == null){
				res.sendError(404, "Action "+actionPath+" not found.");
				return;
			}
			MoskitoUIAction action;
			try{
				action = ActionFactory.getInstanceOf(mapping.getType());
			}catch(ActionFactoryException e){
				throw new ServletException("Can't instantiate "+mapping.getType()+" for path: "+actionPath+", because: "+e.getMessage(), e);
			}
			
			ActionForward forward = null;
			try{
				action.preProcess(mapping, req, res); 
				forward = action.execute(mapping, req, res);
				action.postProcess(mapping, req, res);
			}catch(Exception e){
				throw new ServletException("Error in processing: "+e.getMessage(), e);
			}
			
			if (forward!=null){
				req.getRequestDispatcher(forward.getPath()).forward(req, res);
			}	
			
			return;
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
