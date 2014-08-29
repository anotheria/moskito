package net.anotheria.moskito.core.snapshot;

/**
 * A class that wants to consume snapshots after they are created by the Snapshot creator on an interval update has to implement this interface.
 *
 * @author lrosenberg
 * @since 20.03.13 14:31
 */
public interface SnapshotConsumer {
	void consumeSnapshot(ProducerSnapshot snapshot);
}
