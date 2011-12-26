package net.java.dev.moskito.control.monitor.core.updater;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

import net.java.dev.moskito.control.check.ThresholdCheck;
import net.java.dev.moskito.control.monitor.core.cluster.Cluster;
import net.java.dev.moskito.control.monitor.core.connector.config.MonitoredInstance;
import net.java.dev.moskito.control.monitor.core.history.HistoryEntry;


public enum MoskitoHistoryRepository {

	INSTANCE;
	
	private static final int historyLimitSize = 15;
	
	private LinkedBlockingDeque<HistoryEntry> allInstancesThresholdsHistory = new LinkedBlockingDeque<HistoryEntry>(historyLimitSize);
	private Map<String, MonitoredInstance> currentStatusMap = new ConcurrentHashMap<String, MonitoredInstance>();
	private static List<MonitoredInstance> monitoredInstancesList = Cluster.INSTANCE.getInstanceToMonitorList();
	

	public Map<String, MonitoredInstance> getAllMoskitoContorlStatusEntries() {
		return currentStatusMap;
	}
	
	public void addStatusToRepository(StatusEntry entry) {
	
	}
	
	public void performNewStatusChecking() {

		for (MonitoredInstance instance: monitoredInstancesList) {
			
			System.out.println("Checking instance: "+instance);
			
			HistoryEntry entry = new HistoryEntry();
			ThresholdCheck check = instance.preformThresholdCheck(); //synchronize it!
			entry.setCheckResult(check);
			allInstancesThresholdsHistory.offerFirst(entry);
			currentStatusMap.put(instance.getInstanceName(), instance);
			
			System.out.println("Checked Result: "+ check);
			System.out.println("----Current size of history is: "+allInstancesThresholdsHistory.size());
		}
	}
	
	
}
