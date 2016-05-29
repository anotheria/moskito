package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Base class for memory pool stats.
 * @author lrosenberg
 */
public abstract class AbstractMemoryPoolStats extends AbstractStats implements IMemoryPoolStats{

	/**
	 * Creates a new AbstractMemoryPoolStats object.
	 * @param name
	 */
	protected AbstractMemoryPoolStats(String name){
		super(name);
	}
	
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"INIT",
			"MIN_USED",
			"USED",
			"MAX_USED",
			"MIN_COMMITED",
			"COMMITED",
			"MAX_COMMITED",
			"MAX"));

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}
	
	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.isEmpty())
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("init"))
			return String.valueOf(getInit(intervalName));
		if (valueName.equals("min_used"))
			return String.valueOf(getMinUsed(intervalName));
		if (valueName.equals("used"))
			return String.valueOf(getUsed(intervalName));
		if (valueName.equals("used mb"))
			return String.valueOf(getUsed(intervalName) / MB);
		if (valueName.equals("max_used"))
			return String.valueOf(getMaxUsed(intervalName));
		if (valueName.equals("min_commited"))
			return String.valueOf(getMinCommited(intervalName));
		if (valueName.equals("commited"))
			return String.valueOf(getCommited(intervalName));
		if (valueName.equals("max_commited"))
			return String.valueOf(getMaxCommited(intervalName));
		if (valueName.equals("max"))
			return String.valueOf(getMax(intervalName));
		if (valueName.equals("free"))
			return String.valueOf(getFree(intervalName));
		if (valueName.equals("free mb"))
			return String.valueOf(getFree(intervalName) / MB);
		
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

}
