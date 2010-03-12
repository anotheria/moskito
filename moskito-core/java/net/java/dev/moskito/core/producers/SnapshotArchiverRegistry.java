package net.java.dev.moskito.core.producers;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * Registers archiver w/ archivers config
 *
 * @author imercuriev
 *         Date: Mar 9, 2010
 *         Time: 1:52:28 PM
 */
public enum SnapshotArchiverRegistry {

    INSTANCE;

    private List<ISnapshotArchiver> registeredArchivers = new ArrayList<ISnapshotArchiver>();

    public List<ISnapshotArchiver> getRegisteredArchivers() {
        return registeredArchivers;
    }

    public void setRegisteredArchivers(List<ISnapshotArchiver> registeredArchivers) {
        this.registeredArchivers = registeredArchivers;
    }

    public ISnapshotArchiver registerArchiver(SnapshotArchiverConfig config) {
        try {
            Class storageClass = Class.forName(config.getStorageClassName());
            Object[] storageParams = parseParams(null, config.getStorageParams());
            Object storage = storageClass.getConstructor(getClassesArray(storageParams)).newInstance(storageParams);
            Class archiverClass = Class.forName(config.getClassName());
            Object[] archiverParams = parseParams(storage, config.getArchiversConstructorMoreParams());
            ISnapshotArchiver archiver = (ISnapshotArchiver) archiverClass.getConstructor(getClassesArray(archiverParams)).newInstance(archiverParams);
            registeredArchivers.add(archiver);
            return archiver;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create stats archiver from configureme config file", e);
        }
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

    private static Class[] getClassesArray(Object[] allParams) {
        Class[] result = new Class[allParams.length];
        for (int i = 0; i < result.length; i++) result[i] = allParams[i].getClass();
        return result;
    }
}
