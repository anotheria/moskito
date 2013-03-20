package net.anotheria.moskito.core.snapshot;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.03.13 14:31
 */
public interface SnapshotConsumer {
	void consumeSnapshot(ProducerSnapshot snapshot);
}
