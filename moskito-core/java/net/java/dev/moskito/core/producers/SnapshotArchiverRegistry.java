package net.java.dev.moskito.core.producers;

import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.predefined.Constants;

/**
 * Registers archiver per interval or intervals
 *
 * @author imercuriev
 *         Date: Mar 9, 2010
 *         Time: 1:52:28 PM
 */
public class SnapshotArchiverRegistry {

    /**
     * Registers archiver per given interval so that it is triggered at the end of that interval
     * @param archiver to trigger at the end of the given interval
     * @param interval to execute the archiver at the end of it
     */
    public static void registerArchiverPerInterval(final ISnapshotArchiver archiver, Interval interval) {
        interval.addSecondaryIntervalListener(new IIntervalListener() {
            public void intervalUpdated(Interval aCaller) {
                archiver.archive(aCaller);
            }
        });
    }

    /**
     * Registers archiver per given intervals so that it is triggered at their end
     * @param archiver to trigger at the end of the given intervals
     * @param intervals to execute the archiver at their end
     */
    public static void registerArchiverPerIntervals(final ISnapshotArchiver archiver, Interval[] intervals) {
        for (Interval interval : intervals) {
            registerArchiverPerInterval(archiver, interval);
        }
    }

    /**
     * Registers archiver per all intervals which are defined in Constants.DEFAULT_INTERVALS
     * @param archiver to trigger at the end of all intervals
     */
    public static void registerArchiverAllIntervals(final ISnapshotArchiver archiver) {
        registerArchiverPerIntervals(archiver, Constants.DEFAULT_INTERVALS);
    }
}
