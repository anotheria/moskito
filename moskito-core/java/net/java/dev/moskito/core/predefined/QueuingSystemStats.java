package net.java.dev.moskito.core.predefined;

import static net.java.dev.moskito.core.predefined.Constants.MAX_TIME_DEFAULT;
import static net.java.dev.moskito.core.predefined.Constants.MIN_TIME_DEFAULT;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

public class QueuingSystemStats extends AbstractStats{

	/**
	 * Stat definition enum.
	 */
	static enum StatDef{
		
		SERVERS_SIZE("SS"),
		/**
		 * Size of the queue.
		 */
		QUEUE_SIZE("QS"),
		/**
		 * Number of arrived elements,
		 */
		ARRIVED("Arr"),
		/**
		 * Number of served elements.
		 */
		SERVICED("Serv"),
		/**
		 * Number of errors.
		 */
		ERRORS("Err"),
		/**
		 * Number of waiting times.
		 */
		WAITED("Wait"),
		/**
		 * Number of thrown away elements.
		 */
		THROWN_AWAY("Thr"),
		WAITING_TIME("WT"),
		WAITING_TIME_MIN("WTm"),
		WAITING_TIME_MAX("WTM"),
		WAITING_TIME_AVG("WTA"),
		SERVICE_TIME("ST"),
		SERVICE_TIME_MIN("STm"),
		SERVICE_TIME_MAX("STM"),
		SERVICE_TIME_AVG("STA");

		/**
		 * Name of the stat object.
		 */
		private String statName;

		private StatDef(String aStatName){
			statName = aStatName;
		}
		
		public String getStatName(){
			return statName;
		}
		
		public String getStatLabel(){
			return " " + statName + ": ";
		}
		
		public static List<String> getStatNames(){
			List<String> ret = new ArrayList<String>(StatDef.values().length);
			for(StatDef v: StatDef.values())
				ret.add(v.getStatName());
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
	
	
	private StatValue serversSize;
	private StatValue queueSize;
	private StatValue arrived;
	private StatValue serviced;
	private StatValue errors;
	private StatValue waited; 
	private StatValue throwedAway;
	private StatValue waitingTime;
	private StatValue waitingTimeMin;
	private StatValue waitingTimeMax;
	private StatValue servicingTime;
	private StatValue servicingTimeMin;
	private StatValue servicingTimeMax;

	
	/**
	 * Name of the cache.
	 */
	private String name;

	public QueuingSystemStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}
	
	public QueuingSystemStats(String name){
		this(name, Constants.getDefaultIntervals());
	} 
	
	
	public QueuingSystemStats(String aName,  Interval[] selectedIntervals){
		Long longPattern = Long.valueOf(0);
		name = aName;
		
		serversSize = StatValueFactory.createStatValue(longPattern, "serversSize", selectedIntervals);
		queueSize = StatValueFactory.createStatValue(longPattern, "queueSize", selectedIntervals);
		arrived = StatValueFactory.createStatValue(longPattern, "arrived", selectedIntervals);
		serviced = StatValueFactory.createStatValue(longPattern, "serviced", selectedIntervals);
		errors = StatValueFactory.createStatValue(longPattern, "errors", selectedIntervals);
		waited = StatValueFactory.createStatValue(longPattern, "waited", selectedIntervals);
		throwedAway = StatValueFactory.createStatValue(longPattern, "throwedAway", selectedIntervals);
		waitingTime = StatValueFactory.createStatValue(longPattern, "waitingTime", selectedIntervals);
		waitingTimeMin = StatValueFactory.createStatValue(longPattern, "waitingTimeMin", selectedIntervals);
		waitingTimeMin.setDefaultValueAsLong(MIN_TIME_DEFAULT);
		waitingTimeMin.reset();
		waitingTimeMax = StatValueFactory.createStatValue(longPattern, "waitingTimeMax", selectedIntervals);
		waitingTimeMax.setDefaultValueAsLong(MAX_TIME_DEFAULT);
		waitingTimeMax.reset();
		servicingTime = StatValueFactory.createStatValue(longPattern, "servicingTime", selectedIntervals);
		servicingTimeMin = StatValueFactory.createStatValue(longPattern, "servicingTimeMin", selectedIntervals);
		servicingTimeMin.setDefaultValueAsLong(MIN_TIME_DEFAULT);
		servicingTimeMin.reset();
		servicingTimeMax = StatValueFactory.createStatValue(longPattern, "servicingTimeMax", selectedIntervals);
		servicingTimeMax.setDefaultValueAsLong(MAX_TIME_DEFAULT);
		servicingTimeMax.reset();
	
	}
	
	public void setServersSize(int size){
		serversSize.setValueAsLong(size);
	}
	
	public long getServersSize(String intervalName){
		return serversSize.getValueAsLong();
	}
	
	public void setQueueSize(int size){
		queueSize.setValueAsLong(size);
	}
	
	public long getQueueSize(String intervalName){
		return queueSize.getValueAsLong();
	}
	
	public void addArrived(){
		arrived.increase();
	}
	
	public long getArrived(String intervalName){
		return arrived.getValueAsLong(intervalName);
	}
	
	public void addServiced(){
		serviced.increase();
	}
	
	public long getServiced(String intervalName){
		return serviced.getValueAsLong(intervalName);
	}
	
	public void addError(){
		errors.increase();
	}
	
	public long getErrors(String intervalName){
		return errors.getValueAsLong(intervalName);
	}
	
	public void addWaited(){
		waited.increase();
	}
	
	public long getWaited(String intervalName){
		return waited.getValueAsLong(intervalName);
	}
	
	public void addThrowedAway(){
		throwedAway.increase();
	}
	
	public long getThrowedAway(String intervalName){
		return throwedAway.getValueAsLong(intervalName);
	}
	
	public void addWaitingTime(long time){
		waitingTime.increaseByLong(time);
		waitingTimeMin.setValueIfLesserThanCurrentAsLong(time);
		waitingTimeMax.setValueIfGreaterThanCurrentAsLong(time);
	}
	
	public long getWaitingTime(String intervalName){
		return waitingTime.getValueAsLong(intervalName);
	}
	
	public long getWaitingTimeMin(String intervalName){
		return waitingTimeMin.getValueAsLong(intervalName);
	}
	
	public long getWaitingTimeMax(String intervalName){
		return waitingTimeMax.getValueAsLong(intervalName);
	}
	
	public long getWaitingTimeAverage(String intervalName){
		long waitedVal = waited.getValueAsLong(intervalName);
		return waitedVal > 0? waitingTime.getValueAsLong(intervalName) / waitedVal: Constants.AVERAGE_TIME_DEFAULT;
	}
	
	public void addServicingTime(long time){
		servicingTime.increaseByLong(time);
		servicingTimeMin.setValueIfLesserThanCurrentAsLong(time);
		servicingTimeMax.setValueIfGreaterThanCurrentAsLong(time);
	}
	
	public long getServicingTime(String intervalName){
		return servicingTime.getValueAsLong(intervalName);
	}
	
	public long getServicingTimeMin(String intervalName){
		return servicingTimeMin.getValueAsLong(intervalName);
	}
	
	public long getServicingTimeMax(String intervalName){
		return servicingTimeMax.getValueAsLong(intervalName);
	}
	
	public long getServicingTimeAverage(String intervalName){
		long servicedVal = serviced.getValueAsLong(intervalName);
		return servicedVal > 0? servicingTime.getValueAsLong(intervalName) / servicedVal: Constants.AVERAGE_TIME_DEFAULT;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public List<String> getAvailableValueNames() {
		return StatDef.getStatNames();
	}

	private String transformMinTimeNanos(long minTime, TimeUnit timeUnit){
		if (minTime == Constants.MIN_TIME_DEFAULT)
			return "NoR";
			
		return "" + timeUnit.transformNanos(minTime);
	}
	
	private String transformMaxTimeNanos(long maxTime, TimeUnit timeUnit){
		if (maxTime == Constants.MAX_TIME_DEFAULT)
			return "NoR";
			
		return "" + timeUnit.transformNanos(maxTime);
	}
	
	private String transformAveraeTimeNanos(long maxTime, TimeUnit timeUnit){
		if (maxTime == Constants.AVERAGE_TIME_DEFAULT)
			return "NoR";
			
		return "" + timeUnit.transformNanos(maxTime);
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		
		b.append(StatDef.SERVERS_SIZE.getStatLabel()).append(getServersSize(intervalName));
		b.append(StatDef.QUEUE_SIZE.getStatLabel()).append(getQueueSize(intervalName));
		b.append(StatDef.ARRIVED.getStatLabel()).append(getArrived(intervalName));
		b.append(StatDef.SERVICED.getStatLabel()).append(getServiced(intervalName));
		b.append(StatDef.ERRORS.getStatLabel()).append(getErrors(intervalName));
		b.append(StatDef.WAITED.getStatLabel()).append(getWaited(intervalName));
		b.append(StatDef.THROWN_AWAY.getStatLabel()).append(getThrowedAway(intervalName));
		b.append(StatDef.WAITING_TIME.getStatLabel()).append(timeUnit.transformNanos(getWaitingTime(intervalName)));
		b.append(StatDef.WAITING_TIME_MIN.getStatLabel()).append(transformMinTimeNanos(getWaitingTimeMin(intervalName), timeUnit));
		b.append(StatDef.WAITING_TIME_MAX.getStatLabel()).append(transformMaxTimeNanos(getWaitingTimeMax(intervalName),timeUnit));
		b.append(StatDef.WAITING_TIME_AVG.getStatLabel()).append(transformAveraeTimeNanos(getWaitingTimeAverage(intervalName), timeUnit));
		b.append(StatDef.SERVICE_TIME.getStatLabel()).append(timeUnit.transformNanos(getServicingTime(intervalName)));
		b.append(StatDef.SERVICE_TIME_MIN.getStatLabel()).append(transformMinTimeNanos(getServicingTimeMin(intervalName), timeUnit));
		b.append(StatDef.SERVICE_TIME_MAX.getStatLabel()).append(transformMaxTimeNanos(getServicingTimeMax(intervalName),timeUnit));
		b.append(StatDef.SERVICE_TIME_AVG.getStatLabel()).append(transformAveraeTimeNanos(getServicingTimeAverage(intervalName), timeUnit));

		return b.toString();
	}

	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		
		if (valueName.equals(StatDef.SERVERS_SIZE.getStatName()))
			return ""+getServersSize(intervalName);
		
		if (valueName.equals(StatDef.QUEUE_SIZE.getStatName()))
			return ""+getQueueSize(intervalName);
		
		if (valueName.equals(StatDef.ARRIVED.getStatName()))
			return ""+getArrived(intervalName);
		
		if (valueName.equals(StatDef.SERVICED.getStatName()))
			return ""+getServiced(intervalName);
		
		if (valueName.equals(StatDef.ERRORS.getStatName()))
			return ""+getErrors(intervalName);
		
		if (valueName.equals(StatDef.WAITED.getStatName()))
			return ""+getWaited(intervalName);
		
		if (valueName.equals(StatDef.THROWN_AWAY.getStatName()))
			return ""+getThrowedAway(intervalName);
		
		if (valueName.equals(StatDef.WAITING_TIME.getStatName()))
			return ""+timeUnit.transformNanos(getWaitingTime(intervalName));
		
		if (valueName.equals(StatDef.WAITING_TIME_MIN.getStatName()))
			return transformMinTimeNanos(getWaitingTimeMin(intervalName), timeUnit);
		
		if (valueName.equals(StatDef.WAITING_TIME_MAX.getStatName()))
			return transformMaxTimeNanos(getWaitingTimeMax(intervalName), timeUnit);
		
		if (valueName.equals(StatDef.WAITING_TIME_AVG.getStatName()))
			return transformAveraeTimeNanos(getWaitingTimeAverage(intervalName), timeUnit);
		
		if (valueName.equals(StatDef.SERVICE_TIME.getStatName()))
			return ""+timeUnit.transformNanos(getServicingTime(intervalName));
		
		if (valueName.equals(StatDef.SERVICE_TIME_MIN.getStatName()))
			return transformMinTimeNanos(getServicingTimeMin(intervalName), timeUnit);
		
		if (valueName.equals(StatDef.SERVICE_TIME_MAX.getStatName()))
			return transformMaxTimeNanos(getServicingTimeMax(intervalName), timeUnit);
		
		if (valueName.equals(StatDef.SERVICE_TIME_AVG.getStatName()))
			return transformAveraeTimeNanos(getServicingTimeAverage(intervalName), timeUnit);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

}
