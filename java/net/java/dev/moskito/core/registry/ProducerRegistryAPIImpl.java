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
package net.java.dev.moskito.core.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.filters.CategoryFilter;
import net.java.dev.moskito.core.registry.filters.SubsystemFilter;
import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.IntervalRegistryListener;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

import org.apache.log4j.Logger;

/**
 * Implementation of the public api for the producer registry.
 * @author another
 *
 */
public class ProducerRegistryAPIImpl implements IProducerRegistryAPI, IProducerRegistryListener, IntervalRegistryListener, IIntervalListener{
	
	/**
	 * Logger.
	 */
	private static final Logger log = Logger.getLogger(ProducerRegistryAPIImpl.class);
	
	private List<IStatsProducer> _cachedProducerList;
	private Map<String,IStatsProducer> _cachedProducerMap;
	private List<IntervalInfo> _cachedIntervalInfos;

	/**
	 * The producer registry.
	 */
	private IProducerRegistry registry;
	/**
	 * The interval registry.
	 */
	private IntervalRegistry intervalRegistry;
	
	/**
	 * Lock object for cache modifications.
	 */
	private static final Object cacheLock = new Object();
	/**
	 * Lock object for interval modifications.
	 */
	private static final Object intervalLock = new Object();
	
	/**
	 * Creates and sets up the ProducerRegistryAPIImpl. ProducerRegistryAPIImpl is meant to be a singleton.
	 */
	ProducerRegistryAPIImpl(){
		_cachedProducerList = null;
		_cachedProducerMap = null;
		_cachedIntervalInfos = null;
		
		registry = ProducerRegistryFactory.getProducerRegistryInstance();
		registry.addListener(this);
		
		intervalRegistry = IntervalRegistry.getInstance();
		
	}
	
	/**
	 * Rebuilds the caches upon change.
	 */
	private void buildProducerCacheFromScratch(){
		log.debug("rebuilding producer cache");
		synchronized(cacheLock){
			if (_cachedProducerList!=null)
				return;
			rebuildProducerCache(registry.getProducers());
		}
	}
	
	/**
	 * Rebuilds the caches with given producers..
	 */
	private void rebuildProducerCache(Collection<IStatsProducer> producers){
		log.debug("Rebuilding producer cache with "+producers.size()+" producers.");
		log.debug("Following producers known: "+producers);
		synchronized(cacheLock){
			//lets create lists with more place to store as actually need, we will probably need it.
			int approxSize = (int)(producers.size()*1.5);
			_cachedProducerList = new ArrayList<IStatsProducer>(approxSize); 
			_cachedProducerList.addAll(producers);
			
			_cachedProducerMap = new HashMap<String,IStatsProducer>(approxSize);
			for (IStatsProducer p : producers)
				_cachedProducerMap.put(p.getProducerId(), p);
		}
		if (log.isDebugEnabled()){
			log.debug("Cachedproducer list contains "+_cachedProducerList.size()+" producers: ");
			log.debug(_cachedProducerList);
			log.debug("Cached producer map contains: "+_cachedProducerMap.size()+" producers");
			log.debug(_cachedProducerMap);
		}
	}

	//we should look into synchronizing access to this method, since the listener can possible remove a 
	//producer during processing.
	@Override public List<IStatsProducer> getAllProducers() {
		if (_cachedProducerList==null)
			buildProducerCacheFromScratch();
		return _cachedProducerList;
	}

	@Override public List<IStatsProducer> getAllProducersByCategory(String category) {
		return getProducers(new CategoryFilter(category));
	}

	@Override public List<IStatsProducer> getAllProducersBySubsystem(String subsystem) {
		return getProducers(new SubsystemFilter(subsystem));
	}

	@Override public List<IntervalInfo> getPresentIntervals() {
		if (_cachedIntervalInfos==null)
			createIntervalList();
		return _cachedIntervalInfos;
	}
	
	/**
	 * Creates the list of existing intervals.
	 */
	private void createIntervalList(){
		synchronized(intervalLock){
			if (_cachedIntervalInfos!=null)
				return;
			List<Interval> intervals = intervalRegistry.getIntervals();
			_cachedIntervalInfos = new ArrayList<IntervalInfo>(intervals.size());
			for (Interval interval : intervals) {
				_cachedIntervalInfos.add(new IntervalInfo(interval));
				interval.addSecondaryIntervalListener(this);
			}
		}
	}
	
	@Override public void intervalUpdated(Interval aCaller) {
		IntervalInfo dummy = new IntervalInfo(aCaller);
		synchronized(intervalLock){
			int index = _cachedIntervalInfos.indexOf(dummy);
			if (index==-1)
				return;
			_cachedIntervalInfos.get(index).update();
		}
	}

	@Override public void intervalCreated(Interval aInterval) {
		synchronized(intervalLock){
			_cachedIntervalInfos.add(new IntervalInfo(aInterval));
		}
	}

	@Override public IStatsProducer getProducer(String producerId) {
		if (_cachedProducerList==null)
			buildProducerCacheFromScratch();

		synchronized(cacheLock){
			return _cachedProducerMap.get(producerId);
		}
	}

	@Override public List<IStatsProducer> getProducers(IProducerFilter... filters) {
		if (_cachedProducerList==null)
			buildProducerCacheFromScratch();
		List <IStatsProducer> ret = new ArrayList<IStatsProducer>();
		@SuppressWarnings("unchecked")
		List<IStatsProducer> workCopy = (List<IStatsProducer>)((ArrayList)_cachedProducerList).clone();
		for (IStatsProducer p  : workCopy){
			boolean fit = true;
			for(IProducerFilter filter: filters)
				if (!filter.doesFit(p)){
					fit = false;
					break;
				}
			if(fit)
				ret.add(p);
		}
		return ret;
	}

	@Override public void notifyProducerRegistered(IStatsProducer producer) {
		log.info("Producer registered: "+producer.getProducerId()+" / "+producer);
		if (_cachedProducerList==null)
			return;
		synchronized(cacheLock){
			_cachedProducerList.add(producer);
			_cachedProducerMap.put(producer.getProducerId(), producer);
		}
	}

	@Override public void notifyProducerUnregistered(IStatsProducer producer) {
		log.info("Producer unregistered: "+producer.getProducerId()+" / "+producer);
		if (_cachedProducerList==null)
			return;
		synchronized(cacheLock){
			_cachedProducerList.remove(producer);
			_cachedProducerMap.remove(producer.getProducerId());
		}
	}

	@Override public List<String> getCategories() {
		List<String> ret = new ArrayList<String>();
		List<IStatsProducer> producers = getAllProducers();
		for (IStatsProducer p : producers){
			if (! (ret.contains(p.getCategory())))
				ret.add(p.getCategory());
		}
		return ret;
	}

	@Override public List<String> getSubsystems() {
		List<String> ret = new ArrayList<String>();
		List<IStatsProducer> producers = getAllProducers();
		for (IStatsProducer p : producers){
			if (! (ret.contains(p.getSubsystem())))
				ret.add(p.getSubsystem());
		}
		return ret;
	}
}
