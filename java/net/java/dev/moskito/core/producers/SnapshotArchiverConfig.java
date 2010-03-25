package net.java.dev.moskito.core.producers;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.configureme.annotations.AbortedConfiguration;
import org.configureme.annotations.AfterConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

/**
 * Configuration that is provided by any client software that
 * wants their Moskito stats to be archived
 *
 * The following configuration is supported (this configures local XML storage):
 * {
 *     className: "net.java.dev.moskito.central.storages.SynchroneousConnector",
 *     archiversConstructorMoreParams: "",
 *     storageClassName: "net.java.dev.moskito.central.storages.XmlStatStorage",
 *     storageParams: "",
 *     hostName: "thishost.net"
 * }
 *
 * The following assumptions are made concerning ISnapshotArchiver implementations.
 *
 * <ul>
 * <li> SynchroneousConnector (ISnapshotArchiver implementation in a more common case)
 * accepts storage instance as first param and optionally can accept more params of type String.</li>
 * <li>Storage implementation  accepts 0 or more params of type String</li>
 * </ul>
 *
 * The config properties are used to define the following:
 * <ul>
 * <li>className - name of archiver implementation class</li>
 * <li>archiversConstructorMoreParams - specifies additional comma-separated list of
 * String params for the archiver constructor, in case of empty
 * string it is assuned that the constructor has no additional string params</li>
 * <li>storageClassName - implementation class name of the archivers storage</li>
 * <li>storageParams - comma-separated list of list of params passed to the storage implementation</li>
 * <li>hostName - is used to specify host name of the machine that produces the stats. Archivers do store this information too.
 * The hostName parameter is optional in the config file. If it is ommited the host name is autodetected.
 * But if it fails to autodetect then RuntimeException is thrown</li>
 * </ul>
 *
 * @author imercuriev
 *         Date: Mar 11, 2010
 *         Time: 10:01:42 AM
 */
@ConfigureMe(allfields=true, name="archiver")
public final class SnapshotArchiverConfig {
    private String className;
    private String archiversConstructorMoreParams;
    private String storageClassName;
    private String storageParams;
    private String hostName;

    @DontConfigure private boolean valid;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getArchiversConstructorMoreParams() {
        return archiversConstructorMoreParams;
    }

    public void setArchiversConstructorMoreParams(String archiversConstructorMoreParams) {
        this.archiversConstructorMoreParams = archiversConstructorMoreParams;
    }

    public String getStorageClassName() {
        return storageClassName;
    }

    public void setStorageClassName(String storageClassName) {
        this.storageClassName = storageClassName;
    }

    public String getStorageParams() {
        return storageParams;
    }

    public void setStorageParams(String storageParams) {
        this.storageParams = storageParams;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @AbortedConfiguration public void callIfAborted(){
        valid = false;
    }

    @AfterConfiguration public void callAfterEachConfiguration(){
        boolean configValid = true;
        if (hostName == null) {
            try {
                hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                configValid = false;
                //throw new RuntimeException("Cannot create archivers config since host name information cannot be retrieved", e);
                e.printStackTrace();
            }
        }
        valid = configValid;
    }


    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public static SnapshotArchiverConfig createInstance() {
        SnapshotArchiverConfig instance = new SnapshotArchiverConfig();
        try {
            ConfigurationManager.INSTANCE.configure(instance);
        } catch (IllegalArgumentException e) {
            // config is invalid
            System.err.println("Failed to configure storage instance");
            e.printStackTrace();
        } catch (Throwable e) {
            System.err.println("Failed to configure storage instance");
            e.printStackTrace();
        }
        return instance;
    }

    private static final String TO_STRING_FORMAT = "className: {0}, archiversConstructorMoreParams {1}, storageClassName {2}, storageParams {3}, hostName {4}, valid {5}";
    public String toString() {
        return MessageFormat.format(TO_STRING_FORMAT, this.className, this.archiversConstructorMoreParams, this.storageClassName, this.storageParams, this.hostName, this.valid);
    }
}
