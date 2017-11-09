package net.anotheria.extensions.php.connectors.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.rabbitmq.client.*;
import net.anotheria.extensions.php.connectors.AbstractConnector;
import net.anotheria.extensions.php.dto.PHPProducerDTO;
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
 * format with strict to {@link net.anotheria.extensions.php.dto.PHPProducerDTO}
 * class structure
 *
 * Has default configuration for rabbitmq host, port, auth credentials
 * and queue name assuming RabbitMQ instance is running on same host
 * with out of box configuration and queue name in php agent is default.
 *
 * No additional configuration for this connector is needed if
 * RabbitMQ and php agent instances satisfy requirement mentioned above.
 */
public class RabbitMQConnector extends AbstractConnector {

    private final static Logger log = LoggerFactory.getLogger(RabbitMQConnector.class);

    private static final Gson gson = new GsonBuilder().create();

    /**
     * RabbitMQ connection
     */
    private Connection connection;
    /**
     * Channel used by connector to retrieve data
     */
    private Channel channel;

    private long enabledInTimestamp;

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
    public void initWithDefaultProperties(Properties properties) throws ConnectorInitException {

        log.debug("Starting to initWithDefaultProperties RabbitMQ connector in php plugin...");

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
            enabledInTimestamp = System.currentTimeMillis();
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
     * that parsing incoming json message and pass it
     * to {@link AbstractConnector#updateProducer(PHPProducerDTO)}
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

            PHPProducerDTO producerDTO;

            try {
                producerDTO = gson.fromJson(new String(body, "UTF-8"), PHPProducerDTO.class);
            } catch (JsonSyntaxException e) {
                log.error("Failed to parse incoming json data.", e);
                return;
            }

            if((producerDTO.getTimestamp() * 1000) > enabledInTimestamp) {
                updateProducer(producerDTO);
            }

        }

    }

}
