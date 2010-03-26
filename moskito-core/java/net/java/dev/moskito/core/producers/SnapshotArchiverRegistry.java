package net.java.dev.moskito.core.producers;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;


/**
 * Registers archiver w/ archivers config
 *
 * @author imercuriev
 *         Date: Mar 9, 2010
 *         Time: 1:52:28 PM
 */
public enum SnapshotArchiverRegistry {

    INSTANCE;

    private static Logger log = Logger.getLogger(SnapshotArchiverRegistry.class);
    private List<ISnapshotArchiver> registeredArchivers = new ArrayList<ISnapshotArchiver>();

    public List<ISnapshotArchiver> getRegisteredArchivers() {
        return registeredArchivers;
    }

    public void setRegisteredArchivers(List<ISnapshotArchiver> registeredArchivers) {
        this.registeredArchivers = registeredArchivers;
    }

    public ISnapshotArchiver registerArchiver(SnapshotArchiverConfig config) {
        ISnapshotArchiver archiver = null;
        try {
            Object storage = createStorage(config.getStorageClassName(), config.getStorageParams());
            Class archiverClass = Class.forName(config.getClassName());
            Object[] archiverParams = parseParams(
                    storage,
                    config.getArchiversConstructorMoreParams()
            );
            archiver = (ISnapshotArchiver) archiverClass.getConstructor(getClassesArray(archiverParams)).newInstance(archiverParams);
            registeredArchivers.add(archiver);
        } catch (Throwable e) {
            //throw new RuntimeException("Failed to create stats archiver from configureme config file", e);
            System.err.println(SnapshotArchiverConfig.FAILED_TO_CONFIGURE_MESSAGE + "\nCause: " + e.getMessage());
            //e.printStackTrace();
            log.warn(SnapshotArchiverConfig.FAILED_TO_CONFIGURE_MESSAGE, e);
        }
        return archiver;
    }

    public Object createStorage(String storageClassName, String stringStorageParams) throws Exception {
        Class storageClass = Class.forName(storageClassName);
        Object[] storageParams = parseParams(null, stringStorageParams);
        return storageClass.getConstructor(getClassesArray(storageParams)).newInstance(storageParams);
    }

    private static Object[] parseParams(Object firstParam, String params) {
        boolean firstPresent = firstParam != null;
        int minParams = firstPresent ? 1 : 0;
        Object[] result;
        if (params == null || params.length() == 0) {
            result = new Object[minParams];
        } else {
            StringTokenizer st = new StringTokenizer(params, ",");
            result = new Object[minParams + st.countTokens()];
            int i = minParams;
            while (st.hasMoreTokens()) {
                result[i] = st.nextToken();
                i++;
            }
        }
        if (firstPresent) result[0] = firstParam;
        return result;
    }

    private static Class[] getClassesArray(Object[] allParams) throws Exception {
        Class[] result = new Class[allParams.length];
        Class storageClass = Class.forName("net.java.dev.moskito.central.StatStorage");
        boolean firstStorage = (allParams.length > 0 && allParams[0] != null && storageClass.isInstance(allParams[0]));
        if (firstStorage) {
            result[0] = storageClass;
        }
        int startIndex = firstStorage ? 1 : 0;
        for (int i = startIndex; i < result.length; i++) result[i] = allParams[i].getClass();
        return result;
    }
}
