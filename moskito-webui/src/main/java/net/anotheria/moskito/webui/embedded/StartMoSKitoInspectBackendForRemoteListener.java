package net.anotheria.moskito.webui.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener can be used to start MoSKito Inspect in embedded mode in a webapp.
 *
 * @author lrosenberg
 * @since 16.04.14 11:04
 */
public class StartMoSKitoInspectBackendForRemoteListener implements ServletContextListener {

	private static Logger log = LoggerFactory.getLogger(StartMoSKitoInspectBackendForRemoteListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try{
			StartMoSKitoInspectBackendForRemote.startMoSKitoInspectBackend();
		}catch(MoSKitoInspectStartException e){
			log.error("Couldn't auto-start MoSKito Inspect in embedded mode ", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
