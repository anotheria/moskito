package net.anotheria.moskito.extensions.monitoring;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.extensions.monitoring.config.MonitoringPluginConfig;
import net.anotheria.moskito.extensions.monitoring.fetcher.HttpStatusFetcher;
import net.anotheria.moskito.extensions.monitoring.parser.ApacheStatusParser;
import net.anotheria.moskito.extensions.monitoring.parser.NginxStubStatusParser;
import net.anotheria.moskito.extensions.monitoring.stats.ApacheStats;
import net.anotheria.moskito.extensions.monitoring.stats.NginxStats;
import net.anotheria.util.StringUtils;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GenericMonitoringPlugin class.
 * Configures {@link MonitoringPluginConfig} from {@link #configurationName} and
 * creates {@link GenericMonitor} based on it.
 *
 * @author dzhmud
 */
@SuppressWarnings("unused")
public class GenericMonitoringPlugin extends AbstractMoskitoPlugin {

    /* Register all available plugin types.*/
    static {
        PluginTypeRegistry.registerPluginType("apache",
                new ApacheStats.ApacheStatsFactory(),
                HttpStatusFetcher.FACTORY,
                ApacheStatusParser.INSTANCE);
        PluginTypeRegistry.registerPluginType("nginx",
                new NginxStats.NginxStatsFactory(),
                HttpStatusFetcher.FACTORY,
                NginxStubStatusParser.INSTANCE);
    }

    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** Name of the configuration file used by this plugin instance. */
    private String configurationName;

    /** GenericMonitor instance. */
    private GenericMonitor genericMonitor;

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public void initialize() {
        final String pluginName = getClass().getName();
        logger.info("Initializing "+pluginName+"...");
        logger.info("Configuring "+pluginName+" from '" + configurationName + "'");

        MonitoringPluginConfig config = new MonitoringPluginConfig();
        if (StringUtils.isEmpty(configurationName))
            ConfigurationManager.INSTANCE.configure(config);
        else
            ConfigurationManager.INSTANCE.configureAs(config, configurationName);

        logger.info("Configured "+pluginName+" from '" + configurationName + "'");

        genericMonitor = GenericMonitor.initialize(configurationName, config);

        logger.info("..."+pluginName+" initialized.");
    }

    @Override
    public void deInitialize() {
        genericMonitor.deInitialize();
    }

}
