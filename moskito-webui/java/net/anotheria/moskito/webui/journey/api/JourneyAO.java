package net.anotheria.moskito.webui.journey.api;

import java.io.Serializable;
import java.util.List;

/**
 * Journey DTO to be used in the API Layer.
 *
 * @author lrosenberg
 * @since 05.04.14 11:37
 */
public class JourneyAO implements Serializable{
	/**
	 * Contained calls.
	 */
	List<JourneySingleTracedCallAO> calls;
	/**
	 * Name of the journey.
	 */
	private String name;
	/**
	 * If true the sessions is still active.
	 */
	private boolean active;
	/**
	 * Timestamp the journey was created.
	 */
	private long createdTimestamp;
	/**
	 * Timestamp of last activity in the session.
	 */
	private long lastActivityTimestamp;

	public List<JourneySingleTracedCallAO> getCalls() {
		return calls;
	}

	public void setCalls(List<JourneySingleTracedCallAO> calls) {
		this.calls = calls;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
