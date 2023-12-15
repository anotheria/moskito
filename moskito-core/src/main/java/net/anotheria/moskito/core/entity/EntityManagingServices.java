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

            OnDemandStatsProducer<CounterStats> producer = new OnDemandStatsProducer<>(name, "business", "counter", new CounterStatsFactory());
            ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
            Accumulator acc = Accumulators.createAccumulator(name, name, "", "counter", "1h");
            try {
                acc.tieToStats(producer.getStats("counter"));
            } catch (OnDemandStatsProducerException e) {
                log.error(e.getMessage());
                continue;
            }
            executorService.scheduleAtFixedRate(new Updater(service, producer, topic), 0, 60 * 60, TimeUnit.SECONDS);
        }
    }

    /**
     * The Updater class is a Runnable responsible for updating the entity counter for a specific topic.
     */
    private static class Updater implements Runnable {

        private final EntityManagingService service;
        private final String topic;
        private final OnDemandStatsProducer<CounterStats> producer;

        Updater(EntityManagingService service, OnDemandStatsProducer<CounterStats> producer, String topic) {
            this.service = service;
            this.topic = topic;
            this.producer = producer;
        }

        public void run() {
            int entityCount = service.getEntityCount(topic);
            try {
                producer.getStats("counter").set(entityCount);
            } catch (OnDemandStatsProducerException e) {
                log.error(e.getMessage());
            }
        }
    }
}
