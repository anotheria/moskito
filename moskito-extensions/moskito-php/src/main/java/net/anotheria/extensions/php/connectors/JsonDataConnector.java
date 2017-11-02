package net.anotheria.extensions.php.connectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.anotheria.extensions.php.dto.PHPProducerDTO;

/**
 * Abstract connector for receiving producer data
 * in json format with strict to {@link PHPProducerDTO}
 * structure
 */
public abstract class JsonDataConnector extends Connector {

    private static final Gson gson = new GsonBuilder().create();

    /**
     * Additional method for applying producer data
     * using producer and it stats json representation
     * @param producerJson json representation of producer data
     */
    protected void registerProducer(String producerJson) {
        PHPProducerDTO producerDTO = gson.fromJson(producerJson, PHPProducerDTO.class);
        registerProducer(producerDTO);
    }

}
