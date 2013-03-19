package net.anotheria.moskito.central.storage;

import net.anotheria.moskito.central.Snapshot;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.03.13 23:14
 */
public interface Storage {

	void configure(String configurationName);

	void processSnapshot(Snapshot target);
}
