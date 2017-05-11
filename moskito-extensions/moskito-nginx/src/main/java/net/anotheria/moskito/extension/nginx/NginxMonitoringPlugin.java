package net.anotheria.moskito.extension.nginx;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.extension.nginx.config.NginxMonitorConfig;
import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nginx monitoring plugin.
 * Periodically reads metrics from nginx stub status page
 * and provides parsed absolute values and some average per-second values.
 *
 * @author dzhmud
 */
public class NginxMonitoringPlugin extends AbstractMoskitoPlugin {

    /**
     * Single logger instance for NginxMonitoringPlugin and all related classes.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(NginxMonitoringPlugin.class);

    /**
     * Name of the configuration file used by this plugin instance.
     */
    private String configurationName;

    /**
     * NginxMonitor instance.
     */
    private NginxMonitor nginxMonitor;

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public void initialize() {
        LOGGER.info("Initializing NginxMonitoringPlugin...");
        LOGGER.info("Configuring NginxMonitorConfig from '" + configurationName + "'");

        NginxMonitorConfig config = new NginxMonitorConfig();
        if (StringUtils.isEmpty(configurationName))
            ConfigurationManager.INSTANCE.configure(config);
        else
            ConfigurationManager.INSTANCE.configureAs(config, configurationName);

        LOGGER.info("Configured NginxMonitorConfig from '" + configurationName + "'");

        nginxMonitor = NginxMonitor.initialize(config);

        LOGGER.info("...NginxMonitoringPlugin initialized.");
    }

    @Override
    public void deInitialize() {
        nginxMonitor.deInitialize();
    }

}
