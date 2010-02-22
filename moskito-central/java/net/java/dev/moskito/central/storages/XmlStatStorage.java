package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.central.StatStorageException;
import net.java.dev.moskito.core.producers.IStatsSnapshot;
import net.java.dev.moskito.core.stats.Interval;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.util.Collection;
import java.util.Date;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Feb 22, 2010
 *         Time: 9:11:30 AM
 */
public class XmlStatStorage implements StatStorage {

    public static interface StorageFileResolver {
        public File buildFileRef(Date when, String host, Interval interval);
    }

    public static class DefaultStorageFileResolver implements StorageFileResolver {

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

        /**
         * Implements the following storage structure
         * 0 gedb01:/opt/data/gecollector/biz01/IUserService/5m/2008_01_01# ls -l | tail -30
         * -rw-r--r-- 1 frs users 7754 Jan  1  2008 2008_01_01T21_29_43_761.xml
         *
         * @param when
         * @param host
         * @param interval
         * @return file reference
         */
        public File buildFileRef(Date when, String host, Interval interval) {
            StringBuilder pathBuilder = new StringBuilder();
            pathBuilder.append(rootDir.getPath());
            pathBuilder.append('/');
            pathBuilder.append(host);
            pathBuilder.append('/');
            pathBuilder.append(interval.getName());
            pathBuilder.append('/');
            pathBuilder.append(DEFAULT_DATE_DIR_FORMAT.format(when));

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

    private StorageFileResolver storageFileResolver;

    public XmlStatStorage(File rootDir) {
        storageFileResolver = new DefaultStorageFileResolver(rootDir);
    }

    public void store(Collection<IStatsSnapshot> snapshots, Date when, String host, Interval interval) throws StatStorageException {
        File xmlFile = storageFileResolver.buildFileRef(when, host, interval);
        if (xmlFile.exists()) {
            throw new StatStorageException("Stats has already been stored for host " + host +
            "The stats can only be stored once per millisecond for the given host for a given interval");
        }
        FileWriter xmlFileWriter = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element root = doc.createElement("stats"); // Create Root Element
            doc.appendChild(root);

            DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            DOMImplementationLS domImplLS = (DOMImplementationLS)registry.getDOMImplementation("LS");

            LSSerializer ser = domImplLS.createLSSerializer();  // Create a serializer for the DOM
            LSOutput out = domImplLS.createLSOutput();
            xmlFileWriter = new FileWriter(xmlFile);        // Writer will be a String
            out.setCharacterStream(xmlFileWriter);
            ser.write(doc, out);                                // Serialize the DOM
        } catch (Exception e) {
            throw new StatStorageException("Failed to store stats XML file: " + xmlFile.getPath());
        } finally {
            if (xmlFileWriter != null) {
                try {
                    xmlFileWriter.close();
                } catch (Exception e) {
                    throw new StatStorageException("Failed to close stats XML file: " + xmlFile.getPath());
                }
            }
        }

    }

    public IStatsSnapshot queryLastSnapshotByDate(Date when, String host, String statName, Interval interval) throws StatStorageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
