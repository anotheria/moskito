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

import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.List;

/**
 * IStats is the internal representation of a statistical value set. Its multidimensional since it supports simultaneous values for multiple intervals. 
 * It can contain multiple StatValues of different types (long, int, double etc). IStats are 'produced' or gathered by the IStatsProducer.
 * Typical IStats are ServiceStats, CacheStats, MemoryStats etc. 
 *
 * @author lrosenberg
 */
public interface IStats {
	
	/**
	 * This method creates a human-readable textual representation of all statistical
	 * values in all intervals.
	 * 
	 * @return the formatted text output
	 */
	String toStatsString();
	
	/**
	 * This method creates a human-readable textual representation of all statistical
	 * values in the given interval.
	 * 
	 * @param aIntervalName the name of the interval or <code>null</code> for all intervals
	 * @return the formatted text output
	 */
	String toStatsString(String aIntervalName);
	
	/**
	 * This method creates a human-readable textual representation of all statistical
	 * values in all intervals.
	 * 
	 * @return the formatted text output
	 */
	String toStatsString(TimeUnit unit);
	
	/**
	 * This method creates a human-readable textual representation of all statistical
	 * values in the given interval.
	 * 
	 * @param aIntervalName the name of the interval or <code>null</code> for all intervals
	 * @return the formatted text output
	 */
	String toStatsString(String aIntervalName, TimeUnit unit);
	
	
	/**
	 * This method returns the name of this set of statistical values.
	 * 
	 * @return the name
	 */
	String getName();
	
	CallExecution createCallExecution();
	
	String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit);
	/**
	 * Returns the names of all contained stats.
	 * @return
	 */
	List<String> getAvailableValueNames();
	
	/**
	 * Returns true if this stat object didn't record anything of interest in a given interval. This is useful to reduce garbage.
	 * @return
	 */
	boolean isEmpty(String intervalName);
}
