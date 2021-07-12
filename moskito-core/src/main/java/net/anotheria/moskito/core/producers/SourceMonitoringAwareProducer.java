package net.anotheria.moskito.core.producers;

/**
 * Signaling interface, if this is interface is implemented the producer is capable
 * of monitoring the source of traffic - which producer caused the traffic.
 * The interface is checked by the UI to allow user to enable and disable the source monitoring.
 *
 * @author lrosenberg
 * @since 30.11.20 17:18
 */
public interface SourceMonitoringAwareProducer {
	/**
	 * Returns true if the source monitoring is currently enabled.
	 * @return
	 */
	boolean sourceMonitoringEnabled();

	/**
	 * Enables source monitoring.
	 */
	void enableSourceMonitoring();

	/**
	 * Disables source monitoring.
	 */
	void disableSourceMonitoring();
}
