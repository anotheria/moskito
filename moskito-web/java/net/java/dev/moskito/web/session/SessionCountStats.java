package net.java.dev.moskito.web.session;

import static net.java.dev.moskito.core.predefined.Constants.getDefaultIntervals;
import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

public class SessionCountStats extends AbstractStats{
	/**
	 * Current number of sessions.
	 */
	private StatValue numberOfSessions;
	
	/**
	 * Min number of sessions.
	 */
	private StatValue minNumberOfSessions;

	/**
	 * Max number of sessions.
	 */
	private StatValue maxNumberOfSessions;
	

	private StatValue numberOfCreatedSessions;
	
	private StatValue numberOfDestroyedSessions;
	
	public SessionCountStats() {
		this(getDefaultIntervals());
	}
	
	public SessionCountStats(Interval[] intervals){
		super("Sessions");
		
		Integer pattern = Integer.valueOf(0);
		
		numberOfSessions = StatValueFactory.createStatValue(pattern, "numberOfSessions", intervals); 
		minNumberOfSessions = StatValueFactory.createStatValue(pattern, "minNumberOfSessions", intervals);
		minNumberOfSessions.setDefaultValueAsInt(Integer.MAX_VALUE);
		minNumberOfSessions.reset();
		maxNumberOfSessions = StatValueFactory.createStatValue(pattern, "maxNumberOfSessions", intervals); 
		maxNumberOfSessions.setDefaultValueAsInt(Integer.MIN_VALUE);
		maxNumberOfSessions.reset();
		numberOfCreatedSessions = StatValueFactory.createStatValue(pattern, "createdSessions", intervals); 
		numberOfDestroyedSessions = StatValueFactory.createStatValue(pattern, "destroyedSessions", intervals); 
		
	}
	
	public void notifySessionCreated(){
		numberOfCreatedSessions.increase();
		
		numberOfSessions.increase();
		maxNumberOfSessions.setValueIfGreaterThanCurrentAsInt(numberOfSessions.getValueAsInt());
	}
	
	public void notifySessionDestroyed(){
		numberOfDestroyedSessions.increase();
		
		numberOfSessions.decrease();
		minNumberOfSessions.setValueIfLesserThanCurrentAsInt(numberOfSessions.getValueAsInt());
	}

	@Override public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder("Sessions ");
		ret.append(" Cur: ").append(numberOfSessions.getValueAsInt(intervalName));
		ret.append(" Min: ").append(minNumberOfSessions.getValueAsInt(intervalName));
		ret.append(" Max: ").append(maxNumberOfSessions.getValueAsInt(intervalName));
		ret.append(" New: ").append(numberOfCreatedSessions.getValueAsInt(intervalName));
		ret.append(" Del: ").append(numberOfDestroyedSessions.getValueAsInt(intervalName));
		return ret.toString();
	}
	
	

	public int getCurrentSessionCount(String intervalName){
		return numberOfSessions.getValueAsInt(intervalName);
	}

	public int getMinSessionCount(String intervalName){
		return minNumberOfSessions.getValueAsInt(intervalName);
	}

	public int getMaxSessionCount(String intervalName){
		return maxNumberOfSessions.getValueAsInt(intervalName);
	}

	public int getCreatedSessionCount(String intervalName){
		return numberOfCreatedSessions.getValueAsInt(intervalName);
	}

	public int getDestroyedSessionCount(String intervalName){
		return numberOfDestroyedSessions.getValueAsInt(intervalName);
	}

	@Override
	public String getValueByNameAsString(String valueName, String intervalName,
			TimeUnit timeUnit) {
		
		if (valueName==null)
			return null;
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("cur") || valueName.equals("current"))
			return ""+getCurrentSessionCount(intervalName);
		if (valueName.equals("min"))
			return ""+getMinSessionCount(intervalName);
		if (valueName.equals("max"))
			return ""+getMaxSessionCount(intervalName);
		if (valueName.equals("new"))
			return ""+getCreatedSessionCount(intervalName);
		if (valueName.equals("del"))
			return ""+getDestroyedSessionCount(intervalName);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}
}
