package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;

/**
 * Notification provider that outputs threshold alerts to System.err (standard error stream).
 * This is a lightweight notification option for users who want stderr output without
 * requiring a logging framework or external notification system.
 *
 * <p><strong>Design Note:</strong> This class intentionally uses System.err.println() and
 * printStackTrace() as part of its core functionality. This is NOT a security issue but a
 * deliberate design choice to provide a simple, zero-dependency notification mechanism
 * for lightweight deployments or debugging scenarios.</p>
 *
 * <p>Use this provider when you want alerts written directly to stderr, such as when running
 * in containers where stderr is captured by the orchestration system, or for quick debugging.</p>
 *
 * @author lrosenberg
 * @since 24.10.12 10:22
 */
@SuppressFBWarnings(
	value = {"INFORMATION_EXPOSURE_THROUGH_AN_ERROR_MESSAGE", "THROWS_METHOD_THROWS_CLAUSE_BASIC_EXCEPTION"},
	justification = "Intentional design: this provider outputs to stderr as its primary function. " +
			"printStackTrace() is used deliberately for error handling within the notification process."
)
public class SyserrNotificationProvider implements NotificationProvider {
	@Override
	public void configure(NotificationProviderConfig config) {

	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		try{
			System.err.println("NEW ThresholdAlert: "+alert);
		}catch(Exception any){
			any.printStackTrace();
		}
	}
}
