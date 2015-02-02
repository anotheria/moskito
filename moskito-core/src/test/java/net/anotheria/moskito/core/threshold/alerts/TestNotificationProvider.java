package net.anotheria.moskito.core.threshold.alerts;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.12 00:07
 */
public class TestNotificationProvider  implements NotificationProvider{

	ThresholdAlert lastAlert;

	private static AtomicLong alertCounter = new AtomicLong(0);

	@Override
	public void configure(NotificationProviderConfig config) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		lastAlert = alert;
		alertCounter.incrementAndGet();
	}

	public static long getAlertCount(){
		return alertCounter.get();
	}
}

