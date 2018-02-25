package net.anotheria.moskito.core.config.errorhandling;

/**
 * Target for the error catcher, defines if memory or logfile or none should be used.
 *
 * @author lrosenberg
 * @since 04.06.17 14:43
 */
public enum ErrorCatcherTarget {
	/**
	 * Log errors, don't keep in memory.
	 */
	LOG(true, false),
	/**
	 * Keep errors in memory, don't log.
	 */
	MEMORY(false, true),
	/**
	 * Log and keep in memory.
	 */
	LOGANDMEMORY(true, true),
	/**
	 * Custom mode, a standard error catcher won't do anything here, but it can be used to configure own error catchers.
	 */
	CUSTOM(false, false);


	/**
	 * If true we should log the error.
	 */
	private boolean doLog;
	/**
	 * If true the error stays in memory.
	 */
	private boolean doKeepInMemory;

	ErrorCatcherTarget(boolean aDoLog, boolean aDoKeepInMemory){
		doLog = aDoLog;
		doKeepInMemory = aDoKeepInMemory;
	}

	public boolean keepInMemory(){
		return doKeepInMemory;
	}

	public boolean log(){
		return doLog;
	}


}
