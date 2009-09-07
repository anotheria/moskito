package net.java.dev.moskito.core.producers;

public interface CallExecution {
	/**
	 * Same as startExecution(true);
	 */
	void startExecution();
	
	/**
	 * Starts the execution. If recordUseCase is true and a use case is running, the current progress will be stored.
	 * @param recordUseCase
	 */
	void startExecution(boolean recordUseCase);
	/**
	 * Finishes the execution.
	 */
	void finishExecution();
	/**
	 * Notifies that the execution encountered an error.
	 */
	void notifyExecutionError();
	/**
	 * Notifies that the execution is aborted. Same as notifyExecutionError(); finishExecution();.
	 */
	void abortExecution();
}
