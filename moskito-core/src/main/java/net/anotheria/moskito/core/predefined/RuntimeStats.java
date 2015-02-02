package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats object that corresponds to JMX RuntimeMbean.
 * @author another
 *
 */
public class RuntimeStats extends AbstractStats {

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"Process",
			"Starttime",
			"Uptime"
	));


	/**
	 * Name of the process.
	 */
	private StatValue processName;
	/**
	 * Start timestamp of the process/runtime.
	 */
	private StatValue startTime;
	/**
	 * Uptime of the process.
	 */
	private StatValue uptime;
	
	public RuntimeStats(){
		this("Runtime", Constants.getDefaultIntervals());
	} 
	
	public RuntimeStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	} 

	public RuntimeStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		processName = StatValueFactory.createStatValue("", "processName", selectedIntervals);
		startTime = StatValueFactory.createStatValue(0L, "startTime", selectedIntervals); 
		uptime = StatValueFactory.createStatValue(0L, "uptime", selectedIntervals);

		addStatValues(processName, startTime, uptime);
	}
	
	public void update(String aName, long aStartTime, long anUptime){
		processName.setValueAsString(aName);
		startTime.setValueAsLong(aStartTime);
		uptime.setValueAsLong(anUptime);
	}

	@Override
	public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder();
		
		ret.append(getName()).append(' ');
		ret.append(" process: ").append(processName.getValueAsString(intervalName));
		ret.append(" starttime: ").append(startTime.getValueAsLong(intervalName));
		ret.append(" uptime: ").append(uptime.getValueAsInt(intervalName));
		
		return ret.toString();
	}
	
	@Override
	public String getValueByNameAsString(String valueName, String intervalName,
			TimeUnit timeUnit) {
		
		if (valueName==null)
			throw new AssertionError("Value name can't be null");
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("process") || valueName.equals("name") || valueName.equals("processname"))
			return processName.getValueAsString(intervalName);
		if (valueName.equals("starttime"))
			return ""+getStartTime(intervalName);
		if (valueName.equals("uptime"))
			return ""+getUptime(intervalName);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	public String getProcessName(String intervalName){
		return processName.getValueAsString(intervalName);
	}
	
	public long getStartTime(String intervalName){
		return startTime.getValueAsLong(intervalName);
	}
	
	public long getUptime(String intervalName){
		return uptime.getValueAsLong(intervalName);
	}

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}


}
