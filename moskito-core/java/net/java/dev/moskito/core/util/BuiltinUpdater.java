package net.java.dev.moskito.core.util;

import java.util.Timer;
import java.util.TimerTask;

public class BuiltinUpdater {
	/**
	 * Timer instance.
	 */
	private static final Timer timer = new Timer("MoSKito Builtin-Updater", true);

	public static void addTask(TimerTask task){
		timer.scheduleAtFixedRate(task, 0, 1000L*60);
	}
}
