package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.Configure;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 16:08
 */
public class ThresholdsAlertsConfig {
	@Configure
	private NotificationProvider[] notificationProviders = new NotificationProvider[0];

	@Configure
	private AlertHistoryConfig alertHistoryConfig = new AlertHistoryConfig();

	@Configure private int dispatcherThreadPoolSize;

	public NotificationProvider[] getNotificationProviders() {
		return notificationProviders;
	}

	public void setNotificationProviders(NotificationProvider[] notificationProviders) {
		this.notificationProviders = notificationProviders;
	}

	public AlertHistoryConfig getAlertHistoryConfig() {
		return alertHistoryConfig;
	}

	public void setAlertHistoryConfig(AlertHistoryConfig alertHistoryConfig) {
		this.alertHistoryConfig = alertHistoryConfig;
	}

	public int getDispatcherThreadPoolSize() {
		return dispatcherThreadPoolSize;
	}

	public void setDispatcherThreadPoolSize(int dispatcherThreadPoolSize) {
		this.dispatcherThreadPoolSize = dispatcherThreadPoolSize;
	}
}
