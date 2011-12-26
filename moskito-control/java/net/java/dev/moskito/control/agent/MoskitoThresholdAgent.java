package net.java.dev.moskito.control.agent;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.java.dev.moskito.control.check.ThresholdCheck;
import net.java.dev.moskito.control.connector.ThresholdConnector;


public class MoskitoThresholdAgent implements MoskitoContorlAgent,Filter  {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		ThresholdConnector tc = new ThresholdConnector();
		
		ThresholdCheck check = (ThresholdCheck) tc.performMoskitoControlChecking();
		try{
			response.getOutputStream().write(check.toJSON().getBytes());
			response.getOutputStream().flush();
		}finally{
			try{
				response.getOutputStream().close();
			}catch(IOException ignore){;}
		}
		
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
