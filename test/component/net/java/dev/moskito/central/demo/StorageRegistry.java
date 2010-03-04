package net.java.dev.moskito.central.demo;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.central.StatStorageException;
import net.java.dev.moskito.central.storages.XmlStatStorage;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.predefined.Constants;
import net.java.dev.moskito.core.producers.IStatsSnapshot;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class loads storage that is currently selected and configured in demo.json
 *
 * @author imercuriev
 *         Date: Feb 26, 2010
 *         Time: 10:20:16 AM
 */
public class StorageRegistry {

    private static StorageRegistry instance;

    private StatStorage storage;

    private StorageRegistry() {}

    public static synchronized StorageRegistry getInstance() {
        if (instance == null) {
            instance = new StorageRegistry();
        }
        return instance;
    }

    public StatStorage getStorage() {
        if (storage == null) {
            //todo: implement storage creation w/ storage factories
            final String XML_STORAGE_ROOT_DIR = "/projects/companies/Anotheria/SVN/tests/xml";
            // this initializes XML storage for sercviceStats
            File storageRoot = new File(XML_STORAGE_ROOT_DIR);
            if (!storageRoot.exists()) {
                // if not exists trying to create
                if (!storageRoot.mkdirs()) {
                    throw new RuntimeException("Cannot create XML storage root folder: " + XML_STORAGE_ROOT_DIR);
                }
            }
            storage = new XmlStatStorage(storageRoot);
        }
        return storage;
    }
}
