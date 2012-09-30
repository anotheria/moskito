package net.anotheria.moskito.core.producers;


import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;

import java.util.ArrayList;

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
            for (IStatsProducer<?> producer : ProducerRegistryFactory.getProducerRegistryInstance().getProducers()) {
            	ArrayList<IStats> statsList = new ArrayList<IStats>();
            	statsList.addAll(producer.getStats());
                for (IStats stats : statsList) {
                    archiver.archive(interval, stats.createSnapshot(interval.getName(), producer.getProducerId()), config.getHostName());
                }
            }
        }
    }
}
