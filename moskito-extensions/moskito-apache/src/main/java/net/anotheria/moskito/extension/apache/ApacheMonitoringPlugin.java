package net.anotheria.moskito.extension.apache;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.extension.apache.config.ApacheMonitoringPluginConfig;
import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Moskito monitoring plugin for Apache httpd server.
 *
 * @author dzhmud
 */
public class ApacheMonitoringPlugin extends AbstractMoskitoPlugin {

    /**
     * Single logger instance for ApacheMonitoringPlugin and all related classes.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(ApacheMonitoringPlugin.class);

    /**
     * Name of the configuration file used by this plugin instance.
     */
    private String configurationName;

    /**
     * ApacheMonitor instance.
     */
    private ApacheMonitor apacheMonitor;

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public void initialize() {
        LOGGER.info("Initializing ApacheMonitoringPlugin...");
        LOGGER.info("Configuring ApacheMonitorConfig from '" + configurationName + "'");

        ApacheMonitoringPluginConfig config = new ApacheMonitoringPluginConfig();
        if (StringUtils.isEmpty(configurationName))
            ConfigurationManager.INSTANCE.configure(config);
        else
            ConfigurationManager.INSTANCE.configureAs(config, configurationName);

        LOGGER.info("Configured ApacheMonitorConfig from '" + configurationName + "'");

        apacheMonitor = ApacheMonitor.initialize(config);

        LOGGER.info("...ApacheMonitoringPlugin initialized.");
    }

    @Override
    public void deInitialize() {
        apacheMonitor.deInitialize();
    }
    
}
