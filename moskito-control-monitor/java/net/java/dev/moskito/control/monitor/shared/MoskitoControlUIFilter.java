package net.java.dev.moskito.control.monitor.shared;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;

public class MoskitoControlUIFilter extends MAFFilter {	 

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators() {		
		return Arrays.asList(new ActionMappingsConfigurator[] { new MoskitoControlMappingsConfigurator() });
	}

	@Override
	protected String getDefaultActionName() {		
		return "mscontrol";
	}

	 
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		super.init(arg0);
		System.out.println("=============== \n Moskito-Control UI Filter init. \n ===============");
	}
	
}
