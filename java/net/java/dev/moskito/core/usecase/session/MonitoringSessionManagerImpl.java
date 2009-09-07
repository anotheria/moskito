package net.java.dev.moskito.core.usecase.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MonitoringSessionManagerImpl implements IMonitoringSessionManager{

	private Map<String, MonitoringSession> sessions;
	
	MonitoringSessionManagerImpl(){
		sessions = new ConcurrentHashMap<String, MonitoringSession>();
	}
	
	public MonitoringSession createSession(String name) {
		MonitoringSession s = new MonitoringSession(name);
		sessions.put(s.getName(), s);
		return s;
	}

	public MonitoringSession getSession(String name) throws NoSuchMonitoringSessionException {
		MonitoringSession s = sessions.get(name);
		if (s==null)
			throw new NoSuchMonitoringSessionException(name);
		return s;
	}

	public List<MonitoringSession> getSessions() {
		ArrayList<MonitoringSession> ret = new ArrayList<MonitoringSession>();//do not call sessions size since it will synchronize the map
		ret.addAll(sessions.values());
		return ret;
	}

	public void removeSession(MonitoringSession session) {
		removeSession(session.getName());
		
	}

	public void removeSession(String name) {
		sessions.remove(name);		
	}

}
