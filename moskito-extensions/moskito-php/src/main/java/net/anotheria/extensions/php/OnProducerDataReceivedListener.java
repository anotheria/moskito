package net.anotheria.extensions.php;

import net.anotheria.extensions.php.dto.PHPProducerDTO;

/**
 * Interface for connectors callback
 * on new incoming producers data
 */
public interface OnProducerDataReceivedListener {

    /**
     * Called by connectors when new producer data arrived
     * @param producerDTO producer data
     */
    void updateProducer(PHPProducerDTO producerDTO);

}
