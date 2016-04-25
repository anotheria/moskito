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
package net.anotheria.moskito.core.producers;

import java.util.List;

/**
 * This interface declares a stats producer. A stats producer is something that produces stats, it might be 
 * a servlet, an action, a service, but also an exectuion pattern. Basically anything you want to monitor is a stats producer since it produces 
 * monitoring stats. A stats producer is also referred to as monitoring point.
 * @param <S> a subclass of IStats.
 * @author lrosenberg
 */
public interface IStatsProducer<S extends IStats> {

	/**
	 * Returns the list of all stats.
	 * 
	 * @return all stats this producer produces / generates.
	 */
	List<S> getStats();

	/**
	 * Returns the meaningful id of this producer. This should be something like a class or
	 * interface name.
	 * 
	 * @return a string which provides unique identification of this producer, i.e. the class name
	 *         or a readable form of it.
	 */
	String getProducerId();

	/**
	 * Returns the category of this producer. A typical categorie is something like servlet, action,
	 * service or dao. The categories are used to group different StatsProducer to get overview over
	 * a certain layer in the application, i.e. show me all action.
	 * 
	 * @return the id of the category the producer belongs to.
	 */
	String getCategory();

	/**
	 * Returns the subsystem the current producer is located in. Moskito referrs to your application
	 * as "the system". If you want to filter some parts of your system separately you should use
	 * the getSubsystem() method to separate them. Possible subsystems are: usermanagement,
	 * messaging... whatever. The getCategory() method should separate parts of the application
	 * horizontally, whether the getSubsystem() method should provide vertical separation.
	 * 
	 * @return the id of the subsystem the producer belongs to.
	 */
	String getSubsystem();

}
