package net.java.dev.moskito.core.journey;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;

/**
 * A monitoring session at the runtime of recording.
 * @author lrosenberg
 *
 */
public class Journey {
	/**
	 * Name of the session.
	 */
	private String name;
	/**
	 * UseCases (Calls) in this session.
	 */
	private List<CurrentlyTracedCall> tracedCalls;
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
	public Journey(String aName){
		name = aName;
		createdTimestamp = System.currentTimeMillis();
		active = true;
		tracedCalls = new ArrayList<CurrentlyTracedCall>();
	}
	
	/**
	 * Adds a use case (call) to this session.
	 * @param aUseCase
	 */
	public void addUseCase(CurrentlyTracedCall aTracedCall){
		tracedCalls.add(aTracedCall);
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
	
	/**
	 * Returns contained use cases.
	 * @return
	 */
	public List<CurrentlyTracedCall> getTracedCalls() {
		return tracedCalls;
	}
}
