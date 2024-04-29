package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.alerts.AlertDispatcher;
import net.anotheria.moskito.core.timing.UpdateTriggerServiceFactory;
import net.anotheria.moskito.core.util.AfterStartTasks;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import net.anotheria.moskito.core.util.MBeanUtil;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * This listener sets the name of the application according to the context name and tries to cleanup threadlocals on application stop.
 * However, its almost impossible to cleanup ALL thread locals, therefore some will remain and memory leak utilities, like
 * tomcat's, based on thread locals will complain. However, for a real world use-case there are no problems to be expected.
 */
public class StartStopListener implements ServletContextListener{

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(StartStopListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Initializing moskito for "+sce.getServletContext().getServletContextName());
	}
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("Deinitializing MoSKito for "+sce.getServletContext().getServletContextName());
		BuiltinUpdater.cleanup();
		ThresholdRepository.getInstance().cleanup();
		UpdateTriggerServiceFactory.getUpdateTriggerService().cleanup();
		MBeanUtil.getInstance().cleanup();
		AfterStartTasks.shutdown();
		AlertDispatcher.shutdown();

		/**
		 * Stop configuration source repository.
		 */
		ConfigurationManager.INSTANCE.shutdown();

	}

}
 