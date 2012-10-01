package net.java.dev.moskitodemo;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;

import org.apache.log4j.Logger;

/**
 * MoskitoUI Filter is the main entering point of the Moskito Web User Interface.
 * @author lrosenberg
 *
 */
public class MoskitoDemoFilter extends MAFFilter{
	
	/**
	 * Logger.
	 */
	private static final Logger log = Logger.getLogger(MoskitoDemoFilter.class);
	
	@Override public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.info("Initing MoSKito Demo...");
		System.out.println("MOSKITO DEMO FILTER");
	}

 
	@Override public String getProducerId() {
		return "demofilter";
	}

	@Override public String getSubsystem() {
		return "demo";
	}

	@Override public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators(){
		return Arrays.asList(new ActionMappingsConfigurator[]{ new DemoMappingsConfiguration() });
	}

}
