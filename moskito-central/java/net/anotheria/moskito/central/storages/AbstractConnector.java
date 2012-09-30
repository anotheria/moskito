package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.central.StatStorage;
import net.java.dev.moskito.core.producers.ISnapshotArchiver;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;
import org.apache.commons.lang.ArrayUtils;

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
    protected String[] expectedIntervals;

    public AbstractConnector(StatStorage storage) {
        this.storage = storage;
    }

    public AbstractConnector(StatStorage storage, String[] expectedIntervals) {
        this.storage = storage;
        this.expectedIntervals = expectedIntervals;
    }

    protected abstract void archiveStats(Interval aCaller, IStatsSnapshot snapshot, String hostName);

	@Override
	public String[] getExpectedIntervals() {
		return expectedIntervals;
	}

	@Override
    public void archive(Interval aCaller, IStatsSnapshot snapshot, String hostName) {
        if(isExpectedInterval(aCaller.getName(), expectedIntervals)) {
            archiveStats(aCaller, snapshot, hostName);
        }
    }

    protected boolean isExpectedInterval(String currentInterval, String[] expectedIntervals) {
        if(ArrayUtils.isEmpty(expectedIntervals)) return true;
        for(String interval : expectedIntervals) {
            if(interval.equals(currentInterval)) return true;
        }
        return false;
    }
}
