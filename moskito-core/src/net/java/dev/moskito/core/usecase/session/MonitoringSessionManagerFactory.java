package net.java.dev.moskito.core.usecase.session;

public class MonitoringSessionManagerFactory {
	private static IMonitoringSessionManager instance = new MonitoringSessionManagerImpl();
	
	public static IMonitoringSessionManager getMonitoringSessionManager(){
		return instance;
	}
}
