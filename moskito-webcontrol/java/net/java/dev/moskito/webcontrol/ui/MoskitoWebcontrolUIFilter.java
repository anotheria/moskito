package net.java.dev.moskito.webcontrol.ui;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.anotheria.maf.ActionMappingsConfigurator;
import net.anotheria.maf.MAFFilter;
import net.java.dev.moskito.webcontrol.testsupport.DummyFillRepository;
import net.java.dev.moskito.webcontrol.testsupport.DummyFillViewConfig;

import org.apache.log4j.Logger;


public class MoskitoWebcontrolUIFilter extends MAFFilter {

	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";
	
	private static Logger log = Logger.getLogger(MoskitoWebcontrolUIFilter.class);
	
	@Override public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.error("initing filter");
		String pathToImagesParameter = config.getInitParameter("pathToImages");
		if (pathToImagesParameter!=null && pathToImagesParameter.length()>0)
			pathToImages = pathToImagesParameter;
		config.getServletContext().setAttribute("mskPathToImages", pathToImages);
		
		//TODO delete later
		DummyFillRepository.fillRepository();
		DummyFillViewConfig.fillConfig();
	}

 
	@Override public String getProducerId() {
		return "moskitoWC";
	}

	@Override public String getSubsystem() {
		return "monitoring";
	}

	@Override public String getCategory() {
		return "filter";
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators(){
		return Arrays.asList(new ActionMappingsConfigurator[]{ new MoskitoWebcontrolMappingsConfigurator() });
	}
	
}
