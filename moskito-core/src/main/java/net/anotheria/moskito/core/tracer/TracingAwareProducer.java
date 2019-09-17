package net.anotheria.moskito.core.tracer;

/**
 * This interface should be implemented by a producer that supports tracing.
 *
 * @author lrosenberg
 * @since 05.05.15 15:27
 */
public interface TracingAwareProducer {
	/**
	 * Returns true if tracing is supported by this producer.
	 * @return
	 */
	boolean tracingSupported();
}
