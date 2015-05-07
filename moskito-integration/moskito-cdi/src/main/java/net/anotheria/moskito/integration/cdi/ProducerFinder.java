package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

/**
 * Util for producer lookup.
 *
 * @author Alex Osadchy
 */
public final class ProducerFinder {

    /**
     * Private constructor.
     */
    private ProducerFinder() {}

    /**
     * Returns {@link OnDemandStatsProducer} by given parameters.
     *
     * @param producerId producer id
     * @param category category
     * @param subsystem subsystem
     * @param factory {@link IOnDemandStatsFactory}
     * @param <T> type of the stats (counter/service)
     *
     * @return {@link OnDemandStatsProducer}
     */
    public static <T extends IStats> OnDemandStatsProducer<T> getProducer(String producerId, String category, String subsystem, IOnDemandStatsFactory<T> factory){
        IStatsProducer<T> producer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(producerId);

        if (producer == null) {
            producer = new OnDemandStatsProducer<T>(producerId, category, subsystem, factory);
            ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
        }

        return (OnDemandStatsProducer<T>) producer;
    }

}
