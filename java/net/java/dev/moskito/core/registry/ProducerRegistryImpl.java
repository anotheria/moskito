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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.util.StartBuiltInProducers;

import org.apache.log4j.Logger;

/**
 * The ProducerRegistryImplementation is a singleton object, but for webapp-reloading issues it doesn't contain a 
 * getInstance() method. Instead please use the ProducerRegistryFactory for instantiation.
 * @author lrosenberg
 */
public enum ProducerRegistryImpl implements IProducerRegistry{
	
	INSTANCE;
	
	private static Logger log;
	static {
		log = Logger.getLogger(ProducerRegistryImpl.class);
	}

	/**
	 * The listeners list.
	 */
	private List<IProducerRegistryListener> listeners;
	/**
	 * The map in which the producers are stored.
	 */
	private Map<String,IStatsProducer> registry;
	
	ProducerRegistryImpl(){
		listeners = new CopyOnWriteArrayList<IProducerRegistryListener>();
		registry = new ConcurrentHashMap<String,IStatsProducer>();
		StartBuiltInProducers.startbuiltin();
	}

	public void addListener(IProducerRegistryListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IProducerRegistryListener listener) {
		listeners.remove(listener);
	}

	public Collection<IStatsProducer> getProducers() {
		return registry.values();
	}
	
	public IStatsProducer getProducer(String producerId){
		return registry.get(producerId);
	}

	public void registerProducer(IStatsProducer producer) {
		log.info("Registry register producer: "+producer.getProducerId()+" / "+producer);
		IStatsProducer previous = registry.put(producer.getProducerId(), producer);
		if (previous!=null)
			log.info("Under this name a producer was already registered: "+previous);
		
		for(IProducerRegistryListener listener : listeners){
			if (previous!=null)
				listener.notifyProducerUnregistered(previous);
			listener.notifyProducerRegistered(producer);
		}
	}


	public void unregisterProducer(IStatsProducer producer) {
		registry.remove(producer.getProducerId());
		for(IProducerRegistryListener listener : listeners){
			listener.notifyProducerUnregistered(producer);
		}
	}
}
