package net.anotheria.extensions.php.connectors;

import net.anotheria.extensions.php.OnProducerDataReceivedListener;
import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;

import java.util.Properties;

/**
 * Partial implementation of connector interface.
 * Has {@link Connector#init(Properties)}
 * implementation that saves listener to an instance field
 * and calls abstract {@link AbstractConnector#initWithDefaultProperties(Properties)}
 * method that must be defined in final connector implementations.
 *
 * Listener invoking is available by calling {@link AbstractConnector#updateProducer(PHPProducerDTO)}
 * method.
 *
 * Has {@link AbstractConnector#getDefaultProperties()}
 * method that may return connector default properties values
 * that been added to init(Properties) method argument
 *
 */
public abstract class AbstractConnector implements Connector {

    /**
     * Instance of data listener to be used
     */
    private OnProducerDataReceivedListener listener;

    /**
     * Initialization method of connector
     * Called on plugin initialization
     * @param properties configured connector properties
     * @throws ConnectorInitException on init fail
     */
    public abstract void initWithDefaultProperties(Properties properties) throws ConnectorInitException;

    public void setOnProducerDataReceivedListener(OnProducerDataReceivedListener listener) {
        this.listener = listener;
    }

    @Override
    public void init(Properties configuredProperties) throws ConnectorInitException {
        Properties properties = new Properties(getDefaultProperties());
        properties.putAll(configuredProperties);

        initWithDefaultProperties(properties);
    }

    /**
     * Has empty body to enable method implementation skip
     */
    @Override
    public void deinit() {}

    /**
     * Used to define default connector configuration properties
     * @return default connector configuration properties
     */
    public Properties getDefaultProperties() {
        return new Properties();
    }

    /**
     * Connectors implementations should call this method on incoming
     * data to process it by mappers.
     *
     * @param producerDTO new incoming producer data
     */
    protected void updateProducer(PHPProducerDTO producerDTO) {
        listener.updateProducer(producerDTO);
    }

}
