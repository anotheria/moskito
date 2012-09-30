package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.core.producers.IStatsSnapshot;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides default algorithm for building paths to the files created in the XML file storage
 *
 * @author imercuriev
 *         Date: Mar 26, 2010
 *         Time: 9:32:57 AM
 */
public class DefaultStorageFileResolver implements StorageFileResolver {

    private File rootDir;
    private static final SimpleDateFormat DEFAULT_DATE_DIR_FORMAT =  new SimpleDateFormat("yyyy_MM_dd");
    private static final SimpleDateFormat DEFAULT_XML_FILE_NAME_FORMAT = new SimpleDateFormat("HH_mm_ss_SSS");
    private static final String DEFAULT_EXT = "xml";

    public DefaultStorageFileResolver(File rootDir) {
        this.rootDir = rootDir;
        if (!rootDir.exists()) {
            throw new RuntimeException("XML stat storage root directory must exist: " + rootDir.getPath());
        }
    }

    public File buildFileRef(IStatsSnapshot snapshot, Date when, String host, String interval) {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(rootDir.getPath());
        pathBuilder.append('/');
        pathBuilder.append(host);
        pathBuilder.append('/');
        pathBuilder.append(interval);
        pathBuilder.append('/');
        pathBuilder.append(snapshot.getProducerId());
        pathBuilder.append('/');
        pathBuilder.append(DEFAULT_DATE_DIR_FORMAT.format(when));
        pathBuilder.append('/');
        pathBuilder.append(snapshot.getInterfaceName());

        File dir = new File(pathBuilder.toString());
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Cannot create disrectory in XML storage: " + dir.getPath());
            }
        }

        if (!dir.exists()) throw new RuntimeException("XML stat storage directory must exist: " + dir.getPath());

        pathBuilder.append('/');
        pathBuilder.append(DEFAULT_DATE_DIR_FORMAT.format(when));
        pathBuilder.append('T');
        pathBuilder.append(DEFAULT_XML_FILE_NAME_FORMAT.format(when));
        pathBuilder.append('.');
        pathBuilder.append(DEFAULT_EXT);
        return new File(pathBuilder.toString());
    }
}
