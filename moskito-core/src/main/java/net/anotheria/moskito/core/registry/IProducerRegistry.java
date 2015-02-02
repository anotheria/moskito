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

import java.util.Collection;

/**
 * Producer registry is used internally to register/unregister producers.
 * @author lrosenberg
 *
 */
public interface IProducerRegistry {
	/**
	 * Registers a stats producer.
	 * @param producer
	 */
	void registerProducer(IStatsProducer producer);
	/**
	 * Unregisters a stat producer.
	 * @param producer
	 */
	void unregisterProducer(IStatsProducer producer);
	/**
	 * Returns registered producers.
	 * @return
	 */
	Collection<IStatsProducer> getProducers();
	/**
	 * Returns registered producers.
	 * @return
	 */
	Collection<ProducerReference> getProducerReferences();
	/**
	 * Returns a producer with given id.
	 * @param producerId
	 * @return
	 */
	IStatsProducer getProducer(String producerId);
	/**
	 * Adds a producer registry listener.
	 * @param listener
	 */
	void addListener(IProducerRegistryListener listener);
	
	/**
	 * Removes a listener.
	 * @param listener
	 */
	void removeListener(IProducerRegistryListener listener);
	/**
	 * Clean ups the state. This is useful for webapp shutdown or unit tests.
	 */
	void cleanup();
}

