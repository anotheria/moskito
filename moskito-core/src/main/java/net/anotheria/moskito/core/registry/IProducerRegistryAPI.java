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

import java.util.List;

/**
 * This interface provides the API which should be used by tools for producer monitoring instead of the registry itself.
 * It provides an additional abstraction layer. Depending on the implementation some caching could also be provided,
 * to reduce overhead. 
 * @author lrosenberg
 *
 */
public interface IProducerRegistryAPI {
	/**
	 * Returns all registered producers.
	 */
	List<IStatsProducer> getAllProducers();

	/**
	 * Returns all registered producers for the given category.
	 * @param category the category to be selected.
	 * @return list of all producers with category equal to submitted category.
	 */
	List<IStatsProducer> getAllProducersByCategory(String category);
	
	/**
	 * Returns all registered producers for the given subsystem.
	 * @param subsystem the subsystem to be selected.
	 * @return list of all producers with subsystem equal to submitted subsystem.
	 */
	List<IStatsProducer> getAllProducersBySubsystem(String subsystem);
	/**
	 * Returns the producer with the given producerId.
	 * @param producerId
	 * @return
	 */
	IStatsProducer getProducer(String producerId);
	/**
	 * Returns a filtered list of producers.
	 * @param filters
	 * @return
	 */
	List<IStatsProducer> getProducers(IProducerFilter... filters);
	/**
	 * Returns the list of available categories.
	 * @return
	 */
	List<String> getCategories();
	/**
	 * Returns the list of available subsystems.
	 * @return
	 */
	List<String> getSubsystems();

	/**
	 * Return known configured intervals.
	 * @return list with interval info objects for all configured and supported intervals.
	 */
	List<IntervalInfo> getPresentIntervals();
}
