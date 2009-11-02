package net.java.dev.moskito.core.usecase.session;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;

/**
 * A monitoring session at the runtime of recording.
 * @author lrosenberg
 *
 */
public class MonitoringSession {
	/**
	 * Name of the session.
	 */
	private String name;
	/**
	 * UseCases (Calls) in this session.
	 */
	private List<ExistingRunningUseCase> useCases;
	/**
	 * True if the session is still actively recorded.
	 */
	private boolean active;
	/**
	 * Timestamp of the session creation.
	 */
	private long createdTimestamp;
	/**
	 * Timestamp of last activity in this session.
	 */
	private long lastActivityTimestamp;
	
	/**
	 * Creates a new monitoring session with a given name.
	 * @param aName
	 */
	public MonitoringSession(String aName){
		name = aName;
		createdTimestamp = System.currentTimeMillis();
		active = true;
		useCases = new ArrayList<ExistingRunningUseCase>();
	}
	
	/**
	 * Adds a use case (call) to this session.
	 * @param aUseCase
	 */
	public void addUseCase(ExistingRunningUseCase aUseCase){
		useCases.add(aUseCase);
		lastActivityTimestamp = System.currentTimeMillis();
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public long getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public long getLastActivityTimestamp() {
		return lastActivityTimestamp;
	}
	public void setLastActivityTimestamp(long lastActivityTimestamp) {
		this.lastActivityTimestamp = lastActivityTimestamp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ExistingRunningUseCase> getUseCases() {
		return useCases;
	}
}
