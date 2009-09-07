package net.java.dev.moskito.core.usecase.session;

import java.util.List;

public interface IMonitoringSessionManager {
	public List<MonitoringSession> getSessions();
	
	public MonitoringSession createSession(String name);
	
	public MonitoringSession getSession(String name) throws NoSuchMonitoringSessionException;
	
	public void removeSession(String name);
	
	public void removeSession(MonitoringSession session);
	
	
}
