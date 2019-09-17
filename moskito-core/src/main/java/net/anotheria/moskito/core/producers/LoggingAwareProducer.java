package net.anotheria.moskito.core.producers;

/**
 * This interface signals that the producer may support logging of method calls.
 *
 * @author lrosenberg
 * @since 2019-08-20 11:34
 */
public interface LoggingAwareProducer {
	/**
	 * Returns true if logging is currently enabled.
	 * @return
	 */
	boolean isLoggingEnabled();

	/**
	 * Returns true if the logging is supported by this producer.
	 * @return
	 */
	boolean isLoggingSupported();


	/**
	 * Enables logging of all methods calls.
	 */
	void enableLogging();

	/**
	 * Disables logging of all method calls.
	 */
	void disableLogging();

}
