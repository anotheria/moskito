package net.anotheria.moskito.webcontrol.shared;

import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application entrance point.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoWebControlFilter extends MAFFilter {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MoskitoWebControlFilter.class);

	@Override
	public void init(final FilterConfig config) throws ServletException {
		LOGGER.info("----  Moskito Web Control Initialization: STARTED  ------");
		super.init(config);

		// api's initialization
		configureAPITier();

		LOGGER.info("----  Moskito Web Control Initialization: FINISHED  ------");
	}

	@Override
	protected List<ActionMappingsConfigurator> getConfigurators() {
		return Arrays.asList(new ActionMappingsConfigurator[] { new MoskitoWebControlActionMappings() });
	}

	@Override
	protected boolean isPathExcluded(String servletPath) {
		if (servletPath.startsWith("/js/"))
			return true;
		if (servletPath.startsWith("/css/"))
			return true;
		if (servletPath.startsWith("/img/"))
			return true;

		return super.isPathExcluded(servletPath);
	}

	/**
	 * Configuring API Tier.
	 */
	private void configureAPITier() {
	}

}
