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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.producers.IStatsProducer;

import org.apache.log4j.Logger;

/**
 * The ProducerRegistryImplementation is a singleton object, but for webapp-reloading issues it doesn't contain a 
 * getInstance() method. Instead please use the ProducerRegistryFactory for instantiation.
 * @author lrosenberg
 */
public class ProducerRegistryImpl implements IProducerRegistry{
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(ProducerRegistryImpl.class);
	
	/**
	 * The listeners list.
	 */
	private List<IProducerRegistryListener> listeners = new CopyOnWriteArrayList<IProducerRegistryListener>();
	/**
	 * The map in which the producers are stored.
	 */
	private Map<String,ProducerReference> registry = new ConcurrentHashMap<String, ProducerReference>();
	
	/**
	 * Creates the ProducerRegistryImpl singleton instance.
	 */
	ProducerRegistryImpl(){
		reset();
	}

	@Override public void addListener(IProducerRegistryListener listener) {
		listeners.add(listener);
	}

	@Override public void removeListener(IProducerRegistryListener listener) {
		listeners.remove(listener);
	}

	@Override public Collection<IStatsProducer> getProducers() {
		ArrayList<IStatsProducer> ret = new ArrayList<IStatsProducer>();
		for (ProducerReference r : getProducerReferences()){
			if (r.get()!=null)
				ret.add(r.get());
		}
		return ret;
	}
	
	@Override public Collection<ProducerReference> getProducerReferences() {
		return registry.values();
	}

	@Override public IStatsProducer getProducer(String producerId){
		ProducerReference ref = registry.get(producerId); 
		return ref == null ? null : ref.get();
	}

	@Override public void registerProducer(IStatsProducer producer) {
		//null pointer exceptions in the toString method of the producer shouldn't crash here.
		String producerToString = null;
		try{
			producerToString = producer.toString();
		}catch(Exception e){
			producerToString = "Illegal to string method: "+e.getMessage()+", "+e.getClass();
		}
		log.info("Registry register producer: "+producer.getProducerId()+" / "+producerToString);
		ProducerReference previousRef = registry.put(producer.getProducerId(), new ProducerReference(producer));
		IStatsProducer previous = previousRef == null ? null : previousRef.get();
		if (previous!=null)
			log.info("Under this name a producer was already registered: "+previous);
		
		//now create a mx bean.
		
		for(IProducerRegistryListener listener : listeners){
			if (previous!=null)
				listener.notifyProducerUnregistered(previous);
			listener.notifyProducerRegistered(producer);
		}
	}


	@Override public void unregisterProducer(IStatsProducer producer) {
		registry.remove(producer.getProducerId());
		for(IProducerRegistryListener listener : listeners){
			listener.notifyProducerUnregistered(producer);
		}
	}

	/**
	 * Resets the impl for unittests.
	 */
	void reset() {
		cleanup(); // cleaning up all context before reseting producer registry instance

		listeners.clear();
		registry.clear();

		String junittest = System.getProperty("JUNITTEST");
		if (junittest != null && (junittest.equalsIgnoreCase("true"))) // preventing listiner's initialization for JUnit run's
			return;

		addListener(new JMXBridgeListener());
	}

	public void cleanup() {
		ArrayList<ProducerReference> producerReferences = new ArrayList<ProducerReference>();
		producerReferences.addAll(registry.values());
		for (ProducerReference p : producerReferences) {
			try {
				if (p.get()!=null)
					unregisterProducer(p.get());
			} catch (Exception e) {
				log.warn("can't unregister producer " + p, e);
			}
		}
	}
}
