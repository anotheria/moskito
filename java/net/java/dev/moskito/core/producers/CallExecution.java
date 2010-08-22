package net.java.dev.moskito.core.producers;
/**
 * Interface for a single execution object. This method is useful if you want to record a use-case which is not limited to a single method,
 * or is some code inside a method and can't be recorded via interface/method proxy or annotation.
 * @author lrosenberg
 *
 */
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
