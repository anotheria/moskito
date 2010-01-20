package net.java.dev.moskito.webui;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.maf.MAFFilter;

/**
 * MoskitoUI Filter is the main entering point of the Moskito Web User Interface.
 * @author another
 *
 */
public class MoskitoUIFilter extends MAFFilter{
	
	private static final Logger log = Logger.getLogger(MoskitoUIFilter.class);
	
	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";
	
	@Override public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.info("Initing MoSKito WebUI...");
//		System.out.println("Initing MoSKito WebUI...");
		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter!=null && pathToImagesParameter.length()>0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);
	}

 
	@Override public String getProducerId() {
		return "moskitoUI";
	}

	@Override public String getSubsystem() {
		return "monitoring";
	}

	@Override public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators(){
		return Arrays.asList(new ActionMappingsConfigurator[]{ new MoskitoMappingsConfigurator() });
	}

}
