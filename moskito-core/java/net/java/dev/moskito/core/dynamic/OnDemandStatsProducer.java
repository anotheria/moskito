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
package net.java.dev.moskito.core.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.inspection.Inspectable;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * This producer is used when the different method producing stats aren't known at compile time (or you don't want to use 
 * them). It is used by the InvocationProxy to add methods dynamically as they being called, but also by the filters, like
 * RequestURIFilter dynamically adding a stat for each new uri.  
 * @author dvayanu
 */
public class OnDemandStatsProducer implements IStatsProducer, Inspectable{
	
	private static Logger log;
	static{
		log = Logger.getLogger(OnDemandStatsProducer.class);
	}
	
	/**
	 * The factory for stat creation.
	 */
	private IOnDemandStatsFactory factory;
	
	/**
	 * A cached stat list for faster access.
	 */
	private List <IStats> _cachedStatsList;
	/**
	 * A map where all stat and their ids (strings) are being stored.
	 */
	private Map<String,IStats> stats;
	
	/**
	 * A fast access variable for default (cumulated) stats.
	 */
	private IStats linkToDefaultStats;
	
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
	 */
	public OnDemandStatsProducer(String aProducerId, String aCategory, String aSubsystem, IOnDemandStatsFactory aStatsFactory){
		category = aCategory;
		producerId = aProducerId;
		subsystem = aSubsystem;
		factory = aStatsFactory;
		
		stats = new HashMap<String,IStats>();
		_cachedStatsList = new ArrayList<IStats>();
		
		try{
			linkToDefaultStats = getStats("cumulated");
		}catch(OnDemandStatsProducerException ignored){
			log.error("couldn't get link to default stats, probably the limitation is far too low.");
		}
		Exception e = new Exception();
		e.fillInStackTrace();
		creationInfo = new CreationInfo(e.getStackTrace()); 
	}
	
	//note, please check whether this fun is affected by broken DLC problem. 
	public IStats getStats(String name) throws OnDemandStatsProducerException{
		IStats stat;
		synchronized (stats) {
			stat = stats.get(name);
		}
		if (stat == null){
			if (limitForNewEntriesReached())
				throw new OnDemandStatsProducerException("Limit reached");
			stat = factory.createStatsObject(name);
			synchronized(stats){
				//check whether another thread was faster
				if (stats.get(name)==null){
					stats.put(name, stat);
					_cachedStatsList.add(stat);
				}else{
					//ok, another thread was faster, we have to throw away our object.
					stat = stats.get(name);
				}
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

	@Override public List<IStats> getStats() {
		return _cachedStatsList;
	}

	@Override public String getSubsystem() {
		return subsystem == null ? "default" : subsystem;
	}
	
	public IStats getDefaultStats(){
		return linkToDefaultStats;
	}
	
	@Override public String toString(){
		return "OnDemandProducer ("+getProducerNameExtension()+"): "+getProducerId()+":"+getSubsystem()+":"+getCategory();
	}
	
	/**
	 * The getStats method checked whether the limit is reached before creating a new stat object. Overwrite this function and return true if you want to limit the number of possible stored stats. Not limiting the 
	 * stats can result in memory leak if someone can influence the stat names from the outside. Therefore MoskitoFilters are using 
	 * EntryCountLimitedOnDemandStatsProducer instead.
	 * @return false
	 */
	protected boolean limitForNewEntriesReached(){
		return false;
	}
	
	protected String getProducerNameExtension(){
		return "unlimited";
	}
	
	protected List<IStats> getCachedStatsList(){
		return _cachedStatsList;
	}
	
	public CreationInfo getCreationInfo(){
		return creationInfo;
	}
	
	
}
