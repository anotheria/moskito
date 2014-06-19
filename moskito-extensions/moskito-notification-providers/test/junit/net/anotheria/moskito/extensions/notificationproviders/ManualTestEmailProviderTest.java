package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.alerts.AlertDispatcher;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This test is used for manual testing of the email notificationprovider.
 * It shouldn't be in unit tests to prevent spam.
 *
 * @author lrosenberg
 * @since 23.10.12 16:05
 */
public class ManualTestEmailProviderTest {

    @Ignore
	@Test public void generateMailConfigAndTriggerMail() throws Exception{
		//prepare config
		MoskitoConfiguration config = new MoskitoConfiguration();

		config.getThresholdsAlertsConfig().setDispatcherThreadPoolSize(1);

		NotificationProviderConfig[] providers = new NotificationProviderConfig[1];
		providers[0] = new NotificationProviderConfig();
		providers[0].setClassName(MailgunNotificationProvider.class.getName());
		//providers[0].setParameter("leon@leon-rosenberg.net,rosenberg.leon@gmail.com, michael.schuetz@anotheria.net");

        providers[0].setProperty("recipients","leon@leon-rosenberg.net,rosenberg.leon@gmail.com,michael.schuetz@anotheria.net");
		providers[0].setProperty("templateUrl", "template.htm");
		providers[0].setGuardedStatus(ThresholdStatus.YELLOW.name());
		/*providers[3] = new NotificationProviderConfig();
		providers[3].setClassName(DummyNotificationProvider.class.getName());
		providers[3].setParameter("1");*/

		config.getThresholdsAlertsConfig().setNotificationProviders(providers);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(config);

		//now for the test
		ThresholdDefinition td = new ThresholdDefinition();
		td.setName("TEST");
		Threshold testT = new Threshold(td);
		ThresholdAlert a1 = new ThresholdAlert(testT, ThresholdStatus.GREEN, ThresholdStatus.YELLOW, "1", null, 0);
		AlertDispatcher dispatcher = AlertDispatcher.INSTANCE;
		dispatcher.dispatchAlert(a1);
		Thread.currentThread().sleep(3000);

		System.out.println("Check the mailbox now");

	}
}
