package net.anotheria.moskito.webui.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * This listener can be used to start MoSKito Inspect in embedded mode in a webapp.
 *
 * @author lrosenberg
 * @since 16.04.14 11:04
 */
public class StartMoSKitoInspectBackendForRemoteListener implements ServletContextListener {

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(StartMoSKitoInspectBackendForRemoteListener.class);

	public static final String RMI_PORT_PARAMETER_NAME = "moskitoRmiPort";

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		int port = -1;
		try{
			port = Integer.parseInt(servletContextEvent.getServletContext().getInitParameter(RMI_PORT_PARAMETER_NAME));
			log.info("MoSKito RMI Port set to "+port);
		}catch(Exception e){}

		try{
			StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend(port);
		}catch(MoSKitoInspectStartException e){
			log.error("Couldn't auto-start MoSKito Inspect in embedded mode ", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
