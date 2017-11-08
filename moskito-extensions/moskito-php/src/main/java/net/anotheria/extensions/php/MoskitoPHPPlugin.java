package net.anotheria.extensions.php;

import net.anotheria.extensions.php.config.MoskitoPHPConfig;
import net.anotheria.extensions.php.connectors.Connector;
import net.anotheria.extensions.php.exceptions.PHPPluginBootstrapException;
import net.anotheria.extensions.php.stats.PHPScriptExecutionStats;
import net.anotheria.extensions.php.stats.decorators.PhpScriptExecutionStatDecorator;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * PHP plugin.
 *
 * Registers producers that comes from php agent.
 *
 * Has configurable mappers that maps php producers
 * and configurable connectors to receive data
 * See {@link MoskitoPHPConfig} class for configuration details
 *
 */
public class MoskitoPHPPlugin extends AbstractMoskitoPlugin {

    private static final Logger log = LoggerFactory.getLogger(MoskitoPHPPlugin.class);

    private ConfigBootstrapper configBootstrapper;


    /** Name of the configuration file used by this plugin instance. */
    private String configurationName;

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public void initialize() {

        if(configurationName == null)
            throw new PHPPluginBootstrapException("Configuration name is not set for Moskito PHP plugin");

        log.info("Initializing Moskito PHP plugin with configuration " + configurationName + "...");

        // Register PHP execution stats decorator
        DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(
                PHPScriptExecutionStats.class,
                new PhpScriptExecutionStatDecorator()
        );

        configBootstrapper = new ConfigBootstrapper(configurationName);

        // Loading plugin mappers and connectors
        configBootstrapper.bootstrapPlugin(ProducerRegistryFactory.getProducerRegistryInstance());

    }

    @Override
    public void deInitialize() {
        log.info("Deinitializing Moskito PHP plugin...");
        configBootstrapper.destroyPlugin();
    }

}
