package net.anotheria.extensions.php.connectors;

import net.anotheria.extensions.php.ProducerUpdater;
import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;

import java.util.Properties;

/**
 * Basic class for connectors.
 *
 * Children classes must have default public constructor to be loaded.
 */
public abstract class Connector {

    /**
     * Instance of producer updater
     * to invoke
     */
    private ProducerUpdater producerUpdater;

    /**
     * Initialization method of connector
     * Called on plugin initialization
     * @param properties configured connector properties
     * @throws ConnectorInitException on init fail
     */
    public abstract void init(Properties properties) throws ConnectorInitException;

    /**
     * Called on plugin deintialization
     */
    public void deinit() {}

    /**
     * Used to define default connector
     * configuration properties
     * @return default connector configuration properties
     */
    public Properties getDefaultProperties() {
        return new Properties();
    }

    /**
     * Connectors should call this method on incoming
     * data to process it by mappers.
     *
     * @param producerDTO new incoming producer data
     */
    protected void registerProducer(PHPProducerDTO producerDTO) {
        producerUpdater.updateProducer(producerDTO);
    }

    public void setProducerUpdater(ProducerUpdater producerUpdater) {
        this.producerUpdater = producerUpdater;
    }

}
