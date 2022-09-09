package net.anotheria.moskito.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This utility is used to perform auto-start tasks slightly after the initialization and in a separate thread.
 *
 * @author lrosenberg
 * @since 25.02.18 22:10
 */
public final class AfterStartTasks {

	/**
	 * Scheduled executor service.
	 */
	private static ScheduledExecutorService scheduledExecutorService;

	private static Logger log = LoggerFactory.getLogger(AfterStartTasks.class);

	static{
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	}
	private AfterStartTasks(){}

	public static void submitTask(Runnable r){
		submitTask(r, 10);
	}

	public static void submitTask(Runnable r, int delayInSeconds){
		log.debug("Scheduled "+r+" in "+delayInSeconds+" seconds.");
		scheduledExecutorService.schedule(r, delayInSeconds, TimeUnit.SECONDS);
	}

	/**
	 * Shuts down the internal executor. It is only necessary to prevent weird warnings in reloading context.
	 */
	public static void shutdown(){
		scheduledExecutorService.shutdown();
	}
}
