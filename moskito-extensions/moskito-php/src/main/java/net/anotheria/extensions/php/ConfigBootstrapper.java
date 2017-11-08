package net.anotheria.extensions.php;

import net.anotheria.extensions.php.config.ConfigChangedNotifier;
import net.anotheria.extensions.php.config.ConnectorConfig;
import net.anotheria.extensions.php.config.MapperConfig;
import net.anotheria.extensions.php.config.MoskitoPHPConfig;
import net.anotheria.extensions.php.connectors.Connector;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;
import net.anotheria.extensions.php.exceptions.PHPPluginBootstrapException;
import net.anotheria.extensions.php.mappers.Mapper;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Processes Moskito PHP plugin configuration.
 * Loads mappers and connectors and wires them together
 */
class ConfigBootstrapper {

    private final static Logger log = LoggerFactory.getLogger(ConfigBootstrapper.class);

    private ConnectorsRegistry connectorsRegistry = new ConnectorsRegistry();
    private MoskitoPHPConfig config;
    private OnProducerDataReceivedListener listener;

    private Properties startupProperties;

    private static final String PROPERTY_CONNECTORS_DISABLED = "connectorsDisabled";
    private static final String PROPERTY_TRUE_VALUE = "true";

    ConfigBootstrapper(String configurationName) {

        config = new MoskitoPHPConfig();
        ConfigurationManager.INSTANCE.configureAs(config, configurationName);

        startupProperties = new Properties();
        startupProperties.put(PROPERTY_CONNECTORS_DISABLED,
                System.getProperty(PROPERTY_CONNECTORS_DISABLED, "false")
        );

    }

    /**
     * Helper method for mappers and connectors classes instantiation
     * @param className name of class to create instance
     * @return given class by name instance
     *
     * @throws ClassNotFoundException on given class not found
     * @throws NoSuchMethodException if default constructor not found in given class
     * @throws IllegalAccessException if default constructor in given class has non-public access
     * @throws InvocationTargetException if given class constructor throws an exception
     * @throws InstantiationException on given class instantiation fail
     */
    private static Object createInstance(String className)
            throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException,
            InstantiationException {

        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        return constructor.newInstance();
    }

    private Connector createConnector(ConnectorConfig config, OnProducerDataReceivedListener listener)
            throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException,
            IllegalAccessException, ClassCastException {

        Connector connector = (Connector) createInstance(config.getConnectorClass());
        connector.setOnProducerDataReceivedListener(listener);

        return connector;

    }

    /**
     * Bootstraps Moskito PHP plugin
     *
     * @param producerRegistry producer registry to use
     * @throws PHPPluginBootstrapException on invalid configuration
     */
    void bootstrapPlugin(IProducerRegistry producerRegistry) throws PHPPluginBootstrapException {

        config.setConfigChangedNotifier(new ConfigChangedNotifierImpl());
        MappersRegistry mappersRegistry = new MappersRegistry();
        listener = new OnProducerDataReceivedListenerImpl(mappersRegistry, producerRegistry);

        for (MapperConfig mapperConfig : config.getMappers()) {

            try {

                mappersRegistry.registerMapper(
                        mapperConfig.getMapperId(),
                        ((Mapper) createInstance(mapperConfig.getMapperClass()))
                );

            } catch (ClassNotFoundException e) {
                throw new PHPPluginBootstrapException(
                        "Mapper class " + mapperConfig.getMapperClass() + " not found", e
                );
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new PHPPluginBootstrapException(
                        "Mapper class must have default public constructor", e
                );
            } catch (InvocationTargetException e) {
                throw new PHPPluginBootstrapException(
                        "Mapper constructor throws exception", e
                );
            } catch (InstantiationException e) {
                throw new PHPPluginBootstrapException(
                        "Failed to instance mapper of class " + mapperConfig.getMapperClass(), e
                );
            } catch (ClassCastException e) {
                throw new PHPPluginBootstrapException(
                        "Class " + mapperConfig.getMapperClass() +
                                " is not instance of " + Mapper.class.getCanonicalName(), e
                );
            }

        }

        boolean disableConnectorsInitOnStartup
                = PROPERTY_TRUE_VALUE.equals(startupProperties.getProperty(PROPERTY_CONNECTORS_DISABLED));

       configureConnectors(disableConnectorsInitOnStartup);

    }

    private void updatePlugin() {
        configureConnectors(false);
    }

    void destroyPlugin() {
        connectorsRegistry.disableAllConnectors();
    }

    private void configureConnectors(boolean disableInit) {

        for (ConnectorConfig connectorConfig : config.getConnectors()) {

            if (!connectorsRegistry.connectorExists(connectorConfig.getConnectorClass())) {

                Connector connector = null;

                try {
                    connector = createConnector(connectorConfig, listener);
                } catch (ClassNotFoundException e) {
                    log.error(
                            "Connector class " + connectorConfig.getConnectorClass() + " not found", e
                    );
                } catch (NoSuchMethodException | IllegalAccessException e) {
                    log.error(
                            "Connector class must have default public constructor", e
                    );
                } catch (InvocationTargetException e) {
                    log.error(
                            "Connector constructor throws exception", e
                    );
                } catch (InstantiationException e) {
                    log.error(
                            "Failed to instance connector of class " + connectorConfig.getConnectorClass(), e
                    );
                } catch (ClassCastException e) {
                    log.error(
                            "Class " + connectorConfig.getConnectorClass() +
                                    " is not instance of " + Connector.class.getCanonicalName(), e
                    );
                }

                connectorsRegistry.addConnector(connector);

            }

            if (!disableInit && connectorConfig.isEnabled()) {

                Properties connectorProperties = new Properties();
                connectorProperties.putAll(connectorConfig.getConnectorProperties());

                try {
                    connectorsRegistry.enableConnector(
                            connectorConfig.getConnectorClass(),
                            connectorProperties
                    );
                } catch (ConnectorInitException e) {
                    log.error("Failed to init connector " + connectorConfig.getConnectorClass(), e);
                }

            }
            else {
                connectorsRegistry.disableConnector(connectorConfig.getConnectorClass());
            }

        }

    }

    class ConfigChangedNotifierImpl implements ConfigChangedNotifier {
        public void notifyConfigChanged() {
            updatePlugin();
        }
    }

}
