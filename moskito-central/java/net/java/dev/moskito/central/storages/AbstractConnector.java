package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.core.producers.ISnapshotArchiver;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.central.StatStorage;

/**
 * Abstract remoting for moskito central.
 * This handles basic initailzation and implementsa required parameters
 * <ul>
 * <li>stats</li>
 * <li>storage</li>
 * <li>hostName</li>
 * </ul>
 *
 * @author imercuriev
 *         Date: Mar 9, 2010
 *         Time: 3:08:34 PM
 */
public abstract class AbstractConnector implements ISnapshotArchiver {
    protected StatStorage storage;

    public AbstractConnector(StatStorage storage) {
        this.storage = storage;
    }
}
