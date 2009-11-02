package net.java.dev.moskito.core.usecase.session;

/**
 * The factory for the monitoring session manager. 
 */
public class MonitoringSessionManagerFactory {
	/**
	 * Internally stored singleton instance.
	 */
	private static IMonitoringSessionManager instance = new MonitoringSessionManagerImpl();
	/**
	 * Returns the singleton instance.
	 * @return
	 */
	public static IMonitoringSessionManager getMonitoringSessionManager(){
		return instance;
	}
}
