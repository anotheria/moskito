package net.anotheria.extensions.php;

import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.extensions.php.mappers.Mapper;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used by connectors to load incoming producer data.
 */
public class ProducerUpdater {

    private static final Logger log = LoggerFactory.getLogger(ProducerUpdater.class);

    /**
     * Store of available mappers in this updater
     */
    private final MappersRegistry mappersRegistry;
    /**
     * Used producers repository
     */
    private final IProducerRegistry producerRegistry;

    /**
     * Package scope constructor for creating instance only on
     * plugin load.
     *
     * @param mappersRegistry mappers registry instance to be used by updated
     * @param producerRegistry producer registry to be used by updated
     */
    ProducerUpdater(MappersRegistry mappersRegistry, IProducerRegistry producerRegistry) {
        this.mappersRegistry = mappersRegistry;
        this.producerRegistry = producerRegistry;
    }

    /**
     * Processes incoming producer data.
     *
     * @param producerDTO incoming producer data to be submitted
     */
    public void updateProducer(PHPProducerDTO producerDTO) {

        synchronized (producerRegistry) {

            Mapper mapper = mappersRegistry.getMapper(producerDTO.getMapperId());

            if (mapper == null) {
                log.error("Mapper with id " + producerDTO.getMapperId() + " is not found to map producer "
                        + producerDTO.getProducerId());
                return;
            }

            try {
                mapper.mapProducer(producerRegistry, producerDTO);
            } catch (MappingException e) {
                log.error("Failed to process producer data with producer id " +
                        producerDTO.getProducerId() +
                        " because mapper throws an exception", e);
            }

        }

    }

}
