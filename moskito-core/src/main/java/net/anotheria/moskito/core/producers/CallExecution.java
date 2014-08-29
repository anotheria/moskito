package net.anotheria.moskito.core.producers;
/**
 * Interface for a single execution object. This method is useful if you want to record a use-case which is not limited to a single method,
 * or is some code inside a method and can't be recorded via interface/method proxy or aop.
 * @author lrosenberg
 *
 */
public interface CallExecution {
	/**
	 * Same as startExecution(true);
	 */
	void startExecution();
	
	/**
	 * Starts the execution. If traceCall is true and a use case is running, the current progress will be stored.
	 * @param traceCall if true the call is also traced.
	 */
	void startExecution(boolean traceCall);

	/**
	 * Starts an execution. Provides trace information.
	 * @param callDescription
	 */
	void startExecution(String callDescription);

	/**
	 * Starts execution, provides option to switch the tracing on and provide call description in case tracing is on.
	 * @param traceCall if true tracing is supported.
	 * @param callDescription call description, for example method names or parameters.
	 */
	void startExecution(boolean traceCall, String callDescription);

	/**
	 * Finishes the execution.
	 */
	void finishExecution();
	
	/**
	 * Finishes the execution and notifies about the execution results for call trace.
	 * @param result
	 */
	void finishExecution(String result);

	/**
	 * Notifies that the execution encountered an error.
	 */
	void notifyExecutionError();
	/**
	 * Notifies that the execution is aborted. Same as notifyExecutionError(); finishExecution();.
	 */
	void abortExecution();
	
	/**
	 * Notifies that the execution is aborted. Same as notifyExecutionError(); finishExecution(result);.
	 */
	void abortExecution(String result);

	/**
	 * Pauses the execution.
	 */
	void pauseExecution();

	/**
	 * Resumes a previously paused execution.
	 */
	void resumeExecution();
}
