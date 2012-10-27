package net.anotheria.moskito.core.treshold.alerts.provider;

import net.anotheria.moskito.core.treshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.treshold.alerts.ThresholdAlert;
import org.apache.log4j.Logger;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 17:00
 */
public class LogFileNotificationProvider implements NotificationProvider{

	private Logger log;

	@Override
	public void configure(String parameter) {
		log = Logger.getLogger(parameter);
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		if (log==null){
			//misconfiguration!!!
			Logger.getLogger(LogFileNotificationProvider.class).warn("Logger not set, cannot log, aborted.");
			return;
		}
		log.info(alert);
	}
}
