package net.java.dev.moskito.core.predefined;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.TimeUnit;

public abstract class AbstractMemoryPoolStats extends AbstractStats implements IMemoryPoolStats{
	
	protected AbstractMemoryPoolStats(String name){
		super(name);
	}
	
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			new String[]{
				"INIT",
				"MIN_USED",
				"USED",
				"MAX_USED",
				"MIN_COMMITED",
				"COMMITED",
				"MAX_COMMITED",
				"MAX",
			})); 

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}
	
	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		if (valueName.equals("init"))
			return ""+getInit(intervalName);
		if (valueName.equals("min_used"))
			return ""+getMinUsed(intervalName);
		if (valueName.equals("used"))
			return ""+getUsed(intervalName);
		if (valueName.equals("max_used"))
			return ""+getMaxUsed(intervalName);
		if (valueName.equals("min_commited"))
			return ""+getMinCommited(intervalName);
		if (valueName.equals("commited"))
			return ""+getCommited(intervalName);
		if (valueName.equals("max_commited"))
			return ""+getMaxCommited(intervalName);
		if (valueName.equals("max"))
			return ""+getMax(intervalName);
		if (valueName.equals("free"))
			return ""+getFree(intervalName);
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

}
