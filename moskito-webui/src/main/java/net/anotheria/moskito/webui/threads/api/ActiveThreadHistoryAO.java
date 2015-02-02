package net.anotheria.moskito.webui.threads.api;

import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryEvent;

import java.io.Serializable;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.03.14 09:38
 */
public class ActiveThreadHistoryAO implements Serializable{
	private List<ThreadHistoryEvent> events;
	private boolean active;
	private int listSize;


	public List<ThreadHistoryEvent> getEvents() {
		return events;
	}

	public void setEvents(List<ThreadHistoryEvent> events) {
		this.events = events;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getListSize() {
		return listSize;
	}
}
