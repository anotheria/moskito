package net.anotheria.moskito.core.threshold.alerts;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.12 00:12
 */
public class DummyNotificationProvider implements NotificationProvider{
	private CountDownLatch latch;
	private static volatile DummyNotificationProvider instance;

	public DummyNotificationProvider(){
		instance = this;
	}

	@Override
	public void configure(String parameter) {
		latch = new CountDownLatch(Integer.parseInt(parameter));
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		latch.countDown();
	}

	public void await(int millis) throws InterruptedException{
		latch.await(millis, TimeUnit.MILLISECONDS);
	}

	public static DummyNotificationProvider getInstance(){
		return instance;
	}
}
