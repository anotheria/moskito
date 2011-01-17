package net.java.dev.moskito.webcontrol.repository;

import net.java.dev.moskito.webcontrol.configuration.StatsSource;

public class SnapshotSource {
	private String name;

	public SnapshotSource(String aName) {
		name = aName;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof SnapshotSource && name.equals(((SnapshotSource) o).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}

	// use this method to cache sources later.
	public static SnapshotSource valueOf(StatsSource config) {
		return new SnapshotSource(config.getName());
	}
}
