package net.anotheria.moskito.core.entity;

import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.Accumulators;
import net.anotheria.moskito.core.counter.CounterStats;

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
            CounterStats c = new CounterStats(name);
            Accumulator acc = Accumulators.createAccumulator(name, name, "", "counter", "1h");
            acc.tieToStats(c);
            executorService.scheduleAtFixedRate(new Updater(service, c, topic), 10, 60 * 60, TimeUnit.SECONDS);
        }
    }

    /**
     * The Updater class is a Runnable responsible for updating the entity counter for a specific topic.
     */
    private static class Updater implements Runnable {

        private final EntityManagingService service;
        private final CounterStats counter;
        private final String topic;

        Updater(EntityManagingService service, CounterStats counter, String topic) {
            this.service = service;
            this.counter = counter;
            this.topic = topic;
        }

        public void run() {
            int entityCount = service.getEntityCount(topic);
            counter.set(entityCount);
        }
    }
}
