package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.Configure;

import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 16:08
 */
public class ThresholdsAlertsConfig {
	@Configure
	private NotificationProviderConfig[] notificationProviders = new NotificationProviderConfig[0];

	@Configure
	private AlertHistoryConfig alertHistoryConfig = new AlertHistoryConfig();

	@Configure private int dispatcherThreadPoolSize;

	public NotificationProviderConfig[] getNotificationProviders() {
		return notificationProviders;
	}

	public void setNotificationProviders(NotificationProviderConfig[] notificationProviders) {
		this.notificationProviders = notificationProviders;
	}

	public AlertHistoryConfig getAlertHistoryConfig() {
		return alertHistoryConfig;
	}

	public void setAlertHistoryConfig(AlertHistoryConfig alertHistoryConfig) {
		this.alertHistoryConfig = alertHistoryConfig;
	}

	/**
	 * Returns the dispatcherThreadPoolSize. To prevent from errors in pool, if the value is configured below 1, 1 is returned.
	 * @return
	 */
	public int getDispatcherThreadPoolSize() {
		return dispatcherThreadPoolSize<1 ? 1 : dispatcherThreadPoolSize;
	}

	public void setDispatcherThreadPoolSize(int dispatcherThreadPoolSize) {
		this.dispatcherThreadPoolSize = dispatcherThreadPoolSize;
	}

	@Override public String toString(){
		return "dispatcherThreadPoolSize: "+dispatcherThreadPoolSize+", alertHistoryConfig: "+alertHistoryConfig+", notificationProviders: "+ Arrays.toString(notificationProviders);
	}
}
