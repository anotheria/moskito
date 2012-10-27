package net.anotheria.moskito.core.threshold.alerts;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class contains all generated alerts at runtime. This class is an one-value-enum-singleton described by J. Bloch.
 * @author lrosenberg
 *
 */
public enum AlertHistory {
	/**
	 * Singleton instance.
	 */
	INSTANCE;
	//its ok to return the original list, since all operations create a copy. The only thing we need to care about is 
	//simulataneous add, and that is guarded by the lock.
	/**
	 * List of alerts.
	 */
	private List<ThresholdAlert> alerts = new CopyOnWriteArrayList<ThresholdAlert>();
	/**
	 * Lock for write operations.
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Adds a new alert. If the number of totally saved alerts is greater than AlertHistoryConfig.getToleratedNumberOfItems() the list is cut.
	 * @param alert
	 */
	public void addAlert(ThresholdAlert alert){
		lock.writeLock().lock();
		try{
			alerts.add(alert);
			if (alerts.size()>MoskitoConfigurationHolder.getConfiguration().getThresholdsAlertsConfig().getAlertHistoryConfig().getToleratedNumberOfItems()){
				alerts = alerts.subList(alerts.size()-MoskitoConfigurationHolder.getConfiguration().getThresholdsAlertsConfig().getAlertHistoryConfig().getMaxNumberOfItems(), alerts.size()-1);
			}
		}finally{
			lock.writeLock().unlock();
		}
	}
	/**
	 * Returns the alerts sofar.
	 * @return
	 */
	public List<ThresholdAlert> getAlerts(){
		List<ThresholdAlert> ret = new ArrayList<ThresholdAlert>();
		ret.addAll(alerts);
		Collections.reverse(ret);
		return ret;
	}
}
