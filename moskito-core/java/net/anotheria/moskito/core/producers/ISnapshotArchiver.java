package net.anotheria.moskito.core.producers;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Archives stats snapshots per interval
 *
 * @author imercuriev
 *         Date: Mar 9, 2010
 *         Time: 1:49:31 PM
 */
public interface ISnapshotArchiver {

    void archive(Interval aCaller, IStatsSnapshot snapshot, String hostName);

	String[] getExpectedIntervals();
}
