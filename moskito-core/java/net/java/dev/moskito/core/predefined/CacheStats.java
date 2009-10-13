/* ------------------------------------------------------------------------- *
$Source$
$Author$
$Date$
$Revision$


Copyright 2004-2005 by FriendScout24 GmbH, Munich, Germany.
All rights reserved.

This software is the confidential and proprietary information
of FriendScout24 GmbH. ("Confidential Information").  You
shall not disclose such Confidential Information and shall use
it only in accordance with the terms of the license agreement
you entered into with FriendScout24 GmbH.
See www.friendscout24.de for details.
** ------------------------------------------------------------------------- */
package net.java.dev.moskito.core.predefined;

import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.stats.impl.StatValueFactory;

/**
 * Stats for caches.
 * @author lrosenberg
 *
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
	 * Name of the cache.
	 */
	private String name;
	
	public CacheStats(){
		this("unnamed", Constants.DEFAULT_INTERVALS);
	} 
	
	public CacheStats(String aName,  Interval[] selectedIntervals){
		Long longPattern = new Long(0);
		name = aName;
		
		requests = StatValueFactory.createStatValue(longPattern, "requests", selectedIntervals);
		hits = StatValueFactory.createStatValue(longPattern, "hits", selectedIntervals);
		writes = StatValueFactory.createStatValue(longPattern, "writes", selectedIntervals);
		garbageCollected = StatValueFactory.createStatValue(longPattern, "gccollects", selectedIntervals);
		rolloverCount = StatValueFactory.createStatValue(longPattern, "rollovers", selectedIntervals);
		expiredCount = StatValueFactory.createStatValue(longPattern, "expirations", selectedIntervals);
		filteredCount = StatValueFactory.createStatValue(longPattern, "filter", selectedIntervals);
		
		//fillRatio = StatValueFactory.createStatValue(new Double(0.0), "fillRatio" , selectedIntervals);

	}
	
	public String getName(){
		return name;
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
		b.append(" EX: ").append(expiredCount.getValueAsLong(intervalName));
		b.append(" FI: ").append(filteredCount.getValueAsLong(intervalName));
		return b.toString();
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
	
	
	public void addRollover(){
		rolloverCount.increase();
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
	


	
}
