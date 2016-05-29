package net.anotheria.moskito.core.util.threadhistory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ThreadHistoryUtility for detection of thread creation and deletion. 
 * Once activated the ThreadHistoryUtility checks in an interval of default 1 minute (customizeable) if new thread has been created or an existing one expired (deleted).
 * This is helpful to detect thread leakage.  
 * @author lrosenberg
 */
public enum ThreadHistoryUtility {
	/**
	 * Singleton instance.
	 */
	INSTANCE;
	/**
	 * If true the history utility is active.
	 */
	private volatile boolean isActive;
	/**
	 * Internal timer.
	 */
	private Timer timer;
	/**
	 * Update interval, once a minute. We make it configurable for tests. Its volatile since it can be changed by another thread.
	 */
	private volatile long updateInterval = 1000L*60;
	/**
	 * Ids of all currently running threads.
	 */
	private Set<Long> runningThreadIds = new HashSet<>();
	
	/**
	 * ThreadMXBean reference.
	 */
	private final ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
	/**
	 * Number of events to held in queue. Default is 1000. Volatile, because another change than the timer changes it.
	 */
	private volatile int maxEventsSize = 1000;
	
	/**
	 * The storage for history events.
	 */
	private List<ThreadHistoryEvent> eventList = new ArrayList<>();
	
	/**
	 * Internal synchronization lock.
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public boolean isActive(){
		return isActive;
	}
	
	public void activate(){
		if (isActive)
			return;
		
		timer = new Timer("MoskitoThreadHistoryUpdater", true);
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				update();
			}
		}, 0, updateInterval);
		isActive = true;
	}
	
	public void deactivate(){
		if (timer!=null){
			timer.cancel();
			timer = null;
		}
		isActive = false;
	}
	
	/**
	 * Pattern for arraycopy.
	 */
	private static final Long[] PATTERN = new Long[0];

	private void update(){
		lock.writeLock().lock();
		try{
			long[] ids = mxBean.getAllThreadIds();
			HashSet<Long> oldIds = (HashSet<Long> )runningThreadIds.clone();
			for (long _id : ids){
				Long id = _id;
				oldIds.remove(id);
				if (!runningThreadIds.contains(id)){
					ThreadHistoryEvent event = ThreadHistoryEvent.created(_id, mxBean.getThreadInfo(_id).getThreadName());
					runningThreadIds.add(id);
					eventList.add(event);
				}
			}
			//now check deleted
			for (Long oldId : oldIds.toArray(new Long[oldIds.size()])){
				ThreadHistoryEvent event = ThreadHistoryEvent.deleted(oldId, "");
				runningThreadIds.remove(oldId);
				eventList.add(event);
			}
			
			if (eventList.size()>maxEventsSize){
				List<ThreadHistoryEvent> oldList = eventList;
				eventList = new ArrayList<ThreadHistoryEvent>(maxEventsSize);
				eventList.addAll(maxEventsSize/10, oldList);
			}
			
		}finally{
			lock.writeLock().unlock();
		}
	}
	
	/**
	 * Returns the list of history events. Returns the clone of internal list.
	 * @return
	 */
	public List<ThreadHistoryEvent> getThreadHistoryEvents(){
		ArrayList<ThreadHistoryEvent> ret = null;
		lock.readLock().lock();
		try{
			ret = (ArrayList<ThreadHistoryEvent>) eventList.clone();
		}finally{
			lock.readLock().unlock();
		}
		return ret;
	}
	
	/**
	 * This method is only for unit-testing purposes.
	 * @param aValue
	 */
	void setUpdateInterval(long aValue){
		updateInterval = aValue;
	}

	public int getMaxEventsSize() {
		return maxEventsSize;
	}

	public void setMaxEventsSize(int maxEventsSize) {
		this.maxEventsSize = maxEventsSize;
	}
	
	public long getUpdateInterval(){
		return updateInterval;
	}
}
