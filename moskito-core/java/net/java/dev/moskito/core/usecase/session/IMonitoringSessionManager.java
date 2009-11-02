package net.java.dev.moskito.core.usecase.session;

import java.util.List;

/**
 * Interface for the monitoring session manager, which manages (creates, stores, deletes) monitoring sessions.
 * @author lrosenberg
 *
 */
public interface IMonitoringSessionManager {
	/**
	 * Returns all known sessions.
	 * @return
	 */
	List<MonitoringSession> getSessions();
	
	/**
	 * Creates a new session with the given name and returns it to the caller.
	 * @param name
	 * @return
	 */
	MonitoringSession createSession(String name);
	/**
	 * Returns the monitoring session with the given name.
	 * @param name name of the session.
	 * @return newly created session.
	 * @throws NoSuchMonitoringSessionException
	 */
	MonitoringSession getSession(String name) throws NoSuchMonitoringSessionException;
	/**
	 * Removes the session with the given name.
	 * @param name name of the session to remove.
	 */
	void removeSession(String name);
	/**
	 * Removes the session from the internal storage.
	 * @param session session to remove.
	 */
	void removeSession(MonitoringSession session);
	
	
}
