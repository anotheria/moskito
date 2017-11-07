package net.anotheria.extensions.php.connectors;

import net.anotheria.extensions.php.OnProducerDataReceivedListener;
import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;

import java.util.Properties;

/**
 * Interface for plugin external data connectors.
 *
 * Implementations initialization should start on
 * {@link Connector#init(OnProducerDataReceivedListener, Properties)}
 * method call that be called on plugin initialization.
 * First argument is listener that must be invoked
 * by calling {@link OnProducerDataReceivedListener#updateProducer(PHPProducerDTO)}
 * method when new data is arrived. Second method argument is
 * connector properties that defined in configuration file.
 *
 * Implementations should listen to their
 * external data connections in separate thread.
 *
 * Implementations must have default public constructor
 * to be instantiated dynamically.
 */
public interface Connector {

    /**
     * Method to initialize connector.
     * Called once on plugin initialization
     *
     * @param properties connector configuration properties
     * @param listener new data arrive listener
     * @throws ConnectorInitException on initialization fails
     */
    void init(OnProducerDataReceivedListener listener, Properties properties) throws ConnectorInitException;

    /**
     * Called once on plugin deinitialization
     */
    void deinit();

}
