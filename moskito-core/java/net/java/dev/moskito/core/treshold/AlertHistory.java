package net.java.dev.moskito.core.treshold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public enum AlertHistory {
	INSTANCE;
	//its ok to return the original list, since all operations create a copy. The only thing we need to care about is 
	//simulataneous add, and that is guarded by the lock.
	
	private List<ThresholdAlert> alerts = new CopyOnWriteArrayList<ThresholdAlert>();
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private AlertHistoryConfig config = new AlertHistoryConfig();
	
	public void addAlert(ThresholdAlert alert){
		System.out.println("LOG ENTRY "+alert);
		lock.writeLock().lock();
		try{
			alerts.add(alert);
			if (alerts.size()>config.getToleratedNumberOfItems()){
				alerts = alerts.subList(alerts.size()-config.getMaxNumberOfItems(), alerts.size()-1);
			}
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	public List<ThresholdAlert> getAlerts(){
		List<ThresholdAlert> ret = new ArrayList<ThresholdAlert>();
		ret.addAll(alerts);
		Collections.reverse(ret);
		return ret;
	}
	
	private class AlertHistoryConfig{
		public final int getMaxNumberOfItems(){
			return 200;
		}
		
		public final int getToleratedNumberOfItems(){
			return (int)(getMaxNumberOfItems()*1.1);
		}
	}
}
