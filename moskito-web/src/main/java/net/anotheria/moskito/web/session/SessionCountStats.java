package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SessionCountStats extends AbstractStats {

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"Cur",
			"Min",
			"Max",
			"New",
			"Del"
	));


	/**
	 * Current number of sessions.
	 */
	private StatValue numberOfCurrentSessions;
	
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
		this("Sessions", Constants.getDefaultIntervals());
	}


	public SessionCountStats(String name, Interval[] intervals){
		super(name);
		
		Integer pattern = Integer.valueOf(0);
		
		numberOfCurrentSessions = StatValueFactory.createStatValue(StatValueTypes.COUNTER, "numberOfSessions", intervals);
		minNumberOfSessions = StatValueFactory.createStatValue(pattern, "minNumberOfSessions", intervals);
		minNumberOfSessions.setDefaultValueAsInt(Integer.MAX_VALUE);
		minNumberOfSessions.reset();
		maxNumberOfSessions = StatValueFactory.createStatValue(pattern, "maxNumberOfSessions", intervals); 
		maxNumberOfSessions.setDefaultValueAsInt(Integer.MIN_VALUE);
		maxNumberOfSessions.reset();
		numberOfCreatedSessions = StatValueFactory.createStatValue(pattern, "createdSessions", intervals); 
		numberOfDestroyedSessions = StatValueFactory.createStatValue(pattern, "destroyedSessions", intervals);

		addStatValues(numberOfCreatedSessions, maxNumberOfSessions, minNumberOfSessions, numberOfCreatedSessions, numberOfDestroyedSessions);
		
	}
	
	public void notifySessionCreated(){
		numberOfCreatedSessions.increase();
		
		numberOfCurrentSessions.increase();
		maxNumberOfSessions.setValueIfGreaterThanCurrentAsInt(numberOfCurrentSessions.getValueAsInt());
	}
	
	public void notifySessionDestroyed(){
		numberOfDestroyedSessions.increase();
		
		numberOfCurrentSessions.decrease();
		minNumberOfSessions.setValueIfLesserThanCurrentAsInt(numberOfCurrentSessions.getValueAsInt());
	}

	@Override public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder("Sessions ");
		ret.append(" Cur: ").append(numberOfCurrentSessions.getValueAsInt(intervalName));
		ret.append(" Min: ").append(minNumberOfSessions.getValueAsInt(intervalName));
		ret.append(" Max: ").append(maxNumberOfSessions.getValueAsInt(intervalName));
		ret.append(" New: ").append(numberOfCreatedSessions.getValueAsInt(intervalName));
		ret.append(" Del: ").append(numberOfDestroyedSessions.getValueAsInt(intervalName));
		return ret.toString();
	}
	
	

	public int getCurrentSessionCount(String intervalName){
		return numberOfCurrentSessions.getValueAsInt(intervalName);
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

	@Override
	public String toString(){
		return toStatsString();
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

}
