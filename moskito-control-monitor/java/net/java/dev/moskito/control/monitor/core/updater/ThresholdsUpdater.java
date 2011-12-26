package net.java.dev.moskito.control.monitor.core.updater;

import java.util.Timer;
import java.util.TimerTask;

public class ThresholdsUpdater implements MonitoredInstanceUpdater {
	
	public ThresholdsUpdater() {
		System.out.println("ThresholdsUpdter startiong.......");
	}
	
	private static MoskitoHistoryRepository curentStatusRepository = MoskitoHistoryRepository.INSTANCE; 
	
	public void startInstanceStatusUpdating() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					updateThresholdStatusRepository();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 1000L * 1 * 1); //every 1 seconds update is calling. 
		
	}
	private void updateThresholdStatusRepository() {
		System.out.println("Thresholds updater is working. Now - updating.");
		curentStatusRepository.performNewStatusChecking();
		
	}

	public static void main(String[] a) {
		new ThresholdsUpdater().startInstanceStatusUpdating();
	}
}
