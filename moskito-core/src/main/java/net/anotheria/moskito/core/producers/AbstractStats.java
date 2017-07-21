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

import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class is the super class of all statistical value sets.
 * @author lrosenberg
 */
public abstract class AbstractStats implements IStats, StatsMXBean{
	
	/**
	 * Constant for MegaByte.
	 */
	protected static final long MB = 1024L*1024;

	/**
	 * Name of the stats object. This can be a method name, an url, a business key figure etc.
	 */
	private String name;
	/**
	 * Cached empty list object.
	 */
	private static final List<String> EMPTY_LIST = Collections.emptyList();

	/**
	 * Contains all stat values that are part of this StatsObject. This is used to properly destroy them.
	 */
	private LinkedList<StatValue> statValuesList = new LinkedList<StatValue>();

	/**
	 * Creates a new AbstractStats object.
	 */
	protected AbstractStats(){
		this("unnamed");
	}
	
	/**
	 * Creates a new AbstractStats object with given name.
	 * @param aName name of the stats object.
	 */
	protected AbstractStats(String aName){
		name = aName;
	}
	
	public String getName(){
		return name;
	}
	
	@Override public String toStatsString() {
		return toStatsString(TimeUnit.MILLISECONDS);
	}

	@Override public String toStatsString(String intervalName) {
		return toStatsString(intervalName, TimeUnit.MILLISECONDS);
	}
	
	@Override public String toStatsString(TimeUnit timeUnit) {
		return toStatsString(null, timeUnit);
	}

	/**
	 * Not supported by default. 
	 */
	public CallExecution createCallExecution(){
		throw new AssertionError("Not implemented");
	}
	
	@Override
	public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit){
		return null;
	}

	@Override
	public List<String> getAvailableValueNames() {
		return EMPTY_LIST;
	}
	
	@Override 
	public String toString(){
		return getName();
	}

	@Override public boolean isEmpty(String intervalName){
		return false;
	}

	@Override
	public void destroy() {
		for (StatValue v : statValuesList){
			v.destroy();
		}
	}

	protected void addStatValues(StatValue... values){
		if (values==null)
			return;
		Collections.addAll(statValuesList, values);
	}
}
