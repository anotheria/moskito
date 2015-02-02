package net.anotheria.moskito.core.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Helper utility to provide updates for builtin producers. This updater is triggered once per
 * minute.
 */
public class BuiltinUpdater {

    /**
     * Timer instance.
     */
    private static final Timer timer = new Timer("MoSKito Builtin-Updater", true);

    /**
     * Adds a new task.
     * 
     * @param task
     *            {@link TimerTask} to add
     */
    public static void addTask(final TimerTask task) {
        addTask(task, 0);
    }

    /**
     * Adds a new task.
     * 
     * @param delay
     *            delay in milliseconds before task is to be executed.
     * @param task
     *            {@link TimerTask} to add
     */
    public static void addTask(final TimerTask task, final long delay) {
        timer.scheduleAtFixedRate(task, delay, 1000L * 60);
    }

    /**
     * Stops the timer allowing users in container to cleanup on shutdown/restart.
     */
    public static void cleanup() {
        timer.cancel();
    }
}
