package net.java.dev.moskito.webcontrol.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum Repository {

	INSTANCE;

	private ConcurrentMap<String, Container> containers;

	private Repository() {
		containers = new ConcurrentHashMap<String, Container>();
	}

	public List<Snapshot> getSnapshots(String containerName) {
		return getContainer(containerName).getSnapshots();
	}

	// in the future add a method which supplies a maximum age for a snapshot
	public Snapshot getSnapshot(String containerName, SnapshotSource source) {
		Snapshot s = getContainer(containerName).getSnapshot(source);
		if (s == null)
			throw new NullPointerException("Snapshot " + containerName + " from " + source + " not found.");
		return s;
	}

	public void addSnapshot(String containerName, Snapshot snapshot) {
		getContainer(containerName).addSnapshot(snapshot);
	}

	private Container getContainer(String containerName) {
		Container c = containers.get(containerName);
		if (c == null) {
			c = new Container(containerName);
			containers.putIfAbsent(containerName, c);
		}
		return c;
	}

	public void clear() {
		containers.clear();
	}
}
