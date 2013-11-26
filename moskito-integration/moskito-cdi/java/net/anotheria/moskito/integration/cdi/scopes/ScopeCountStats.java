package net.anotheria.moskito.integration.cdi.scopes;

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

public class ScopeCountStats extends AbstractStats {

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"Cur",
			"Min",
			"Max",
			"New",
			"Del"
	));


	/**
	 * Current number of instances.
	 */
	private StatValue numberOfCurrentInstances;

	/**
	 * Min number of instances.
	 */
	private StatValue minNumberOfInstances;

	/**
	 * Max number of instances.
	 */
	private StatValue maxNumberOfInstances;


	private StatValue numberOfCreatedInstances;

	private StatValue numberOfDestroyedInstances;

	public ScopeCountStats(String name) {
		this(name, Constants.getDefaultIntervals());
	}


	public ScopeCountStats(String name, Interval[] intervals){
		super(name);
		
		Integer pattern = Integer.valueOf(0);
		
		numberOfCurrentInstances = StatValueFactory.createStatValue(StatValueTypes.COUNTER, "numberOfInstances", intervals);
		minNumberOfInstances = StatValueFactory.createStatValue(pattern, "minNumberOfInstances", intervals);
		minNumberOfInstances.setDefaultValueAsInt(Integer.MAX_VALUE);
		minNumberOfInstances.reset();
		maxNumberOfInstances = StatValueFactory.createStatValue(pattern, "maxNumberOfInstances", intervals);
		maxNumberOfInstances.setDefaultValueAsInt(Integer.MIN_VALUE);
		maxNumberOfInstances.reset();
		numberOfCreatedInstances = StatValueFactory.createStatValue(pattern, "createdInstances", intervals);
		numberOfDestroyedInstances = StatValueFactory.createStatValue(pattern, "destroyedInstances", intervals);
		
	}
	
	public void notifyInstanceCreated(){
		numberOfCreatedInstances.increase();
		
		numberOfCurrentInstances.increase();
		maxNumberOfInstances.setValueIfGreaterThanCurrentAsInt(numberOfCurrentInstances.getValueAsInt());
	}
	
	public void notifyInstanceDestroyed(){
		numberOfDestroyedInstances.increase();
		
		numberOfCurrentInstances.decrease();
		minNumberOfInstances.setValueIfLesserThanCurrentAsInt(numberOfCurrentInstances.getValueAsInt());
	}

	@Override public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder("Instances ");
		ret.append(" Cur: ").append(numberOfCurrentInstances.getValueAsInt(intervalName));
		ret.append(" Min: ").append(minNumberOfInstances.getValueAsInt(intervalName));
		ret.append(" Max: ").append(maxNumberOfInstances.getValueAsInt(intervalName));
		ret.append(" New: ").append(numberOfCreatedInstances.getValueAsInt(intervalName));
		ret.append(" Del: ").append(numberOfDestroyedInstances.getValueAsInt(intervalName));
		return ret.toString();
	}
	
	

	public int getCurrentInstanceCount(String intervalName){
		return numberOfCurrentInstances.getValueAsInt(intervalName);
	}

	public int getMinInstanceCount(String intervalName){
		return minNumberOfInstances.getValueAsInt(intervalName);
	}

	public int getMaxInstanceCount(String intervalName){
		return maxNumberOfInstances.getValueAsInt(intervalName);
	}

	public int getCreatedInstanceCount(String intervalName){
		return numberOfCreatedInstances.getValueAsInt(intervalName);
	}

	public int getDestroyedInstanceCount(String intervalName){
		return numberOfDestroyedInstances.getValueAsInt(intervalName);
	}

	@Override
	public String getValueByNameAsString(String valueName, String intervalName,
			TimeUnit timeUnit) {
		
		if (valueName==null)
			return null;
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("cur") || valueName.equals("current"))
			return ""+getCurrentInstanceCount(intervalName);
		if (valueName.equals("min"))
			return ""+getMinInstanceCount(intervalName);
		if (valueName.equals("max"))
			return ""+getMaxInstanceCount(intervalName);
		if (valueName.equals("new"))
			return ""+getCreatedInstanceCount(intervalName);
		if (valueName.equals("del"))
			return ""+getDestroyedInstanceCount(intervalName);
		
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
