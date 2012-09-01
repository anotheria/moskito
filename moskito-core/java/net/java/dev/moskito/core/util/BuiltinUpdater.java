package net.java.dev.moskito.core.util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Helper utility to provide updates for builtin producers. This updater is triggered once per minute.
 */
public class BuiltinUpdater {
	/**
	 * Timer instance.
	 */
	private static final Timer timer = new Timer("MoSKito Builtin-Updater", true);

	/**
	 * Adds a new task.
	 * @param task
	 */
	public static void addTask(TimerTask task){
		timer.scheduleAtFixedRate(task, 0, 1000L*60);
	}

	/**
	 * Stops the timer allowing users in container to cleanup on shutdown/restart.
	 */
	public static void cleanup(){
		timer.cancel();
	}
}
