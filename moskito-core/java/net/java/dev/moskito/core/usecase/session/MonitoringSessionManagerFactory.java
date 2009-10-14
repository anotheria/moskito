package net.java.dev.moskito.core.usecase.session;

/**
 * The factory for the monitoring session manager. 
 */
public class MonitoringSessionManagerFactory {
	
	private static IMonitoringSessionManager instance = new MonitoringSessionManagerImpl();
	
	public static IMonitoringSessionManager getMonitoringSessionManager(){
		return instance;
	}
}
