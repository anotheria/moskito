package net.anotheria.moskito.central.storages;

import net.anotheria.moskito.central.StatStorage;
import net.anotheria.moskito.central.StatStorageException;
import net.anotheria.moskito.core.producers.IStatsSnapshot;
import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Feb 22, 2010
 *         Time: 9:11:30 AM
 */
public class XmlStatStorage implements StatStorage {

    private static Logger log = Logger.getLogger(XmlStatStorage.class);

    private StorageFileResolver storageFileResolver;

    public XmlStatStorage(String pathResolverClassName) throws Throwable {
        this((StorageFileResolver) Class.forName(pathResolverClassName).newInstance());
        ConfigurationManager.INSTANCE.configure(this.storageFileResolver);
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
