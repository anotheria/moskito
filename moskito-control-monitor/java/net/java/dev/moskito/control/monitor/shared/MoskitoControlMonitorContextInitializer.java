package net.java.dev.moskito.control.monitor.shared;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.control.monitor.core.updater.MonitoredInstanceUpdater;
import net.java.dev.moskito.control.monitor.core.updater.ThresholdsUpdater;

public class MoskitoControlMonitorContextInitializer implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println(" ============== \n Moskito-Control-Monitor was destroyed. \n =============");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(" ============== \n Starting Moskito-Control-Monitor. \n =============");
		
		MonitoredInstanceUpdater updater = new ThresholdsUpdater();
		updater.startInstanceStatusUpdating();
		
		System.out.println(" ============== \n Moskito-Control-Monitor has been started. \n =============");
	}
}
