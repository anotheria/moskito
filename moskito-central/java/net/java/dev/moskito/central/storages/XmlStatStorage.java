package net.java.dev.moskito.central.storages;

import net.java.dev.moskito.central.StatStorage;
import net.java.dev.moskito.central.StatStorageException;
import net.java.dev.moskito.core.producers.IStatsSnapshot;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSOutput;
import org.apache.log4j.Logger;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Feb 22, 2010
 *         Time: 9:11:30 AM
 */
public class XmlStatStorage implements StatStorage {

    public static interface StorageFileResolver {
        public File buildFileRef(IStatsSnapshot snapshot, Date when, String host, String interval);
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
         * @param snapshot
         * @param when
         * @param host
         * @param interval
         * @return file reference
         */
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

    private static Logger log = Logger.getLogger(AssynchroneousConnector.class);

    private StorageFileResolver storageFileResolver;

    public XmlStatStorage(String rootDir) {
        this(new File(rootDir));
    }

    public XmlStatStorage(File rootDir) {
        storageFileResolver = new DefaultStorageFileResolver(rootDir);
    }

    public XmlStatStorage(StorageFileResolver storageFileResolver) {
        this.storageFileResolver = storageFileResolver;
    }

    public void store(Collection<IStatsSnapshot> snapshots, final Date when, final String host, final String interval) throws StatStorageException {

        for (final IStatsSnapshot snapshot : snapshots) {
            File xmlFile = storageFileResolver.buildFileRef(snapshot, when, host, interval);
            if (xmlFile.exists()) {
                log.warn("Stats has already been stored in file " + xmlFile.getPath() +
                    ". The stats are detailed below:\n" + StatStorageUtils.createLogMessage(snapshot, when, host, interval)
                );
                continue;
//                throw new StatStorageException("Stats has already been stored for host " + host +
//                "The stats can only be stored once per millisecond for the given host for a given interval");
            }
            new XmlFileCreationAlgorithm(xmlFile) {
                protected void doCreate(Document doc) {
                    Element root = doc.createElement("stats"); // Create Root Element
                    doc.appendChild(root);
                    root.setAttribute("host", host);
                    root.setAttribute("interval", interval);
                    root.setAttribute("producerId", snapshot.getProducerId());
                    writeDate(root, (when == null) ? snapshot.getDateCreated() : when);

                    Iterator<String> it = snapshot.getProperties().keySet().iterator();
                    while (it.hasNext()) {
                        String propName = it.next();
                        Number value = snapshot.getProperties().get(propName);
                        Element propElement = doc.createElement(propName);
                        root.appendChild(propElement);
                        propElement.appendChild(doc.createTextNode(String.valueOf(value)));
                    }
                }
                private void writeDate(Element element, Date when) {
                    element.setAttribute("timestamp", String.valueOf(when.getTime()));
                    element.setAttribute("time", String.valueOf(when));
                }
            }.create();
        }
    }

    public IStatsSnapshot queryLastSnapshotByDate(Date when, String host, String statName, String interval) throws StatStorageException {
        return null;
    }
    protected abstract class XmlFileCreationAlgorithm {
        private File xmlFile;
        protected XmlFileCreationAlgorithm(File xmlFile) {
            this.xmlFile = xmlFile;
        }
        protected abstract void doCreate(Document doc) throws StatStorageException;
        public void create() throws StatStorageException {
            FileWriter xmlFileWriter = null;
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();

                doCreate(doc);

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
    }
}
