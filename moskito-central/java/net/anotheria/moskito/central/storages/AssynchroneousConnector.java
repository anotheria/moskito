package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.central.StatStorage;
import net.anotheria.util.queue.IQueueWorker;
import net.anotheria.util.queue.QueuedProcessor;
import net.anotheria.util.queue.UnrecoverableQueueOverflowException;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;
import org.apache.log4j.Logger;

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
 * @author imercuriev
 *         Date: Mar 15, 2010
 *         Time: 10:23:04 AM
 */
public class AssynchroneousConnector extends AbstractConnector {

    private static Logger log = Logger.getLogger(AssynchroneousConnector.class);

    protected class StorageParamObject {
        private String interval;
        private IStatsSnapshot snapshot;
        private String hostName;
        private Date when;

        public StorageParamObject(String interval, IStatsSnapshot snapshot, String hostName, Date when) {
            this.interval = interval;
            this.snapshot = snapshot;
            this.hostName = hostName;
            this.when = when;
        }

        public String getInterval() {
            return interval;
        }

        public IStatsSnapshot getSnapshot() {
            return snapshot;
        }

        public String getHostName() {
            return hostName;
        }

        public Date getWhen() {
            return when;
        }
    }

    private QueuedProcessor<StorageParamObject> processor;

    public AssynchroneousConnector(StatStorage storage) {
        this(storage, QueuedProcessor.DEF_QUEUE_SIZE);
    }

    public AssynchroneousConnector(StatStorage storage, String[] expectedIntervals) {
        this(storage, expectedIntervals, QueuedProcessor.DEF_QUEUE_SIZE);
    }

    public AssynchroneousConnector(StatStorage storage, int queueLimit) {
        this(storage, null, queueLimit);
    }

    public AssynchroneousConnector(final StatStorage storage, String[] expectedIntervals, int queueLimit) {
        super(storage, expectedIntervals);
        processor = new QueuedProcessor<StorageParamObject>(
                "AssyncArchiver" + System.currentTimeMillis(),  // passed in an unique name
                new IQueueWorker<StorageParamObject>() {
                    public void doWork(StorageParamObject storageParamObject) throws Exception {
                        List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
                        snapshots.add(storageParamObject.getSnapshot());
                        try {
                            storage.store(snapshots, storageParamObject.getWhen(), storageParamObject.getHostName(), storageParamObject.getInterval());
                        } catch (Exception e) {
                            log.warn("Failed to store stats snapshot for stats:\n" +
                                StatStorageUtils.createLogMessage(storageParamObject.getSnapshot(), storageParamObject.getWhen(), storageParamObject.getHostName(), storageParamObject.getInterval()), e);
                        }
                    }
                },
                queueLimit, log
        );
        processor.start();
    }

    public void archiveStats(Interval aCaller, IStatsSnapshot snapshot, String hostName) {
        try {
            processor.addToQueueDontWait(
                new StorageParamObject(
                    aCaller.getName(),
                    snapshot,
                    hostName,
                    new Date()
                )
            );
        } catch (UnrecoverableQueueOverflowException e) {
            log.warn("Failed to add archiver param object to the queue due to " + e.getMessage());
        }
    }

}
