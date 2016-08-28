package net.anotheria.moskito.core.config.thresholds;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This configuration class holds all info about threshold alerts.
 *
 * @author lrosenberg
 * @since 22.10.12 16:08
 */
public class ThresholdsAlertsConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -7425421359284453771L;

	/**
	 * Notification providers for notification upon threshold status changes.
	 */
	@Configure
	private NotificationProviderConfig[] notificationProviders = new NotificationProviderConfig[0];

	/**
	 * Config for the embedded alert history.
	 */
	@Configure
	@SerializedName("@alertHistoryConfig")
	private AlertHistoryConfig alertHistoryConfig = new AlertHistoryConfig();

	/**
	 * Thread pool size of the alert dispatcher proceeding queue.
	 */
	@Configure private int dispatcherThreadPoolSize;

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public NotificationProviderConfig[] getNotificationProviders() {
		return notificationProviders;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP2")
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
