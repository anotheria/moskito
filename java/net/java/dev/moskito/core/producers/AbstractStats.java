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
package net.java.dev.moskito.core.producers;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.java.dev.moskito.core.stats.TimeUnit;

/**
 * This abstract class is the super class of all statistical value sets.
 * 
 * @author dvayanu
 */
public abstract class AbstractStats implements IStats {

	private String name;
	
	protected AbstractStats(){
		this("unnamed");
	}
	
	protected AbstractStats(String aName){
		name = aName;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * @see net.java.dev.moskito.core.producers.IStats#toStatsString()
	 */
	public String toStatsString() {
		return toStatsString(TimeUnit.MILLISECONDS);
	}

	public String toStatsString(String intervalName) {
		return toStatsString(intervalName, TimeUnit.MILLISECONDS);
	}
	
	public String toStatsString(TimeUnit timeUnit) {
		return toStatsString(null, timeUnit);
	}


	/**
	 * To create snapshots IStats implementation must use getters with primitive return types for its properties.
	 * In order to create a snapshot this implementation walks through all stats properties.
	 * The stats properties are identified by corresponding cob=nvenience getter method that returns one of:
	 * int, long, double
	 * 
	 * @see net.java.dev.moskito.core.producers.IStats#createSnapshot(java.lang.String)
	 */
	public IStatsSnapshot createSnapshot(String aIntervalName){
		
		Date dateCreatedNow = new Date();
		Map<String, Number> snapshotProperties = new HashMap<String, Number>();
		Method[] methods = this.getClass().getMethods();
		try {
			for (Method getter : methods) {
				Class returnType = getter.getReturnType();
				if (getter.getName().startsWith("get") && getter.getParameterTypes().length == 0 
						&& (returnType.isAssignableFrom(int.class) || returnType.isAssignableFrom(long.class) || returnType.isAssignableFrom(double.class))) {
					snapshotProperties.put(getter.getName().substring(3), (Number) getter.invoke(this, null));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("To create snapshots IStats implementation must use getters with primitive return types for its properties");
		}
		
		DefaultStatsSnapshot snapshot = new DefaultStatsSnapshot();
		snapshot.setProperties(snapshotProperties);
		snapshot.setName(getName());
		snapshot.setDateCreated(dateCreatedNow);
		return snapshot;
	}
	
	public CallExecution createCallExecution(){
		throw new AssertionError("Not implemented");
	}

}
