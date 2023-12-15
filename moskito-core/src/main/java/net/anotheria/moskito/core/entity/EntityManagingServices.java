package net.anotheria.moskito.core.entity;

import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.CounterStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The {@code EntityManagingServices} class provides utility methods for managing entity counters.
 * It includes a method to create and schedule entity counters using a {@link ScheduledExecutorService}.
 *
 * @author asamoilich
 */
public class EntityManagingServices {
    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(EntityManagingServices.class);
    /**
     * The {@code ScheduledExecutorService} used for scheduling entity counter updates.
     */
    private static final ScheduledExecutorService executorService;

    private static final String ENTITY_COUNTER_PRODUCER_ID = "EntityCounterProducerId";

    static {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Creates and schedules entity counters for the specified topics using the provided {@link EntityManagingService}.
     * Each counter is associated with a specific topic, and updates are scheduled periodically.
     *
     * @param service The {@link EntityManagingService} used to retrieve entity counts.
     * @param topics  The topics for which entity counters will be created and scheduled.
     */
    public static void createEntityCounter(EntityManagingService service, String... topics) {
        for (String topic : topics) {
            String name = service.getClass().getSimpleName() + ".Entity" + topic + "Counter";

            OnDemandStatsProducer<CounterStats> producer = (OnDemandStatsProducer<CounterStats>) ProducerRegistryFactory.getProducerRegistryInstance().getProducer(ENTITY_COUNTER_PRODUCER_ID);
            if (producer == null) {
                producer = new OnDemandStatsProducer<>(ENTITY_COUNTER_PRODUCER_ID, "business", "counter", new CounterStatsFactory());
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
            }
            Accumulator acc = Accumulators.createAccumulator(name, ENTITY_COUNTER_PRODUCER_ID, "", "counter", "1m");
            try {
                acc.tieToStats(producer.getStats(name));
            } catch (OnDemandStatsProducerException e) {
                log.error(e.getMessage());
                continue;
            }
            executorService.scheduleAtFixedRate(new Updater(service, producer, name, topic), 0, 60, TimeUnit.SECONDS);
        }
    }

    /**
     * The Updater class is a Runnable responsible for updating the entity counter for a specific topic.
     */
    private static class Updater implements Runnable {

        private final EntityManagingService service;
        private final String topic;
        private final OnDemandStatsProducer<CounterStats> producer;
        private final String name;

        Updater(EntityManagingService service, OnDemandStatsProducer<CounterStats> producer, String name, String topic) {
            this.service = service;
            this.topic = topic;
            this.producer = producer;
            this.name = name;
        }

        public void run() {
            int entityCount = service.getEntityCount(topic);
            try {
                producer.getStats(name).set(entityCount);
                producer.getDefaultStats().inc();
            } catch (OnDemandStatsProducerException e) {
                log.error(e.getMessage());
            }
        }
    }
}
