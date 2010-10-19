package net.java.dev.moskito.central.storages;

import net.anotheria.anoprise.metafactory.ServiceFactory;
import net.java.dev.moskito.central.StatStorage;
import org.configureme.ConfigurationManager;

/**
 * Distributeme factory for remote storage impl
 *
 * @author imercuriev
 *         Date: Mar 16, 2010
 *         Time: 11:11:57 AM
 */
public class RemoteStorageFactory implements ServiceFactory<StatStorage> {
    public StatStorage create() {
        StatStorage storage = new RemoteStatStorage();
        try {
            ConfigurationManager.INSTANCE.configure(storage);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to configure local storage. Please, check your moskitoStorage.json");
        }
        return storage;
    }
}
