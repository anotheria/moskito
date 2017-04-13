package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

/**
 * Statistics for a memory pool usage.
 * @author lrosenberg
 * see http://java.sun.com/j2se/1.5.0/docs/api/java/lang/management/MemoryUsage.html
 *
 */
public class MemoryPoolStats extends AbstractMemoryPoolStats implements IMemoryPoolStats{
	/**
	 * Initial size.
	 */
	private StatValue init;
	/**
	 * Used.
	 */
	private StatValue used;
	/**
	 * Min used.
	 */
	private StatValue minUsed;
	/**
	 * Max used.
	 */
	private StatValue maxUsed;
	/**
	 * Commited memory.
	 */
	private StatValue commited;
	/**
	 * Min commited.
	 */
	private StatValue minCommited;
	/**
	 * Max commited.
	 */
	private StatValue maxCommited;
	/**
	 * Max.
	 */
	private StatValue max;

	/**
	 * Creates a new unnamed memory pool stats object with default intervals.
	 */
	public MemoryPoolStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new unnamed memory pool stats object with default intervals.
 	 * @param aName name of the memory pool stats object.
	 */
	public MemoryPoolStats(String aName){
		this(aName, Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new unnamed memory pool stats object.
	 * @param aName name of the object.
	 * @param selectedIntervals supported intervals.
	 */
	public MemoryPoolStats(String aName,  Interval[] selectedIntervals){
		super(aName);
		init = StatValueFactory.createStatValue(0L, "init", selectedIntervals);
		used = StatValueFactory.createStatValue(0L, "used", selectedIntervals);
		minUsed = StatValueFactory.createStatValue(0L, "minUsed", selectedIntervals);
		minUsed.setDefaultValueAsLong(Integer.MAX_VALUE);
		minUsed.reset();

		maxUsed = StatValueFactory.createStatValue(0L, "maxUsed", selectedIntervals);
		
		commited = StatValueFactory.createStatValue(0L, "commited", selectedIntervals);
		minCommited =  StatValueFactory.createStatValue(0L, "minCommited", selectedIntervals);
		minCommited.setDefaultValueAsLong(Integer.MAX_VALUE);
		minCommited.reset();
		maxCommited =  StatValueFactory.createStatValue(0L, "maxCommited", selectedIntervals);
 
		max = StatValueFactory.createStatValue(0L, "max", selectedIntervals);

		addStatValues(init, used, minUsed, maxUsed, commited, minCommited, maxCommited, max);
		
	}
	
	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" INIT: ").append(init.getValueAsLong(intervalName));
		b.append(" MIN USED: ").append(minUsed.getValueAsLong(intervalName));
		b.append(" USED: ").append(used.getValueAsLong(intervalName));
		b.append(" MAX USED: ").append(maxUsed.getValueAsLong(intervalName));
		b.append(" MIN COMMITED: ").append(minCommited.getValueAsLong(intervalName));
		b.append(" COMMITED: ").append(commited.getValueAsLong(intervalName));
		b.append(" MAX COMMITED: ").append(maxCommited.getValueAsLong(intervalName));
		b.append(" MAX: ").append(max.getValueAsLong(intervalName));
		return b.toString();
	}

	/**
	 * Returns the amount of initial memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getInit(String intervalName){
		return init.getValueAsLong(intervalName);
	}

	/**
	 * Returns the amount of user memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getUsed(String intervalName){
		return used.getValueAsLong(intervalName);
	}
	/**
	 * Returns the amount of min used memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getMinUsed(String intervalName){
		return minUsed.getValueAsLong(intervalName);
	}
	/**
	 * Returns the amount of max used memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getMaxUsed(String intervalName){
		return maxUsed.getValueAsLong(intervalName);
	}


	/**
	 * Returns the amount of commited memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getCommited(String intervalName){
		return commited.getValueAsLong(intervalName);
	}

	/**
	 * Returns the amount of min commited memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getMinCommited(String intervalName){
		return minCommited.getValueAsLong(intervalName);
	}
	/**
	 * Returns the amount of max commited memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getMaxCommited(String intervalName){
		return maxCommited.getValueAsLong(intervalName);
	}


	/**
	 * Returns the amount of max available memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getMax(String intervalName){
		return max.getValueAsLong(intervalName);
	}

	/**
	 * Returns the amount of free memory for the selected interval.
	 * @param intervalName the target interval name.
	 * @return
	 */
	public long getFree(String intervalName){
		return getCommited(intervalName) - getUsed(intervalName);
	}

	/**
	 * Sets new initial memory amount.
	 * @param value the memory amount.
	 */
	public void setInit(long value){
		init.setValueAsLong(value);
	}
	/**
	 * Sets new max memory amount.
	 * @param value the memory amount.
	 */
	public void setMax(long value){
		max.setValueAsLong(value);
	}

	/**
	 * Sets new used memory amount.
	 * @param value the memory amount.
	 */
	public void setUsed(long value){
		used.setValueAsLong(value);
		minUsed.setValueIfLesserThanCurrentAsLong(value);
		maxUsed.setValueIfGreaterThanCurrentAsLong(value);
	}

	/**
	 * Sets new commited memory amount.
	 * @param value the memory amount.
	 */
	public void setCommited(long value){
		commited.setValueAsLong(value);
		minCommited.setValueIfLesserThanCurrentAsLong(value);
		maxCommited.setValueIfGreaterThanCurrentAsLong(value);
	}
}
