package net.anotheria.moskito.integration.ehcache;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class provides periodical updates of stats given in the task.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see net.anotheria.moskito.core.util.BuiltinUpdater
 */
public class PeriodicStatsUpdater {

    /**
     * Timer instance.
     */
    private static final Timer timer = new Timer("MoSKito Periodic Stats Updater", true);


    /**
     * Adds a new task with default period that starts immediately.
     *
     * @param task {@link TimerTask} to add
     */
    public static void addTask(final TimerTask task) {
        addTask(task, 0, 1000L*60);
    }

    /**
     * Adds a new task that starts immediately.
     *
     * @param task   {@link TimerTask} to add
     * @param period period of task execution starts in milliseconds.
     */
    public static void addTask(final TimerTask task, final long period) {
        addTask(task, 0, period);
    }

    /**
     * Adds a new task.
     *
     * @param task   {@link TimerTask} to add
     * @param delay  delay in milliseconds before task is to be executed.
     * @param period period of task execution starts in milliseconds.
     */
    public static void addTask(final TimerTask task, final long delay, final long period) {
        timer.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * Stops the timer allowing users in container to cleanup on shutdown/restart.
     */
    public static void cleanup() {
        timer.cancel();
    }

}
