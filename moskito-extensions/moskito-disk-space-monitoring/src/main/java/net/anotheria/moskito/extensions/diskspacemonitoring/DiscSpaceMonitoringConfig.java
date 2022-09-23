package net.anotheria.moskito.extensions.diskspacemonitoring;


import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.LoggerFactory;

/**
 * Config for DiscSpaceMonitoring plugin.
 */
@ConfigureMe(name = "moskito-plugin-disk-monitoring")
public class DiscSpaceMonitoringConfig {
    /**
     * Configured disk list.
     */
    @Configure
    private String[] disks;

    /**
     * Monitor object.
     */
    private static final Object monitor = new Object();

    /**
     * {@link DiscSpaceMonitoringConfig} instance
     */
    private static DiscSpaceMonitoringConfig instance;

    public static DiscSpaceMonitoringConfig getInstance() {
        if (instance != null)
            return instance;
        synchronized (monitor) {
            if (instance != null)
                return instance;
            instance = new DiscSpaceMonitoringConfig();
            try {
                ConfigurationManager.INSTANCE.configure(instance);
            } catch (Exception e) {
                LoggerFactory.getLogger(DiscSpaceMonitoringConfig.class).warn("Configuration failed. Relying on defaults. " + e.getMessage());
            }
            return instance;
        }
    }

    private DiscSpaceMonitoringConfig(){

    }

    public String[] getDisks() {
        return disks;
    }

    public void setDisks(String[] disks) {
        this.disks = disks;
    }
}
