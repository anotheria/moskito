package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.core.producers.IStatsSnapshot;

import java.io.File;
import java.util.Date;

/**
 * Implement this interface for configurable
 * file paths. This interface is used by some storage implementations
 * to connect file systems for storing MoSKito stats
 *
 * @author imercuriev
 *         Date: Mar 29, 2010
 *         Time: 11:19:23 AM
 */
public interface StorageFileResolver {
    /**
     * Builds file paths, uses the following parameters
     * @param snapshot stats snapshot that is passed to the stats storage
     * @param when the date used for storing the stats snapshot
     * @param host the name of the machine where the stats have been produced
     * @param interval interval name that the stats have been collected for
     * @return file reference where the stats are supposed to be stored
     */
    public File buildFileRef(IStatsSnapshot snapshot, Date when, String host, String interval);
}
