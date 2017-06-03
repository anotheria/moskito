package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats object for Garbage Collector related values.
 * @author esmakula
 *
 */
public class GCStats extends AbstractStats {
	/**
	 * Current collection count amount.
	 */
	private StatValue currentCollectionCount;
	/**
	 * Total collection count amount.
	 */
	private StatValue totalCollectionCount;
	/**
	 * Current collection time.
	 */
	private StatValue currentCollectionTime;
	/**
	 * Total collection time.
	 */
	private StatValue totalCollectionTime;

	public GCStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	}

	public GCStats(String aName, Interval[] selectedIntervals){
		super(aName);
		currentCollectionCount = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "currentCollectionCount", selectedIntervals);;
		totalCollectionCount = StatValueFactory.createStatValue(0, "totalCollectionCount", selectedIntervals);
		currentCollectionTime = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "currentCollectionTime", selectedIntervals);;
		totalCollectionTime = StatValueFactory.createStatValue(0, "totalCollectionTime", selectedIntervals);

		addStatValues(currentCollectionCount, totalCollectionCount, currentCollectionTime, totalCollectionTime);
		
	}

	@Override
	public String toStatsString(String intervalName, TimeUnit unit) {
		StringBuilder ret = new StringBuilder();
		
		ret.append(getName()).append(' ');
		ret.append(" currentCollectionCount: ").append(currentCollectionCount.getValueAsLong(intervalName));
		ret.append(" totalCollectionCount: ").append(totalCollectionCount.getValueAsLong(intervalName));
		ret.append(" currentCollectionTime: ").append(currentCollectionTime.getValueAsLong(intervalName));
		ret.append(" totalCollectionTime: ").append(totalCollectionTime.getValueAsLong(intervalName));

		return ret.toString();
	}
	
	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
		
		if (valueName==null)
			throw new AssertionError("Value name can't be null");
		valueName = valueName.toLowerCase();
		
		if (valueName.equals("currentcollectioncount") || valueName.equals("current collection count"))
			return String.valueOf(getCurrentCollectionCount(intervalName));
		if (valueName.equals("totalcollectioncount") || valueName.equals("total collection count"))
			return String.valueOf(getTotalCollectionCount(intervalName));
		if (valueName.equals("currentcollectiontime") || valueName.equals("current collection time"))
			return String.valueOf(getCurrentCollectionTime(intervalName));
		if (valueName.equals("totalcollectiontime") || valueName.equals("total collection time"))
			return String.valueOf(getTotalCollectionTime(intervalName));


		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}

	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"CurrentCollectionCount",
			"TotalCollectionCount",
			"CurrentCollectionTime",
			"TotalCollectionTime"
	));

	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}



	public void update(long aCollectionCount, long aCollectionTime){
		currentCollectionCount.setValueAsLong(aCollectionCount);
		totalCollectionCount.setValueAsLong(aCollectionCount);
		currentCollectionTime.setValueAsLong(aCollectionTime);
		totalCollectionTime.setValueAsLong(aCollectionTime);
	}

	public long getCurrentCollectionCount(String intervalName){
		return currentCollectionCount.getValueAsLong(intervalName);
	}

	public long getTotalCollectionCount(String intervalName){
		return totalCollectionCount.getValueAsLong(intervalName);
	}

	public long getCurrentCollectionTime(String intervalName){
		return currentCollectionTime.getValueAsLong(intervalName);
	}

	public long getTotalCollectionTime(String intervalName){
		return totalCollectionTime.getValueAsLong(intervalName);
	}


}
