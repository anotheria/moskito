package net.java.dev.moskito.webcontrol.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Container {

	private String name;
	private ConcurrentMap<String, Snapshot> snapshots;

	public Container(String aName) {
		name = aName;
		snapshots = new ConcurrentHashMap<String, Snapshot>();
	}

	public List<Snapshot> getSnapshots() {
		ArrayList<Snapshot> list = new ArrayList<Snapshot>(snapshots.size());
		list.addAll(snapshots.values());
		return list;
	}

	public void addSnapshot(Snapshot s) {
		snapshots.put(s.getSource().toString(), s);
	}

	public Snapshot getSnapshot(SnapshotSource source) {
		return snapshots.get(source.toString());
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "name=" + name + ", " + snapshots;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Container && name.equals(((Container) o).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
