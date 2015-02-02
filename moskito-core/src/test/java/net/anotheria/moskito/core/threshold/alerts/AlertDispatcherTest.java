package net.anotheria.moskito.core.threshold.alerts;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.12 00:05
 */
public class AlertDispatcherTest {
	@Test public void testDispatchingOfAlerts() throws Exception{
		//prepare config
		MoskitoConfiguration config = new MoskitoConfiguration();
		config.getThresholdsAlertsConfig().getAlertHistoryConfig().setMaxNumberOfItems(200);
		config.getThresholdsAlertsConfig().getAlertHistoryConfig().setToleratedNumberOfItems(220);

		config.getThresholdsAlertsConfig().setDispatcherThreadPoolSize(1);

		NotificationProviderConfig[] providers = new NotificationProviderConfig[4];
		providers[0] = new NotificationProviderConfig();
		providers[0].setClassName(TestNotificationProvider.class.getName());
		providers[0].setGuardedStatus(ThresholdStatus.YELLOW.name());
		providers[1] = new NotificationProviderConfig();
		providers[1].setClassName(TestNotificationProvider.class.getName());
		providers[1].setGuardedStatus(ThresholdStatus.ORANGE.name());
		providers[2] = new NotificationProviderConfig();
		providers[2].setClassName(TestNotificationProvider.class.getName());
		providers[2].setGuardedStatus(ThresholdStatus.RED.name());
		providers[3] = new NotificationProviderConfig();
		providers[3].setClassName(DummyNotificationProvider.class.getName());
		providers[3].setProperty("count", "3");

		config.getThresholdsAlertsConfig().setNotificationProviders(providers);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(config);

		//now for the test
		ThresholdDefinition td = new ThresholdDefinition();
		td.setName("AlertDispatcherTest");
		Threshold testT = new Threshold(td);
		ThresholdAlert a1 = new ThresholdAlert(testT, ThresholdStatus.GREEN, ThresholdStatus.YELLOW, "1", null, 0 );
		ThresholdAlert a2 = new ThresholdAlert(testT, ThresholdStatus.YELLOW, ThresholdStatus.ORANGE, "2", null, 0);
		ThresholdAlert a3 = new ThresholdAlert(testT, ThresholdStatus.ORANGE, ThresholdStatus.RED, "3", null, 0 );
		AlertDispatcher dispatcher = AlertDispatcher.INSTANCE;
		dispatcher.reset();//enforce dispatcher to reread its config
		dispatcher.dispatchAlert(a1);
		dispatcher.dispatchAlert(a2);
		dispatcher.dispatchAlert(a3);
		DummyNotificationProvider.getInstance().await(1000);
		//we expect 3+2+1 alerts
		assertEquals(6, TestNotificationProvider.getAlertCount());

	}

	@After public void cleanup(){
		MoskitoConfigurationHolder.resetConfiguration();
	}

}