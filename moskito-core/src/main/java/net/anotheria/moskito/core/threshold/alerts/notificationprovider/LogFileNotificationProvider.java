package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 17:00
 */
public class LogFileNotificationProvider implements NotificationProvider{

	private Logger log;

	@Override
	public void configure(NotificationProviderConfig config) {
		log = LoggerFactory.getLogger(config.getProperties().get("appenderName"));
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		if (log==null){
			//misconfiguration!!!
			LoggerFactory.getLogger(LogFileNotificationProvider.class).warn("Logger not set, cannot log, aborted.");
			return;
		}
		log.info(""+alert);
	}
}
