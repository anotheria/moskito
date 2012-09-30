package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.helper.RuntimeConstants;
import net.anotheria.moskito.core.treshold.ThresholdRepository;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
 