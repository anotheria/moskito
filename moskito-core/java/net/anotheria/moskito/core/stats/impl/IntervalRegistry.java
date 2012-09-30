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
package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.producers.SnapshotCreator;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.IntervalRegistryListener;
import net.anotheria.moskito.core.stats.UnknownIntervalException;
import net.anotheria.moskito.core.timing.IUpdateTriggerService;
import net.anotheria.moskito.core.timing.UpdateTriggerServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class implements a registry singleton to hold and create Interval instances.
 * 
 * @author lrosenberg
 */
public final class IntervalRegistry {

	/**
	 * This is the instance of the singleton.
	 */
	private static final IntervalRegistry instance = new IntervalRegistry();

	/**
	 * This Map holds all Intervals by their ids.
	 */
	private Map<Integer, Interval> intervalsById = new ConcurrentHashMap<Integer, Interval>();

	/**
	 * This Map holds all Intervals by their names.
	 */
	private Map<String, Interval> intervalsByName = new ConcurrentHashMap<String, Interval>();
	
	/**
	 * This map stores last update timestamps for registered intervals by name.
	 */
	private Map<String, Long> intervalUpdateTimestamp = new ConcurrentHashMap<String, Long>();

	/**
	 * This is a Thread-safe counter to generate VM-wide uniqe ids for new Interval instances.
	 */
	private AtomicInteger nextId = new AtomicInteger(1);

	/**
	 * This is a time control serive for periodic call backs.
	 */
	private IUpdateTriggerService updateTriggerService;

	/**
	 * This List holds all registered IntervalRegistryListeners.
	 * 
	 * @see IntervalRegistryListener
	 */
	private List<IntervalRegistryListener> registryListeners = new CopyOnWriteArrayList<IntervalRegistryListener>();

	/**
	 * The contructor.
	 */
	private IntervalRegistry() {
		updateTriggerService = UpdateTriggerServiceFactory.getUpdateTriggerService();

        registryListeners.add(new IntervalRegistryListener() {
            public void intervalCreated(Interval aInterval) {
                aInterval.addSecondaryIntervalListener(new IIntervalListener() {
                    public void intervalUpdated(Interval aCaller) {
                        SnapshotCreator.INSTANCE.createSnapshot(aCaller);
                    }
                });
            }
        });
    }

	/**
	 * This is the Singleton instance accessor method.
	 * 
	 * @return the IntervalRegistry instance.
	 */
	public static IntervalRegistry getInstance() {
		return instance;
	}

	/**
	 * This method retrieves an Interval with the given id.
	 * 
	 * @param aId the Interval id
	 * @return the found Interval
	 * @throws UnknownIntervalException if no Interval with the given id could be found
	 */
	Interval getInterval(int aId) {
		Interval interval = intervalsById.get(aId);
		if (interval == null) {
			throw new UnknownIntervalException(aId);
		}
		return interval;
	}

	/**
	 * This method retrieves an Interval with the given name. If no one could be found it will
	 * create a new Interval with given name and length.
	 * TODO: This method should be renamed to "getOrCreateInterval" or something like that 
	 * TODO: This method MUST be synchonrized to avoid doublicate Interval instances....
	 *  
	 * @param aName the Interval name
	 * @param aLength the length of the new Interval in seconds.
	 * @return the existing Interval or a new Interval with given name and length
	 */
	public Interval getInterval(String aName, int aLength) {
		Interval interval = intervalsByName.get(aName);
		if (interval == null)
			interval = createInterval(aName, aLength);
		return interval;
	}

	/**
	 * This method retrieves an Interval with the given name. If no one could be found it will
	 * create a new Interval with given name - the length will be a guess.
	 * TODO: This method should be renamed to "getOrCreateInterval" or something like that 
	 * TODO: This method MUST be synchonrized to avoid double Interval instances....
	 *  
	 * @param aName the Interval name
	 * @return the existing Interval or a new Interval with given name and guessed length
	 */
	public Interval getInterval(String aName) {
		Interval interval = intervalsByName.get(aName);
		if (interval == null) {
			interval = createInterval(aName, IntervalNameParser.guessLengthFromName(aName));
		}
		return interval;
	}

	/**
	 * This method creates a new Interval with the given name and length.
	 * 
	 * @param aName the name of the new Interval
	 * @param aLength the length of the Interval in seconds
	 * @return the new Interval
	 */
	private Interval createInterval(String aName, int aLength) {
		IntervalImpl interval = new IntervalImpl(obtainNextUniqueId(), aName, aLength);
		
		if (aLength!=-1)
			updateTriggerService.addUpdateable(interval, aLength);
		
		intervalsById.put(interval.getId(), interval);
		intervalsByName.put(interval.getName(), interval);
		for (IntervalRegistryListener listener : registryListeners) {
			listener.intervalCreated(interval);
		}
		
		//add new listener to each interval to store update timestamps
		interval.addSecondaryIntervalListener(new IIntervalListener() {
			@Override
			public void intervalUpdated(Interval aCaller) {
				intervalUpdateTimestamp.put(aCaller.getName(), System.currentTimeMillis());
			}
		});
		return interval;
	}

	/**
	 * This method returns the next free id.
	 * 
	 * @return the next id
	 */
	private int obtainNextUniqueId() {
		return nextId.getAndIncrement();
	}

	/**
	 * This method returns a list containing all known Intervals.
	 *  
	 * @return the Interval list
	 */
	public List<Interval> getIntervals() {
		return new ArrayList<Interval>(intervalsByName.values());
	}
	
	/**
	 * Returns last update timestamp of an interval by name.
	 * @param intervalName name of the interval.
	 * @return
	 */
	public Long getUpdateTimestamp(String intervalName){
		return intervalName == null ? Long.valueOf(0) : intervalUpdateTimestamp.get(intervalName);
	}
	
	//this method is used by unit testing and force an interval to be updated
	public void forceUpdateIntervalForTestingPurposes(String intervalName){
		((IntervalImpl)getInterval(intervalName)).update();
	}
}
