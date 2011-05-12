package net.java.dev.moskito.webui.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.helper.RuntimeConstants;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.core.util.BuiltinUpdater;

import org.apache.log4j.Logger;

public class StartStopListener implements ServletContextListener{
	
	private static Logger log = Logger.getLogger(StartStopListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Initializing moskito for "+sce.getServletContext().getServletContextName());
		RuntimeConstants.setApplicationName(sce.getServletContext().getServletContextName());
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Deinitializing moskito for "+sce.getServletContext().getServletContextName());
		BuiltinUpdater.cleanup();
		ThresholdRepository.getInstance().cleanup();
	}

}
 