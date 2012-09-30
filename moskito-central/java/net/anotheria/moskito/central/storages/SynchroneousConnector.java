package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.central.StatStorage;
import net.anotheria.moskito.core.producers.IStatsSnapshot;
import net.anotheria.moskito.core.stats.Interval;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This represents Moskito Cetral storage connector.
 * This is implemented to store Moskito stats on remote storage using 
 * via predefined synchroneous network protocols, i.e. RMI, CORBA, JMS, ...
 *
 * Using this connector stats are stored within the same thread.
 * This can notcably impact performance of other operetaion which might take place
 * per interval update. If this really matters you can use the AssychroneousConnector
 *   
 * @author igor
 *
 */
public class SynchroneousConnector extends AbstractConnector {


    public SynchroneousConnector(StatStorage storage) {
        super(storage);
    }

    public void archiveStats(Interval aCaller, IStatsSnapshot snapshot, String hostName) {
        List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
        snapshots.add(snapshot);
        try {
            storage.store(snapshots, new Date(), hostName, aCaller.getName());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
