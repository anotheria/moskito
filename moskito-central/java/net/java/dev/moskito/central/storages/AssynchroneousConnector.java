package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.anotheria.util.queue.QueuedProcessor;
import net.anotheria.util.queue.IQueueWorker;
import net.anotheria.util.queue.UnrecoverableQueueOverflowException;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

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
        private Interval interval;
        private IStatsSnapshot snapshot;
        private String hostName;
        private Date when;

        public StorageParamObject(Interval interval, IStatsSnapshot snapshot, String hostName, Date when) {
            this.interval = interval;
            this.snapshot = snapshot;
            this.hostName = hostName;
            this.when = when;
        }

        public Interval getInterval() {
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

    public AssynchroneousConnector(StatStorage storage, String queueLimit) {
        this(storage, Integer.parseInt(queueLimit));
    }

    public AssynchroneousConnector(final StatStorage storage, int queueLimit) {
        super(storage);
        processor = new QueuedProcessor<StorageParamObject>(
                "AssyncArchiver" + System.currentTimeMillis(),  // passed in an unique name
                new IQueueWorker<StorageParamObject>() {
                    public void doWork(StorageParamObject storageParamObject) throws Exception {
                        List<IStatsSnapshot> snapshots = new ArrayList<IStatsSnapshot>();
                        snapshots.add(storageParamObject.getSnapshot());
                        try {
                            storage.store(snapshots, storageParamObject.getWhen(), storageParamObject.getHostName(), storageParamObject.getInterval());
                        } catch (Exception e) {
                            e.printStackTrace(System.err);
                        }
                    }
                },
                queueLimit, log
        );
        processor.start();
    }

    public void archive(Interval aCaller, IStatsSnapshot snapshot, String hostName) {
        try {
            processor.addToQueueDontWait(
                new StorageParamObject(
                    aCaller,
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
