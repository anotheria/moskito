package net.anotheria.extensions.php.connectors.impl;

import com.rabbitmq.client.*;
import net.anotheria.extensions.php.connectors.JsonDataConnector;
import net.anotheria.extensions.php.exceptions.ConnectorInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * Connector to retrieve data from RabbitMQ.
 *
 * Expects that data to this connector will be sent in json
 * format strict to {@link net.anotheria.extensions.php.dto.PHPProducerDTO}
 * class structure
 *
 * Has default configuration for rabbitmq host, port, auth credentials
 * and queue name assuming RabbitMQ instance is running on same machine
 * with out of box configuration and queue name in php agent is default.
 *
 * No additional configuration for this connector is needed if
 * RabbitMQ and php agent instances satisfy requirement mentioned above.
 */
public class RabbitMQConnector extends JsonDataConnector {

    private final static Logger log = LoggerFactory.getLogger(RabbitMQConnector.class);

    /**
     * RabbitMQ connection
     */
    private Connection connection;
    /**
     * Channel used by connector to retrieve data
     */
    private Channel channel;

    @Override
    public Properties getDefaultProperties() {
        Properties properties = new Properties();

        properties.setProperty("connector.host", "localhost");
        properties.setProperty("connector.port", "5672");
        properties.setProperty("connector.username", "guest");
        properties.setProperty("connector.password", "guest");
        properties.setProperty("connector.queue-name", "moskito-php");

        return properties;
    }

    /**
     * Opens connection and channel to listen
     * configured queue for incoming data
     * @param properties configured connector properties
     * @throws ConnectorInitException on connection to RabbitMQ fail
     */
    @Override
    public void init(Properties properties) throws ConnectorInitException {

        log.debug("Starting to initialize RabbitMQ connector in php plugin...");

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(properties.getProperty("connector.host"));
        factory.setPort(
                Integer.valueOf(properties.getProperty("connector.port"))
        );
        factory.setUsername(properties.getProperty("connector.username"));
        factory.setPassword(properties.getProperty("connector.password"));

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(properties.getProperty("connector.queue-name"),
                    false, false, false, null);
            channel.basicConsume(
                    properties.getProperty("connector.queue-name"),
                    true, new MoskitoPHPConsumer(channel)
            );
        } catch (IOException | TimeoutException e) {
            deinit();
            throw new ConnectorInitException("Failed to open connection to RabbitMQ", e);
        }


    }

    /**
     * Closes RabbitMQ channel and connection
     */
    public void deinit() {

        if (channel != null) try {
            channel.close();
        } catch (IOException | TimeoutException e) {
            log.warn("Failed to close channel in RabbitMQ connector");
        }

        if (connection != null) try {
            connection.close();
        } catch (IOException e) {
            log.warn("Failed to close connection in RabbitMQ connector");
        }

    }

    /**
     * Consumer implementation for passing messages
     * to {@link JsonDataConnector#registerProducer(String)}
     */
    private class MoskitoPHPConsumer extends DefaultConsumer {

        /**
         * Constructs a new instance and records its association to the passed-in channel.
         *
         * @param channel the channel to which this consumer is attached
         */
        private MoskitoPHPConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(
                String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body
        ) throws IOException {

            String producerJson = new String(body, "UTF-8");
            registerProducer(producerJson);

        }

    }

}
