package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;

/**
 * Notification provider that outputs threshold alerts to System.out (standard output stream).
 * This is a lightweight notification option for users who want stdout output without
 * requiring a logging framework or external notification system.
 *
 * <p><strong>Design Note:</strong> This class intentionally uses System.out.println() and
 * printStackTrace() as part of its core functionality. This is NOT a security issue but a
 * deliberate design choice to provide a simple, zero-dependency notification mechanism
 * for lightweight deployments or debugging scenarios.</p>
 *
 * <p>Use this provider when you want alerts written directly to stdout, such as when running
 * in containers where stdout is captured by the orchestration system, or for simple logging
 * to console during development.</p>
 *
 * @author lrosenberg
 * @since 24.10.12 10:22
 */
@SuppressFBWarnings(
	value = {"INFORMATION_EXPOSURE_THROUGH_AN_ERROR_MESSAGE", "THROWS_METHOD_THROWS_CLAUSE_BASIC_EXCEPTION"},
	justification = "Intentional design: this provider outputs to stdout as its primary function. " +
			"printStackTrace() is used deliberately for error handling within the notification process."
)
public class SysoutNotificationProvider  implements NotificationProvider {

	@Override
	public void configure(NotificationProviderConfig config) {
		//nothing to configure here.
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		try{
			System.out.println("NEW ThresholdAlert: "+alert);
		}catch(Exception any){
			any.printStackTrace();
		}
	}
}
