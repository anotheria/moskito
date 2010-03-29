package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.core.producers.IStatsSnapshot;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.Configure;
import org.configureme.annotations.DontConfigure;
import org.configureme.annotations.AfterConfiguration;
import org.apache.log4j.Logger;

/**
 * Implementation of StorageFileResolver interface
 * that uses configurable path string and is able to accept instance name from
 * Java system property.
 *
 * For more details please reffer to <a href="http://infra.anotheria.net/jira/browse/MSK-16">JIRA TASK MSK-16</a>
 *
 * @author imercuriev
 *         Date: Mar 26, 2010
 *         Time: 10:49:30 AM
 */
@ConfigureMe(name = "moskitoCentralPath")
public class ConfigurableStorageFileResolver implements StorageFileResolver {

    private static final String PARAM_STORAGE_DIR = "{storagedir}";
    private static final String PARAM_HOST = "{host}";
    private static final String PARAM_INSTANCE = "{instance}";
    private static final String PARAM_PRODUCER = "{producer}";
    private static final String PARAM_INTERVAL_NAME = "{intervalname}";
    private static final String PARAM_DATE = "{date}";

    private static final String[] PATH_PARAMS = new String[] {
        PARAM_STORAGE_DIR, PARAM_HOST, PARAM_INSTANCE, PARAM_PRODUCER, PARAM_INTERVAL_NAME, PARAM_DATE
    };

    private static final String DEFAULT_STORAGE_FILE_FORMAT = buildDefaultFileFormat();

    private static String buildDefaultFileFormat() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < PATH_PARAMS.length; i++) {
            String pathElement = PATH_PARAMS[i];
            if (i > 0) sb.append("/");
            sb.append(pathElement);
        }
        return sb.toString();
    }

    private static final SimpleDateFormat DEFAULT_DATE_DIR_FORMAT =  new SimpleDateFormat("yyyy_MM_dd");
    private static final SimpleDateFormat DEFAULT_XML_FILE_NAME_FORMAT = new SimpleDateFormat("HH_mm_ss_SSS");
    private static final String DEFAULT_EXT = "xml";

    private static Logger log = Logger.getLogger(ConfigurableStorageFileResolver.class);
    @Configure
    private String rootDirString;
    @Configure
    private String storageFileFormat;
    @Configure
    private String instanceName;

    @DontConfigure
    private final String[] pathParamValues = new String[PATH_PARAMS.length];


    public String getRootDirString() {
        return rootDirString;
    }

    public void setRootDirString(String rootDirString) {
        this.rootDirString = rootDirString;
    }

    public String getStorageFileFormat() {
        return storageFileFormat;
    }

    public void setStorageFileFormat(String storageFileFormat) {
        this.storageFileFormat = storageFileFormat;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    @AfterConfiguration
    public void callAfterEachConfiguration() {
        // fill in param values, this param values can only change with reconfiguration
        pathParamValues[0] = rootDirString;
        pathParamValues[1] = storageFileFormat;
        pathParamValues[2] = instanceName;

        // if storage file format is not specified then default one is used
        if (storageFileFormat == null) storageFileFormat = DEFAULT_STORAGE_FILE_FORMAT;
        log.info("Using storage file format: " + storageFileFormat);

        // if Java System property named "instanceName" is specified then it should be used as instanceName
        String instanceNamePropertyValue = System.getProperty("instanceName");
        if (instanceNamePropertyValue != null) {
            instanceName = instanceNamePropertyValue;
        }
        if (instanceName == null) {
            instanceName = "unknown" + System.currentTimeMillis();
        }
        log.info("Using instance name: " + instanceName);
    }

    public File buildFileRef(IStatsSnapshot snapshot, Date when, String host, String interval) {
        String fileDir = storageFileFormat
            .replaceAll(PARAM_STORAGE_DIR, rootDirString)
            .replaceAll(PARAM_HOST, instanceName)
            .replaceAll(PARAM_PRODUCER, snapshot.getProducerId())
            .replaceAll(PARAM_INTERVAL_NAME, interval)
            .replaceAll(PARAM_DATE, DEFAULT_DATE_DIR_FORMAT.format(when));

        File dir = new File(fileDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Cannot create disrectory in XML storage: " + dir.getPath());
            }
        }

        if (!dir.exists()) throw new RuntimeException("XML stat storage directory must exist: " + dir.getPath());

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(fileDir);
        pathBuilder.append('/');
        pathBuilder.append(DEFAULT_DATE_DIR_FORMAT.format(when));
        pathBuilder.append('T');
        pathBuilder.append(DEFAULT_XML_FILE_NAME_FORMAT.format(when));
        pathBuilder.append('.');
        pathBuilder.append(DEFAULT_EXT);
        return new File(pathBuilder.toString());
    }
}
