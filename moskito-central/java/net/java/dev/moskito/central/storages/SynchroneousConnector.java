package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.central.StatStorage;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * This represents Moskito Cetral storage connector.
 * This is implemented to store Moskito stats on remote storage using 
 * via predefined synchroneous network protocols, i.e. RMI, CORBA, JMS, ...
 *   
 * @author igor
 *
 */
public class SynchroneousConnector extends AbstractConnector {


    public SynchroneousConnector(StatStorage storage) {
        super(storage);
    }

    public void archive(Interval aCaller, IStatsSnapshot snapshot, String hostName) {
        List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
        snapshots.add(snapshot);
        try {
            storage.store(snapshots, new Date(), hostName, aCaller);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
