package net.java.dev.moskito.control.monitor.core.history;

import java.util.Collection;

public interface History {

	Collection<HistoryEntry> getAllMoskitoHistoryEntries();
	
	void putHistoryEntry(HistoryEntry entry);
	
	void clear();
}
