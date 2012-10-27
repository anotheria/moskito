package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.helper.RuntimeConstants;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This listener sets the name of the application according to the context name and tries to cleanup threadlocals on application stop.
 * However, its almost impossible to cleanup ALL thread locals, therefore some will remain and memory leak utilities, like
 * tomcat's, based on thread locals will complain. However, for a real world use-case there are no problems to be expected.
 */
public class StartStopListener implements ServletContextListener{

	/**
	 * Logger.
	 */
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
 