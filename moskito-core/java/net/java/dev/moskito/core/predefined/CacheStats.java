package net.java.dev.moskito.core.predefined;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

/**
 * Stats for (read) caches.
 * @author lrosenberg
 */
public class CacheStats extends AbstractStats{
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
	 * Number of rollovered items if supported by this cache implementation.
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

	public CacheStats(){
		this("unnamed", Constants.getDefaultIntervals());
	} 
	
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

	}
	
	public String getName(){
		return name;
	}
	
	public double getHitRatio(String intervalName){
		return hits.getValueAsDouble(intervalName)/requests.getValueAsDouble(intervalName);
	}
	
	public void addRequest(){
		requests.increase();
	}
	
	public long getRequests(String intervalName){
		return requests.getValueAsLong(intervalName);
	}
	
	public void addHit(){
		hits.increase();
	}

	public long getHits(String intervalName){
		return hits.getValueAsLong(intervalName);
	}
	
	public void addHitRequest(){
		requests.increase();
		hits.increase();
	}
	
	public void addWrite(){
		writes.increase();
	}
	
	
	public long getWrites(String intervalName){
		return writes.getValueAsLong(intervalName);
	}

	public void addDelete(){
		deletes.increase();
	}
	
	public long getDeletes(String intervalName){
		return deletes.getValueAsLong(intervalName);
	}
	
	
	public void addRollover(){
		rolloverCount.increase();
	}
	
	public void addCacheFull(){
		cacheFullCount.increase();
	}
	
	public long getCacheFullCount(String intervalName){
		return cacheFullCount.getValueAsLong(intervalName);
	}

	public long getRolloverCount(String intervalName){
		return rolloverCount.getValueAsLong(intervalName);
	}

	
	public void addGarbageCollected(){
		garbageCollected.increase();
	}

	public long getGarbageCollected(String intervalName){
		return garbageCollected.getValueAsLong(intervalName);
	}

	public void addFiltered(){
		filteredCount.increase();
	}
	
	public long getFiltered(String intervalName){
		return filteredCount.getValueAsLong(intervalName);
	}

	public void addExpired(){
		expiredCount.increase();
	}

	public long getExpired(String intervalName){
		return expiredCount.getValueAsLong(intervalName);
	}
	
	//TODO dummy
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
