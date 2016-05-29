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
package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.filters.CategoryFilter;
import net.anotheria.moskito.core.registry.filters.SubsystemFilter;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.IntervalRegistryListener;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the public api for the producer registry.
 * @author lrosenberg
 *
 */
public class ProducerRegistryAPIImpl implements IProducerRegistryAPI, IProducerRegistryListener, IntervalRegistryListener, IIntervalListener {

	//TODO currently we are implementing IntervalRegistryListener but we are not using it!

	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(ProducerRegistryAPIImpl.class);
	
	/**
	 * Cached producer list - used internally to reduce overhead.
	 */
	private List<ProducerReference> _cachedProducerList;
	/**
	 * Cached producer map - used internally to reduce overhead.
	 */
	private Map<String,ProducerReference> _cachedProducerMap;
	/**
	 * Cached interval infos - used internally to reduce overhead.
	 */
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
			final int approxSize = (int)(producers.size()*1.5);

            _cachedProducerList = new ArrayList<ProducerReference>(approxSize);
            _cachedProducerMap = new HashMap<String,ProducerReference>(approxSize);

            for (IStatsProducer sp : producers){
				final ProducerReference reference = new ProducerReference(sp);

                _cachedProducerList.add(reference);
                _cachedProducerMap.put(sp.getProducerId(), reference);
            }
		}

		if (log.isDebugEnabled()){
			log.debug("Cachedproducer list contains "+_cachedProducerList.size()+" producers: ");
			log.debug(String.valueOf(_cachedProducerList));
			log.debug("Cached producer map contains: "+_cachedProducerMap.size()+" producers");
			log.debug(String.valueOf(_cachedProducerMap));
		}
	}

	//we should look into synchronizing access to this method, since the listener can possible remove a 
	//producer during processing.
	@Override public List<IStatsProducer> getAllProducers() {
		if (_cachedProducerList==null)
			buildProducerCacheFromScratch();
		ArrayList<IStatsProducer> ret = new ArrayList<IStatsProducer>();
		for (ProducerReference pr : _cachedProducerList){
			if (pr.get()!=null)
				ret.add(pr.get());
		}
		return ret;
		
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
			ProducerReference ref = _cachedProducerMap.get(producerId);
			if (ref==null)
				throw new NoSuchProducerException(producerId);
			return ref.get();
		}
	}

	@Override public List<IStatsProducer> getProducers(IProducerFilter... filters) {
		if (_cachedProducerList==null)
			buildProducerCacheFromScratch();
		List <IStatsProducer> ret = new ArrayList<IStatsProducer>();
		@SuppressWarnings("unchecked")
		List<ProducerReference> workCopy = (List<ProducerReference>)((ArrayList<ProducerReference>)_cachedProducerList).clone();
		for (ProducerReference p  : workCopy){
			IStatsProducer pp = p.get();
			boolean fit = true;
			if (pp==null)
				continue;
			for(IProducerFilter filter: filters)
				if (!filter.doesFit(pp)){
					fit = false;
					break;
				}
			if(fit)
				ret.add(pp);
		}
		return ret;
	}

	@Override public void notifyProducerRegistered(IStatsProducer producer) {
		log.info("Producer registered: "+producer.getProducerId()+" / "+producer);
		if (_cachedProducerList==null)
			return;
		synchronized(cacheLock){
			ProducerReference pr = new ProducerReference(producer);
			_cachedProducerList.add(pr);
			_cachedProducerMap.put(producer.getProducerId(), pr);
		}
	}

	@Override public void notifyProducerUnregistered(IStatsProducer producer) {
		log.info("Producer unregistered: "+producer.getProducerId()+" / "+producer);
		if (_cachedProducerList==null)
			return;
		synchronized(cacheLock){
			ProducerReference ref = _cachedProducerMap.remove(producer.getProducerId());
			if (ref!=null)
				_cachedProducerList.remove(ref);

		}
	}

	@Override public List<String> getCategories() {
		List<String> ret = new ArrayList<String>();
		List<IStatsProducer> producers = getAllProducers();
		for (IStatsProducer p : producers){
			if (! (ret.contains(p.getCategory())))
				ret.add(p.getCategory());
		}
       Collections.sort(ret);
       return ret;
	}

	@Override public List<String> getSubsystems() {
		List<String> ret = new ArrayList<String>();
		List<IStatsProducer> producers = getAllProducers();
		for (IStatsProducer p : producers){
			if (! (ret.contains(p.getSubsystem())))
				ret.add(p.getSubsystem());
		}
        Collections.sort(ret);
		return ret;
	}
}
