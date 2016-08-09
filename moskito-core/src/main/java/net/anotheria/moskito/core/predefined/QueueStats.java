package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Stats for queues.
 * @author dmetelin
 */
public class QueueStats extends AbstractStats {

	/**
	 * The definition enum.
	 */
	public static enum StatDef{
		/**
		 * Requests to the queue.
 		 */
		REQUESTS("Req"),
		/**
		 * Number of enqueued items.
		 */
		ENQUEUED("Enq"),
		/**
		 * Number of dequeued items.
		 */
		DEQUEUED("Deq"),
		/**
		 * Number of queue full status.
		 */
		FULL("Full"),
		/**
		 * Number of empty queue accesses.
		 */
		EMPTY("Empty"),
		/**
		 * The total size of the queue.
		 */
		TOTAL_SIZE("TS"),
		ENQUEUE_LAST_SIZE("ELast"),
		ENQUEUE_MIN_SIZE("EMin"),
		ENQUEUE_MAX_SIZE("EMax"),
		ENQUEUE_AVERAGE_SIZE("EAvg");
		//TODO: TBD
//		DEQUEUE_LAST_SIZE("ELast"),
//		DEQUEUE_MIN_SIZE("EMin"),
//		DEQUEUE_MAX_SIZE("EMax"),
//		DEQUEUE_AVERAGE_SIZE("EAvg"),
//		LAST_SIZE("ELast"),
//		MIN_SIZE("EMin"),
//		MAX_SIZE("EMax"),
//		AVERAGE_SIZE("EAvg");

		/**
		 * Name of the stat.
		 */
		private String statName;

		private StatDef(String aStatName){
			statName = aStatName;
		}
		
		public String getStatName(){
			return statName;
		}
		
		public String getStatLabel(){
			return ' ' + statName + ": ";
		}
		
		public static List<String> getStatNames(){
			List<String> ret = new ArrayList<>(StatDef.values().length);
			for(StatDef v: StatDef.values()) {
                ret.add(v.statName);
            }
			return ret;
		}
	}	
	
	/**
	 * Min size value.
	 */
	public static final long MIN_SIZE_DEFAULT = Long.MAX_VALUE;
	/**
	 * Max size value.
	 */
	public static final long MAX_SIZE_DEFAULT = 0L;
	
	/**
	 * Number of read requests.
	 */
	private StatValue requests;
	/**
	 * Number of en-queued elements.
	 */
	private StatValue enqueued;
	/**
	 * Number of de-queued elements.
	 */
	private StatValue dequeued;
	/**
	 * Number of times the queue was empty.
	 */
	private StatValue empty;
	/**
	 * The total possible  queue size.
	 */
	private StatValue totalSize;
	/**
	 * The last queue size (number of elements in it).
	 */
	private StatValue lastSize;
	/**
	 * Sum of queue sizes at the moment of new element additions.
	 */
	private StatValue sumOfSizes;
	/**
	 * The max queue size (number of elements in it).
	 */
	private StatValue maxSize;
	/**
	 * The min queue size (number of elements in it).
	 */
	private StatValue minSize;

	
	
	/**
	 * Name of the cache.
	 */
	private String name;

	/**
	 * Creates a new unnamed queue stats object.
	 */
	public QueueStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new queue stats object.
	 * @param name name of the queue stats object.
	 */
	public QueueStats(String name){
		this(name, Constants.getDefaultIntervals());
	}


	/**
	 * Creates a new queue stats object.
	 * @param aName
	 * @param selectedIntervals
	 */
	public QueueStats(String aName,  Interval[] selectedIntervals){
		Long longPattern = 0L;
		name = aName;
		
		requests = StatValueFactory.createStatValue(longPattern, "requests", selectedIntervals);
		enqueued = StatValueFactory.createStatValue(longPattern, "enqueued", selectedIntervals);
		dequeued = StatValueFactory.createStatValue(longPattern, "dequeued", selectedIntervals);
		empty = StatValueFactory.createStatValue(longPattern, "empty", selectedIntervals);
		totalSize = StatValueFactory.createStatValue(longPattern, "totalSize", selectedIntervals);
		lastSize = StatValueFactory.createStatValue(longPattern, "lastSize", selectedIntervals);
		sumOfSizes = StatValueFactory.createStatValue(longPattern, "sumOfSizes", selectedIntervals);
		minSize = StatValueFactory.createStatValue(longPattern, "minSize", selectedIntervals);
		minSize.setDefaultValueAsLong(Constants.MIN_TIME_DEFAULT);
		minSize.reset();
		maxSize = StatValueFactory.createStatValue(longPattern, "maxSize", selectedIntervals);
		maxSize.setDefaultValueAsLong(Constants.MAX_TIME_DEFAULT);
		maxSize.reset();

		addStatValues(requests, enqueued, dequeued, empty, totalSize, lastSize, sumOfSizes, minSize, maxSize);
		
	}
	
	public String getName(){
		return name;
	}
	
	public void addRequest(){
		requests.increase();
	}
	
	public long getRequests(String intervalName){
		return requests.getValueAsLong(intervalName);
	}
	
	public void addEnqueued(){
		enqueued.increase();
	}
	
	public long getEnqueued(String intervalName){
		return enqueued.getValueAsLong(intervalName);
	}
	
	public long getDequeued(String intervalName) {
		return dequeued.getValueAsLong(intervalName);
	}

	public void addDequeued() {
		dequeued.increase();
	}
	
	public long getEmpty(String intervalName) {
		return empty.getValueAsLong(intervalName);
	}

	public void addEmpty() {
		empty.increase();
	}

	public long getFull(String intervalName) {
		return requests.getValueAsLong(intervalName) - enqueued.getValueAsLong(intervalName);
	}

	public long getTotalSize(String intervalName) {
		return totalSize.getValueAsLong(intervalName);
	}

	public void setTotalSize(long totalSize) {
		this.totalSize.setValueAsLong(totalSize);
		
	}


	public void setOnRequestLastSize(long size) {
		this.lastSize.setValueAsLong(size);
		sumOfSizes.increaseByLong(size);
		maxSize.setValueIfGreaterThanCurrentAsLong(size);
		minSize.setValueIfLesserThanCurrentAsLong(size);
	}

	public long getOnRequestLastSize(String intervalName) {
		return lastSize.getValueAsLong(intervalName);
	}
	
	public long getOnRequestMaxSize(String intervalName) {
		return maxSize.getValueAsLong(intervalName);
	}
	
	public long getOnRequestMinSize(String intervalName) {
		return minSize.getValueAsLong(intervalName);
	}
	
	public long getOnRequestAverageSize(String intervalName) {
		long sum = sumOfSizes.getValueAsLong(intervalName);
		if(sum == 0)
			return 0;			
		return sum / requests.getValueAsLong(intervalName);
	}
	
	@Override
	public List<String> getAvailableValueNames() {
		return StatDef.getStatNames();
	}

	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
        String b = name + ' ' +
                StatDef.REQUESTS.getStatLabel() + getRequests(intervalName) +
                StatDef.ENQUEUED.getStatLabel() + getEnqueued(intervalName) +
                StatDef.FULL.getStatLabel() + getFull(intervalName) +
                StatDef.EMPTY.getStatLabel() + getEmpty(intervalName) +
                StatDef.DEQUEUED.getStatLabel() + getDequeued(intervalName) +
                StatDef.TOTAL_SIZE.getStatLabel() + getTotalSize(intervalName) +
                StatDef.ENQUEUE_LAST_SIZE.getStatLabel() + getOnRequestLastSize(intervalName) +
                StatDef.ENQUEUE_MIN_SIZE.getStatLabel() + getOnRequestMinSize(intervalName) +
                StatDef.ENQUEUE_MAX_SIZE.getStatLabel() + getOnRequestMaxSize(intervalName) +
                StatDef.ENQUEUE_AVERAGE_SIZE.getStatLabel() + getOnRequestAverageSize(intervalName);

        return b;
	}

	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		
		if (valueName.equalsIgnoreCase(StatDef.REQUESTS.getStatName()))
				return String.valueOf(getRequests(intervalName));
				
		if (valueName.equalsIgnoreCase(StatDef.ENQUEUED.getStatName()))
			return String.valueOf(getEnqueued(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.DEQUEUED.getStatName()))
			return String.valueOf(getDequeued(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.FULL.getStatName()))
			return String.valueOf(getFull(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.EMPTY.getStatName()))
			return String.valueOf(getEmpty(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.TOTAL_SIZE.getStatName()))
			return String.valueOf(getTotalSize(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.ENQUEUE_LAST_SIZE.getStatName()))
			return String.valueOf(getOnRequestLastSize(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.ENQUEUE_MIN_SIZE.getStatName()))
			return String.valueOf(getOnRequestMinSize(intervalName));
		
		if (valueName.equalsIgnoreCase(StatDef.ENQUEUE_MAX_SIZE.getStatName()))
			return String.valueOf(getOnRequestMaxSize(intervalName));
		
		if (valueName.equals(StatDef.ENQUEUE_AVERAGE_SIZE.getStatName()))
			return String.valueOf(getOnRequestAverageSize(intervalName));

		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}





}
