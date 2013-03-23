package net.anotheria.moskito.central.storage;

import net.anotheria.moskito.central.Snapshot;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 14:07
 */
public interface SnapshotSerializer {
	byte[] serialize(Snapshot snapshot);
}
