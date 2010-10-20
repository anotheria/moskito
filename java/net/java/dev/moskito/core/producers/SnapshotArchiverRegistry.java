package net.java.dev.moskito.core.producers;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
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
            String[] archiverIntervals = config.getArchiverIntervals();
			log.info("Registered intervals in config are: " + ArrayUtils.toString(archiverIntervals));
            Object[] archiverParams = parseParams(
                    new Object[]{storage, archiverIntervals},
                    config.getArchiversConstructorMoreParams()
            );
            archiver = (ISnapshotArchiver) archiverClass.getConstructor(getClassesArray(archiverParams)).newInstance(archiverParams);
			log.info("Registered intervals for archiver " + config.getClassName()
					+ " are: " + ArrayUtils.toString(archiver.getExpectedIntervals()));
            registeredArchivers.add(archiver);
        } catch (Throwable e) {
            log.warn(SnapshotArchiverConfig.FAILED_TO_CONFIGURE_MESSAGE, e);
        }
        return archiver;
    }

    public Object createStorage(String storageClassName, String stringStorageParams) throws Exception {
        Class storageClass = Class.forName(storageClassName);
        Object[] storageParams = parseParams(null, stringStorageParams);
        return storageClass.getConstructor(getClassesArray(storageParams)).newInstance(storageParams);
    }

    private static Object[] parseParams(Object[] firstParam, String params) {
        boolean firstPresent = !ArrayUtils.isEmpty(firstParam);
        int minParams = firstPresent ? firstParam.length : 0;
        Object[] result;
        if (StringUtils.isEmpty(params)) {
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
        if (firstPresent) {
            System.arraycopy(firstParam, 0, result, 0, firstParam.length);
        }
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
