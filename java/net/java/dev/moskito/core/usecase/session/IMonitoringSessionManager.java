package net.java.dev.moskito.core.usecase.session;

import java.util.List;

/**
 * Interface for the monitoring session manager, which manages (creates, stores, deletes) monitoring sessions.
 * @author another
 *
 */
public interface IMonitoringSessionManager {
	/**
	 * Returns all known sessions.
	 * @return
	 */
	public List<MonitoringSession> getSessions();
	
	/**
	 * Creates a new session with the given name and returns it to the caller.
	 * @param name
	 * @return
	 */
	public MonitoringSession createSession(String name);
	/**
	 * Returns the monitoring session with the given name.
	 * @param name
	 * @return
	 * @throws NoSuchMonitoringSessionException
	 */
	public MonitoringSession getSession(String name) throws NoSuchMonitoringSessionException;
	/**
	 * Removes the session with the given name.
	 * @param name
	 */
	public void removeSession(String name);
	/**
	 * Removes the session from the internal storage.
	 * @param session
	 */
	public void removeSession(MonitoringSession session);
	
	
}
