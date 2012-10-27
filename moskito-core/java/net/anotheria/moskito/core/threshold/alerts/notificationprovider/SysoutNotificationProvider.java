package net.anotheria.moskito.core.treshold.alerts.notificationprovider;

import net.anotheria.moskito.core.treshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.treshold.alerts.ThresholdAlert;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.10.12 10:22
 */
public class SysoutNotificationProvider  implements NotificationProvider {

	@Override
	public void configure(String parameter) {
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
