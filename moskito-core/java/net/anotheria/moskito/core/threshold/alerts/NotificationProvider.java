package net.anotheria.moskito.core.threshold.alerts;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 16:36
 */
public interface NotificationProvider {
	/**
	 * Called by the alert dispatcher upon creation with a parameter configured by the config.
	 * @param parameter
	 */
	public void configure(String parameter);

	/**
	 * Called whenever a new alert has been created in the system.
	 * @param alert
	 */
	public void onNewAlert(ThresholdAlert alert);
}
