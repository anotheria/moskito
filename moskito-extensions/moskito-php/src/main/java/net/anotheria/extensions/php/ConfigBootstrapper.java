package net.anotheria.extensions.php;

import net.anotheria.extensions.php.config.ConnectorConfig;
import net.anotheria.extensions.php.config.MapperConfig;
import net.anotheria.extensions.php.config.MoskitoPHPConfig;
import net.anotheria.extensions.php.connectors.Connector;
import net.anotheria.extensions.php.exceptions.PHPPluginBootstrapException;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;
import net.anotheria.extensions.php.mappers.Mapper;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Processes Moskito PHP plugin configuration.
 * Loads mappers and connectors and wires them together
 */
class ConfigBootstrapper {

    private final static Logger log = LoggerFactory.getLogger(ConfigBootstrapper.class);

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

    /**
     * Bootstraps Moskito PHP plugin
     *
     * @param config source of plugin
     * @param producerRegistry producer registry to use
     * @return list of registered connectors
     * @throws PHPPluginBootstrapException on invalid configuration
     */
    static List<Connector> bootstrapPlugin(
            MoskitoPHPConfig config, IProducerRegistry producerRegistry
    ) throws PHPPluginBootstrapException {

        MappersRegistry mappersRegistry = new MappersRegistry();
        ProducerUpdater updater = new ProducerUpdater(mappersRegistry, producerRegistry);
        List<Connector> registeredConnectors = new LinkedList<>();

        for (MapperConfig mapperConfig : config.getMappers()) {

            try {

                mappersRegistry.registerMapper(
                        mapperConfig.getMapperId(),
                        ((Mapper) createInstance(mapperConfig.getMapperClass()))
                );

            } catch (ClassNotFoundException e) {
                throw new PHPPluginBootstrapException(
                        "AbstractMapper class " + mapperConfig.getMapperClass() + " not found", e
                );
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new PHPPluginBootstrapException(
                        "AbstractMapper class must have default public constructor", e
                );
            } catch (InvocationTargetException e) {
                throw new PHPPluginBootstrapException(
                        "AbstractMapper constructor throws exception", e
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

        for (ConnectorConfig connectorConfig : config.getConnectors()) {

            Connector connector;

            try {
                connector = ((Connector) createInstance(connectorConfig.getConnectorClass()));
            } catch (ClassNotFoundException e) {
                throw new PHPPluginBootstrapException(
                        "Connector class " + connectorConfig.getConnectorClass() + " not found", e
                );
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new PHPPluginBootstrapException(
                        "Connector class must have default public constructor", e
                );
            } catch (InvocationTargetException e) {
                throw new PHPPluginBootstrapException(
                        "Connector constructor throws exception", e
                );
            } catch (InstantiationException e) {
                throw new PHPPluginBootstrapException(
                        "Failed to instance connector of class " + connectorConfig.getConnectorClass(), e
                );
            } catch (ClassCastException e) {
                throw new PHPPluginBootstrapException(
                        "Class " + connectorConfig.getConnectorClass() +
                                " is not instance of " + Connector.class.getCanonicalName(), e
                );
            }

            Properties connectorProperties = new Properties(connector.getDefaultProperties());
            connectorProperties.putAll(connectorConfig.getConnectorProperties());
            connector.setProducerUpdater(updater);

            try {
                connector.init(connectorProperties);
                registeredConnectors.add(connector);
            } catch (ConnectorInitException e) {
                log.error("Failed to initialize connector " + connectorConfig.getConnectorClass(), e);
            }

        }

        return registeredConnectors;

    }

}
