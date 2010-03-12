package net.java.dev.moskito.core.producers;


import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;

import java.util.List;

/**
 * Listens to intervale updates and creates stats snapshots and stores them with configured archivers
 *
 * @author imercuriev
 *         Date: Mar 11, 2010
 *         Time: 12:57:07 PM
 */
public enum SnapshotCreator {
    INSTANCE;

    private SnapshotArchiverConfig config = SnapshotArchiverConfig.createInstance();
    {
        if (config.isValid()) {
            SnapshotArchiverRegistry.INSTANCE.registerArchiver(config);
        }
    }

    public void createSnapshot(Interval interval) {
        for (ISnapshotArchiver archiver : SnapshotArchiverRegistry.INSTANCE.getRegisteredArchivers()) {
            for (IStatsProducer producer : ProducerRegistryFactory.getProducerRegistryInstance().getProducers()) {
                for (IStats stats : producer.getStats()) {
                    archiver.archive(interval, stats.createSnapshot(interval.getName(), producer.getProducerId()), config.getHostName());
                }
            }
        }
    }
}
