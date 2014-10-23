/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.anotheria.moskito.extensions.producers;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.EvictionListener;
import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.core.inspection.Inspectable;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This producer is used as a an alternative to OnDemandStatsProducer with a max limit count based on LRU strategy.
 * It uses com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap internally.
 * @author lrosenberg, ubeinges
 */
public class RollingOnDemandStatsProducer<S extends IStats> implements IStatsProducer<S>, Inspectable {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(RollingOnDemandStatsProducer.class);

	/**
	 * The factory for stat creation.
	 */
	private IOnDemandStatsFactory<S> factory;

	/**
	 * A cached stat list for faster access.
	 */
	private List <S> _cachedStatsList;
	/**
	 * A map where all stat and their ids (strings) are being stored.
	 */
	private final ConcurrentLinkedHashMap<String, S> stats;

	/**
	 * A fast access variable for default (cumulated) stats.
	 */
	private S linkToDefaultStats;

	/**
	 * The variable where the category of this producer, given as parameter upon creation, is stored.
	 */
	private String category;
	/**
	 * The variable where the producerId of this producer, given as parameter upon creation, is stored.
	 */
	private String producerId;
	/**
	 * The variable where the subsystem of this producer, given as parameter upon creation, is stored.
	 */
	private String subsystem;

	/**
	 * CreationInfo object initialized on startup.
	 */
	private CreationInfo creationInfo;

	/**
	 * Creates a new OnDemandStatsProducer instance.
	 * @param aProducerId a producer id
	 * @param aCategory a category
	 * @param aSubsystem a subsystem
	 * @param aStatsFactory a factory for IStat object creation.
	 * @param maxEntries max amount of allowed entries.
	 */
	public RollingOnDemandStatsProducer(String aProducerId, String aCategory, String aSubsystem, IOnDemandStatsFactory<S> aStatsFactory, int maxEntries) {
		this(aProducerId, aCategory, aSubsystem, aStatsFactory, maxEntries, maxEntries, 32);
	}

	/**
	 * Creates a new OnDemandStatsProducer instance.
	 * @param aProducerId a producer id
	 * @param aCategory a category
	 * @param aSubsystem a subsystem
	 * @param aStatsFactory a factory for IStat object creation.
	 * @param minEntries starting amount of allowed entries.
	 * @param maxEntries max amount of allowed entries.
	 * @param concurrencyLevel concurrency level of the underlying ConcurrentLinkedHashMap.
	 */
	public RollingOnDemandStatsProducer(String aProducerId, String aCategory, String aSubsystem, IOnDemandStatsFactory<S> aStatsFactory, int minEntries, int maxEntries, int concurrencyLevel){
		category = aCategory;
		producerId = aProducerId;
		subsystem = aSubsystem;
		factory = aStatsFactory;

		if (factory==null)
			throw new IllegalArgumentException("Null factory is not allowed.");

		stats = new ConcurrentLinkedHashMap.Builder<String, S>()
				.initialCapacity(minEntries)
				.maximumWeightedCapacity(maxEntries)
				.concurrencyLevel(concurrencyLevel)
				.listener(new EvictionListener<String, S>() {
					@Override
					public void onEviction(String statName, S statObject) {
						_cachedStatsList.remove(statObject);
						statObject.destroy();
					}
				})
				.build();

		_cachedStatsList = new CopyOnWriteArrayList<S>();
		
		linkToDefaultStats = factory.createStatsObject(OnDemandStatsProducer.CUMULATED_STATS_NAME);
		_cachedStatsList.add(linkToDefaultStats);

		Exception e = new Exception();
		e.fillInStackTrace();
		creationInfo = new CreationInfo(e.getStackTrace()); 
	}
	
	public S getStats(String name){
		S stat = stats.get(name);
		if (stat == null){
			stat = factory.createStatsObject(name);
			S old = stats.putIfAbsent(name, stat);
			if (old==null){
				_cachedStatsList.add(stat);
			}else{
				stat = old;
			}
		}
		return stat;

	}

	@Override public String getCategory() {
		return category == null ? "default" : category;
	}

	@Override public String getProducerId() {
		return producerId;
	}

	@Override public List<S> getStats() {
		return _cachedStatsList;
	}

	@Override public String getSubsystem() {
		return subsystem == null ? "default" : subsystem;
	}
	
	public S getDefaultStats(){
		return linkToDefaultStats;
	}
	
	@Override public String toString(){
		return "RollingOnDemandStatsProducer: "+getProducerId()+":"+getSubsystem()+":"+getCategory();
	}
	
	protected List<S> getCachedStatsList(){
		return _cachedStatsList;
	}

	/**
	 * Returns the creation info about a producer.
	 * @return
	 */
	public CreationInfo getCreationInfo(){
		return creationInfo;
	}
	
	
}
