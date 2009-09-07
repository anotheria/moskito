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

import java.util.List;

import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * This interface provides the API which should be used by tools for producer monitoring instead of the registry itself.
 * It provides an additional abstraction layer. Depending on the implementation some caching could also be provided,
 * to reduce overhead. 
 * @author another
 *
 */
public interface IProducerRegistryAPI {
	public List<IStatsProducer> getAllProducers();
	
	public List<IStatsProducer> getAllProducersByCategory(String category);
	
	public List<IStatsProducer> getAllProducersBySubsystem(String subsystem);
	
	public IStatsProducer getProducer(String producerId);
	
	public List<IStatsProducer> getProducers(IProducerFilter... filters);
	
	public List<String> getCategories();
	
	public List<String> getSubsystems();
	
	//// convenience methods.
	public List<IntervalInfo> getPresentIntervals();
} 

/* ------------------------------------------------------------------------- *
 * $Log: IProducerRegistryAPI.java,v $
 * Revision 1.3  2008/12/21 20:56:46  metelin
 * *** empty log message ***
 *
 * Revision 1.2  2006/07/22 22:49:19  dvayanu
 * Issue number:  1
 *
 * Revision 1.1  2006/06/11 20:00:56  miros
 * #3: Refactored source files to comply the new project structure.
 *
 * Revision 1.1  2006/06/11 15:15:02  miros
 * #3: Initial commit with new project structure.
 *
 * Revision 1.1  2006/06/07 20:52:39  dvayanu
 * initial
 *
 * Revision 1.2  2006/05/28 23:25:08  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/05/26 15:42:30  lrosenberg
 * *** empty log message ***
 *
 */