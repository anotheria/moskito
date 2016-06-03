package net.anotheria.moskito.webui;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskito.webui.plugins.PluginMappingsConfigurator;
import net.anotheria.moskito.webui.shared.api.MoskitoAPIInitializer;
import net.anotheria.moskito.webui.util.VersionUtil;
import net.anotheria.net.util.NetUtils;
import net.anotheria.util.maven.MavenVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.List;

/**
 * MoskitoUI Filter is the main entering point of the Moskito Web User Interface.
 * @author lrosenberg
 *
 */
public class MoskitoUIFilter extends MAFFilter{
	
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(MoskitoUIFilter.class);
	
	/**
	 * Path to images for html-layer image linking.
	 */
	private String pathToImages = "../img/";
	
	@Override public void init(FilterConfig config) throws ServletException {
		super.init(config);
		log.info("Initializing MoSKito WebUI...");
		MoskitoAPIInitializer.initialize();

		try{
			MavenVersion moskitoVersion = VersionUtil.getWebappLibVersion(config.getServletContext(), "moskito-webui");
			MavenVersion appVersion = VersionUtil.getWebappVersion(config.getServletContext());
			config.getServletContext().setAttribute("application_maven_version", appVersion == null ? "?" : appVersion);
			config.getServletContext().setAttribute("moskito_maven_version", moskitoVersion == null ? "?" : moskitoVersion);
			config.getServletContext().setAttribute("moskito_version_string", (moskitoVersion == null || moskitoVersion.getVersion().length()==0)? "unknown" : moskitoVersion.getVersion());
			String computerName = NetUtils.getComputerName();
			config.getServletContext().setAttribute("servername", computerName==null ? "Unknown" : computerName);
		}catch(Exception e){
			log.error("init("+config+ ')', e);
		}

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
		return Arrays.asList(new MoskitoMappingsConfigurator(), new PluginMappingsConfigurator());
	}


	@Override
	protected String getDefaultActionName() {
		return "mskShowAllProducers";
	}

}
