package net.anotheria.extensions.php.connectors;

import net.anotheria.extensions.php.OnProducerDataReceivedListener;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;

import java.util.Properties;

/**
 * Interface for plugin external data connectors.
 *
 * Connector final classes should have default public
 * constructor to be instantiated dynamically.
 *
 * Connector should not to start listening on instance
 * creation.
 *
 * First {@link Connector#setOnProducerDataReceivedListener(OnProducerDataReceivedListener)}
 * be called on plugin initialization.
 *
 * {@link Connector#init(Properties)} method is called when connector
 * need to start listening external data.
 * {@link Connector#deinit()} called when connector need to be shut down
 * and stop to supply new producers data.
 *
 * init and deinit methods can be invoked several times due plugin work,
 * so connector should have ability to be initialized again after deinitialization.
 * deinit method is never called on uninitialized connectors.
 *
 */
public interface Connector {

    /**
     * Method to initWithDefaultProperties connector.
     * Called once on plugin initialization
     *
     * @param properties connector configuration properties
     * @throws ConnectorInitException on initialization fails
     */
    void init(Properties properties) throws ConnectorInitException;

    /**
     * Called once on plugin deinitialization
     */
    void deinit();

    void setOnProducerDataReceivedListener(OnProducerDataReceivedListener listener);

}
