package net.java.dev.moskito.util.threadhistory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.management.MXBean;

public enum ThreadHistoryUtility {
	INSTANCE;
	
	private boolean isActive;
	private Timer timer;
	/**
	 * Update interval, once a minute. We make it configurable for tests. Its volatile since it can be changed by another thread.
	 */
	private volatile long updateInterval = 1000L*60;
	
	private HashSet<Long> runningThreadIds = new HashSet<Long>();
	
	/**
	 * ThreadMXBean reference.
	 */
	private final ThreadMXBean mxBean = ManagementFactory.getThreadMXBean();
	/**
	 * Number of events to held in queue. Default is 1000. Volatile, because another change than the timer changes it.
	 */
	private volatile int maxEventsSize = 1000;
	
	private ArrayList<ThreadHistoryEvent> eventList = new ArrayList<ThreadHistoryEvent>();
	
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
			for (Long oldId : oldIds.toArray(PATTERN)){
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
 