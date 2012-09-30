package net.anotheria.moskito.central.storages;

import net.anotheria.anoprise.metafactory.Extension;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.anoprise.metafactory.ServiceFactory;
import net.anotheria.moskito.central.StatStorage;
import net.anotheria.moskito.central.StatStorageException;
import net.anotheria.moskito.core.producers.IStatsSnapshot;

import java.util.Collection;
import java.util.Date;

/**
 * This storage implementation is a proxy for remote storage
 *
 * @author imercuriev
 *         Date: Mar 16, 2010
 *         Time: 11:51:35 AM
 */
public class LocalProxyStorage implements StatStorage {

    private StatStorage remoteStorage;

    public LocalProxyStorage() throws Exception {
        MetaFactory.addFactoryClass(StatStorage.class, Extension.REMOTE, (Class<ServiceFactory<StatStorage>>)Class.forName("net.java.dev.moskito.central.generated.RemoteStatStorageFactory"));
        remoteStorage = MetaFactory.get(StatStorage.class, Extension.REMOTE);
    }

    public void store(Collection<IStatsSnapshot> snapshots, Date when, String host, String interval) throws StatStorageException {
        remoteStorage.store(snapshots, when, host, interval);
    }

    public IStatsSnapshot queryLastSnapshotByDate(Date when, String host, String statName, String interval) throws StatStorageException {
        return remoteStorage.queryLastSnapshotByDate(when, host, statName, interval);
    }
}
