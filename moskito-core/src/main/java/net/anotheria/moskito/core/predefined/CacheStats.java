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
 * Stats for (read) caches.
 * @author lrosenberg
 */
public class CacheStats extends AbstractStats {
	/**
	 * Number of read requests.
	 */
	private StatValue requests;
	/**
	 * Number of hits.
	 */
	private StatValue hits;
	/**
	 * Number of writes into the cache.
	 */
	private StatValue writes;
	/**
	 * Number of garbage collected items if supported by this cache variant.
	 */
	private StatValue garbageCollected;
	/**
	 * Number of roll-overed items if supported by this cache implementation.
	 */
	private StatValue rolloverCount;
	
	//private StatValue fillRatio;
	/**
	 * Number of expired objects.
	 */
	private StatValue expiredCount;
	/**
	 * Number of filtered objects.
	 */
	private StatValue filteredCount;
	/**
	 * Number of refused objects, because cache was full.
	 */
	private StatValue cacheFullCount;
	
	/**
	 * Number of delete operations.
	 */
	private StatValue deletes;
	
	/**
	 * Name of the cache.
	 */
	private String name;

	/**
	 * Valuenames constant for getAvailableValueNames method.
	 */
	private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
			"REQ",
			"HIT",
			"HR",
			"WR",
			"GC",
			"RO",
			"FU",
			"EX",
			"DEL",
			"FI"));

	/**
	 * Creates a new 'unnamed' cache stats object with default intervals.
	 */
	public CacheStats(){
		this("unnamed", Constants.getDefaultIntervals());
	}

	/**
	 * Creates a new cachestats object.
	 * @param aName
	 * @param selectedIntervals
	 */
	public CacheStats(String aName,  Interval[] selectedIntervals){
		Long longPattern = Long.valueOf(0);
		name = aName;
		
		requests = StatValueFactory.createStatValue(longPattern, "requests", selectedIntervals);
		hits = StatValueFactory.createStatValue(longPattern, "hits", selectedIntervals);
		writes = StatValueFactory.createStatValue(longPattern, "writes", selectedIntervals);
		garbageCollected = StatValueFactory.createStatValue(longPattern, "gccollects", selectedIntervals);
		rolloverCount = StatValueFactory.createStatValue(longPattern, "rollovers", selectedIntervals);
		expiredCount = StatValueFactory.createStatValue(longPattern, "expirations", selectedIntervals);
		filteredCount = StatValueFactory.createStatValue(longPattern, "filter", selectedIntervals);
		cacheFullCount = StatValueFactory.createStatValue(longPattern, "cachefull", selectedIntervals);
		deletes = StatValueFactory.createStatValue(longPattern, "deletes", selectedIntervals);
		
		//fillRatio = StatValueFactory.createStatValue(new Double(0.0), "fillRatio" , selectedIntervals);
		addStatValues(requests, hits, writes, garbageCollected, rolloverCount, expiredCount, filteredCount, cacheFullCount, deletes);

	}
	
	public String getName(){
		return name;
	}

	/**
	 * Returns this caches hit ratio.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public double getHitRatio(String intervalName){
		return hits.getValueAsDouble(intervalName)/requests.getValueAsDouble(intervalName);
	}

	/**
	 * Adds a new cache request.
	 */
	public void addRequest(){
		requests.increase();
	}

	/**
	 * Returns the number of requests in a given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getRequests(String intervalName){
		return requests.getValueAsLong(intervalName);
	}

	/**
	 * Adds a new cache-hit.
	 */
	public void addHit(){
		hits.increase();
	}

	/**
	 * Returns the number of hits in a given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getHits(String intervalName){
		return hits.getValueAsLong(intervalName);
	}

	/**
	 * Adds a request that was a hit. Its the same as calling addRequest();addHit().
	 */
	public void addHitRequest(){
		requests.increase();
		hits.increase();
	}

	/**
	 * Adds a write (new object) in the cache.
	 */
	public void addWrite(){
		writes.increase();
	}

	/**
	 * Returns the number of writes (new objects) in the cache int the given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getWrites(String intervalName){
		return writes.getValueAsLong(intervalName);
	}

	/**
	 * Adds an object removal.
	 */
	public void addDelete(){
		deletes.increase();
	}

	/**
	 * Returns the number of object deletion in a given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getDeletes(String intervalName){
		return deletes.getValueAsLong(intervalName);
	}

	/**
	 * Adds a rollover. This is only useful for fixed size caches that support rollover.
 	 */
	public void addRollover(){
		rolloverCount.increase();
	}

	/**
	 * Adds a cache full condition. Only for caches with limited size and without rollover.
	 */
	public void addCacheFull(){
		cacheFullCount.increase();
	}

	/**
	 * Returns the number of cache fulls in the specified interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getCacheFullCount(String intervalName){
		return cacheFullCount.getValueAsLong(intervalName);
	}

	/**
	 * Returns the number of cache rollovers in a given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getRolloverCount(String intervalName){
		return rolloverCount.getValueAsLong(intervalName);
	}


	/**
	 * Adds a garbage collected object. This is only supported by SoftReference caches.
	 */
	public void addGarbageCollected(){
		garbageCollected.increase();
	}

	/**
	 * Returns number of garbage collected objects in the given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getGarbageCollected(String intervalName){
		return garbageCollected.getValueAsLong(intervalName);
	}

	/**
	 * Adds a filtered object. Only supported by caches that can filter objects.
	 */
	public void addFiltered(){
		filteredCount.increase();
	}

	/**
	 * Returns the number of filtered objects in a given interval.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getFiltered(String intervalName){
		return filteredCount.getValueAsLong(intervalName);
	}

	/**
	 * Adds an expiration event. Only supported by ExpiringCaches.
	 */
	public void addExpired(){
		expiredCount.increase();
	}

	/**
	 * Return number of expired objects.
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public long getExpired(String intervalName){
		return expiredCount.getValueAsLong(intervalName);
	}

	/**
	 * Returns the fill ration of the cache in a given interval. Warning: currently not supported by any cache!
	 * @param intervalName the name of the target interval.
	 * @return
	 */
	public double getFillRatio(String intervalName){
		return 0.0;
	}
	
	@Override
	public List<String> getAvailableValueNames() {
		return VALUE_NAMES;
	}

	@Override public String toStatsString(String intervalName, TimeUnit timeUnit) {
		StringBuilder b = new StringBuilder();
		b.append(getName()).append(' ');
		b.append(" REQ: ").append(requests.getValueAsLong(intervalName));
		b.append(" HIT: ").append(hits.getValueAsLong(intervalName));
		b.append(" HR: ").append(getHitRatio(intervalName));
		b.append(" WR: ").append(writes.getValueAsLong(intervalName));
		b.append(" GC: ").append(garbageCollected.getValueAsLong(intervalName));
		b.append(" RO: ").append(rolloverCount.getValueAsLong(intervalName));
		b.append(" FU: ").append(cacheFullCount.getValueAsLong(intervalName));
		b.append(" EX: ").append(expiredCount.getValueAsLong(intervalName));
		b.append(" DEL: ").append(deletes.getValueAsLong(intervalName));
		b.append(" FI: ").append(filteredCount.getValueAsLong(intervalName));
		return b.toString();
	}

	@Override public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		if (valueName==null || valueName.equals(""))
			throw new AssertionError("Value name can not be empty");
		valueName = valueName.toLowerCase();
		if (valueName.equals("req"))
			return ""+requests.getValueAsLong(intervalName);
		if (valueName.equals("hit"))
			return ""+hits.getValueAsLong(intervalName);
		if (valueName.equals("hr"))
			return ""+getHitRatio(intervalName);
		if (valueName.equals("wr"))
			return ""+writes.getValueAsLong(intervalName);
		if (valueName.equals("gc"))
			return ""+garbageCollected.getValueAsLong(intervalName);
		if (valueName.equals("ro"))
			return ""+rolloverCount.getValueAsLong(intervalName);
		if (valueName.equals("fu"))
			return ""+cacheFullCount.getValueAsLong(intervalName);
		if (valueName.equals("ex"))
			return ""+expiredCount.getValueAsLong(intervalName);
		if (valueName.equals("del"))
			return ""+deletes.getValueAsLong(intervalName);
		if (valueName.equals("fi"))
			return ""+filteredCount.getValueAsLong(intervalName);
		return super.getValueByNameAsString(valueName, intervalName, timeUnit);
	}
}
