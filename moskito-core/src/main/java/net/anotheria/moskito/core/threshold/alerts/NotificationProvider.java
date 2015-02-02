package net.anotheria.moskito.core.threshold.alerts;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;

/**
 * Interface that has to be implemented to provide own notification provider. A notification provider is called
 * upon any changes in thresholds and allows you to react on that change.
 *
 * @author lrosenberg
 * @since 22.10.12 16:36
 */
public interface NotificationProvider {
	/**
	 * Called by the alert dispatcher upon creation with a configuration. Use config.getProperties() for custom
	 * properties.
	 * @param config
	 */
	public void configure(NotificationProviderConfig config);

	/**
	 * Called whenever a new alert has been created in the system.
	 * @param alert
	 */
	public void onNewAlert(ThresholdAlert alert);
}
