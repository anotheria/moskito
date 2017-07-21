package net.anotheria.moskito.core.config.errorhandling;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 04.06.17 14:43
 */
public enum ErrorCatcherTarget {
	LOG(true, false),
	MEMORY(false, true),
	LOGANDMEMORY(true, true);

	private boolean doLog;
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
