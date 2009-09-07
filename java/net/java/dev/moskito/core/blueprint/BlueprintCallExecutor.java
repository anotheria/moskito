package net.java.dev.moskito.core.blueprint;

/**
 * This interface defines the executor for a call on a monitored blueprinted object. Whenever the surrounding code wants to call a method on a monitorable
 * blueprinted object, it calls the appropriate blueprint producer, which performs measurements and updates the use-case tracking and afterwards passes the control to an 
 * instance of the executor. 
 * @author lrosenberg
 *
 */
public interface BlueprintCallExecutor {
	/**
	 * Called by the producer. Passes all parameters.
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public Object execute(Object... parameters) throws Exception;
}
