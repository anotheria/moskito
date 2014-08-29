package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.10.12 10:22
 */
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
