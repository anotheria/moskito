package net.java.dev.moskito.core.treshold;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

/**
 * This class contains all generated alerts at runtime. This class is an one-value-enum-singleton described by J. Bloch.
 * @author another
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
	 * The configuration.
	 */
	private AlertHistoryConfig config = new AlertHistoryConfig();
	
	/**
	 * The logger.
	 */
	private static Logger log = Logger.getLogger("MoskitoAlert");
	/**
	 * Adds a new alert. If the number of totally saved alerts is greater than AlertHistoryConfig.getToleratedNumberOfItems() the list is cut.
	 * @param alert
	 */
	public void addAlert(ThresholdAlert alert){
		log.info(alert);
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
	
	private static class AlertHistoryConfig{
		/**
		 * Returns the max number of items in the list.
		 * @return
		 */
		public final int getMaxNumberOfItems(){
			return 200;
		}
		/**
		 * Returns the max number of tolerated items in the list, this value is usually 10% above the max number of items, to reduce the amount of list cut operations. 
		 * @return
		 */
		public final int getToleratedNumberOfItems(){
			return (int)(getMaxNumberOfItems()*1.1);
		}
	}
}
