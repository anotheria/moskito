package net.anotheria.extensions.php.mappers;

import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.moskito.core.registry.IProducerRegistry;

/**
 * Interface for producer mappers.
 *
 * Final mappers must have default public constructor to be used
 */
public interface Mapper {

    /**
     * Updates producer in given
     * registry with given stats data.
     *
     * Creates and registers new producer if producer with given id not exists.
     *
     * @param producerRegistry registry of producers to update
     * @param producerDTO new producers stats data to be applied
     */
    void mapProducer(IProducerRegistry producerRegistry, PHPProducerDTO producerDTO) throws MappingException;

}
