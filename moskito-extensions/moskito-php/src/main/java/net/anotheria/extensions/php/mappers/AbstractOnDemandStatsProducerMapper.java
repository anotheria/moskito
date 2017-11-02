package net.anotheria.extensions.php.mappers;

import net.anotheria.extensions.php.dto.PHPProducerDTO;
import net.anotheria.extensions.php.dto.PHPStatsDTO;
import net.anotheria.extensions.php.exceptions.MappingException;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract mapper to handle {@link OnDemandStatsProducer} class
 * producers.
 *
 * Has defined {@link Mapper#mapProducer(IProducerRegistry, PHPProducerDTO)}
 * method. But it is required to define {@link AbstractOnDemandStatsProducerMapper#getStatsFactory()}
 * and {@link AbstractOnDemandStatsProducerMapper#updateStats(IStats, StatsValues)}
 * methods in child mappers instead.
 *
 * Due to OnDemandStatsProducer instances can have different stats classes
 * this class checks that generic type of producer from repository stats
 * be same as generic type of child mapper.
 *
 * @param <S> stats class that mapper used
 */
public abstract class AbstractOnDemandStatsProducerMapper<S extends IStats> implements Mapper {

    private static final Logger log = LoggerFactory.getLogger(AbstractOnDemandStatsProducerMapper.class);

    /**
     * Updates stats instance using given incoming data
     *
     * @param stats stats instance to update
     * @param values new data to update stats
     * @throws MappingException on invalid incoming stats data
     */
    public abstract void updateStats(S stats, StatsValues values) throws MappingException;

    /**
     * Returns factory for mapper stats
     * @return  factory for mapper stats
     */
    public abstract IOnDemandStatsFactory<S> getStatsFactory();

    /**
     * Class of stats used by mapper.
     * Required to check is incoming producer can
     * be updated using this mapper
     */
    private Class<S> statsClass;

    /**
     * Constructor with stats class argument
     * @param statsClass class of stats that can be updated by this mapper
     */
    public AbstractOnDemandStatsProducerMapper(Class<S> statsClass) {
        this.statsClass = statsClass;
    }

    /**
     * Casts producer of raw type from producers repository
     * to {@link OnDemandStatsProducer} with producer stats class
     * check.
     *
     * @param producer producer to be casted
     * @return casted producer
     * @throws ClassCastException if producer is not instance of {@link OnDemandStatsProducer}
     *                            or generic type of producer stats is not same as type of mapper
     */
    @SuppressWarnings("unchecked")
    // Justification : producer generic type is checked manually at runtime
    private OnDemandStatsProducer<S> safeCastProducer(IStatsProducer producer) throws ClassCastException {

        OnDemandStatsProducer onDemandStatsTypedProducer =
                (OnDemandStatsProducer)producer;

        // Checking is generic type of producer is corresponds to generic type of mapper
        // By retrieving producer default stats and checking it type
        if(!(statsClass.isInstance(onDemandStatsTypedProducer.getDefaultStats())))
            throw new ClassCastException(
                    "Producer do not have generic type of " +
                            statsClass.getCanonicalName() + " required by mapper"
            );

        return onDemandStatsTypedProducer;

    }

    @Override
    public void mapProducer(IProducerRegistry producerRegistry, PHPProducerDTO producerDTO) throws MappingException {

        log.debug("Starting to map producer with id '{}'...", producerDTO.getProducerId());

        OnDemandStatsProducer<S> producer;
        IStatsProducer producerUncasted = producerRegistry.getProducer(producerDTO.getProducerId());

        if (producerUncasted != null){
            try {
                producer = safeCastProducer(producerUncasted);
            } catch (ClassCastException e) {
                log.error("Cannot update producer data. Producer with id '" +
                        producerDTO.getProducerId() +
                        "' actual type is differs from mapper requirements type.", e);
                return;
            }
        }
        else{

            log.debug("Producer with id '{}' is not found in repository. " +
                            "Creating new producer with category '{}', subsystem '{}' and stats class '{}'...",
                    producerDTO.getProducerId(), producerDTO.getCategory(),
                    producerDTO.getSubsystem(), statsClass.getName()
            );

            producer = new OnDemandStatsProducer<>(
                    producerDTO.getProducerId(),
                    producerDTO.getCategory(),
                    producerDTO.getSubsystem(),
                    getStatsFactory()
            );
            producerRegistry.registerProducer(producer);

        }

        for (PHPStatsDTO statsDTO : producerDTO.getStats()) {

            try {

                StatsValues statsValues = new StatsValues(statsDTO.getValues());

                this.updateStats(
                        producer.getStats(statsDTO.getName()),
                        statsValues
                );
                this.updateStats(producer.getDefaultStats(), statsValues);

            } catch (OnDemandStatsProducerException e) {
                log.error("Failed to get stats with name '" + statsDTO.getName() + "' from producer", e);
            } catch (MappingException | IllegalArgumentException e) {
                log.error("Failed to map stats with name '" + statsDTO.getName() + "'", e);
            }

        }

    }

}
