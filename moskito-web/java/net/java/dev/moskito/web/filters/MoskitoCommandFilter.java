package net.java.dev.moskito.web.filters;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.java.dev.moskito.core.command.CommandControllerFactory;

public class MoskitoCommandFilter implements Filter{
	public static final String DEF_PARAM_COMMAND_NAME = "mskCommand";

	private String paramCommandName;
	
	@Override public void destroy() {
	}

	@Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		String command = req.getParameter(paramCommandName);
		if (command==null || command.length()==0){
			chain.doFilter(req, res);
			return;
		}
		
		@SuppressWarnings("unchecked")Map<String, String[]> parameters = req.getParameterMap();
		CommandControllerFactory.getCommandController().startCommand(command, parameters);
		try{
			chain.doFilter(req, res);
		}finally{
			CommandControllerFactory.getCommandController().stopCommand(command, parameters);
		}
	}

	@Override public void init(FilterConfig config) throws ServletException {
		paramCommandName = DEF_PARAM_COMMAND_NAME;
		
	}
	
	
}
