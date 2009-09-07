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
package net.java.dev.moskito.core.stats.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.dev.moskito.core.stats.IValueHolderFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.UnknownIntervalException;
import net.java.dev.moskito.core.stats.ValueHolder;

/**
 * This class implements StatValue.
 * 
 * @author dvayanu
 * @see StatValue
 * @see AbstractValueHolder
 */
class StatValueImpl implements StatValue {
	/**
	 * This is the default Interval for the absolute value.
	 */
	private static final Interval ABSOLUTE_VALUE = IntervalRegistry.getInstance().getInterval("default", 0);
	
	/**
	 * This attribute holds the absolute value that does not depend on an Interval.
	 * It will be collected for every instance of this statistic value.
	 */
	private ValueHolder absoluteValue;
	
	/**
	 * This map holds all values by the interval name it is responsible for. So every 
	 * registered Interval will be represented by an own entry. Besides this Map contains 
	 * the abolute value. 
	 */
	private Map<String, ValueHolder> values;
	
	/**
	 * This List contains a redundant copy of the values Map values. It will be maintained 
	 * on adding new Intervals.  
	 */
	private List<ValueHolder> valuesAsList; 

	/**
	 * This is the name of statistic value represented by this instance.
	 */
	private String name;
	
	/**
	 * This is the factory instance that is responsible for creation of new ValueHolder instances
	 * and will be used mainly on registering new Intervals.
	 */
	private transient IValueHolderFactory factory;
	
	/**
	 * This is the contructor.
	 * 
	 * @param aName the name of the statistic value
	 * @param aFactory the factory to create ValueHolder instances on Interval registration
	 */
	public StatValueImpl(String aName, IValueHolderFactory aFactory){
		name = aName;
		factory = aFactory;
		values = new HashMap<String, ValueHolder>();
		valuesAsList = new ArrayList<ValueHolder>();
		// now we have to add the ValueHolder for the absolute value
		absoluteValue = factory.createValueHolder(ABSOLUTE_VALUE);
		values.put(ABSOLUTE_VALUE.getName(), absoluteValue);
		valuesAsList.add(absoluteValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#addInterval(net.java.dev.moskito.core.stats.Interval)
	 */
	public void addInterval(Interval aInterval){
		ValueHolder h = factory.createValueHolder(aInterval);
		values.put(aInterval.getName(), h);
		valuesAsList.add(h);
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return name+": "+ valuesAsList;
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getName()
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#increase()
	 */
	public void increase(){
		for (ValueHolder holder : valuesAsList)
			holder.increase();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#decrease()
	 */
	public void decrease(){
		for (ValueHolder holder : valuesAsList)
			holder.decrease();
	}
	
	/**
	 * This method returns the ValueHolder that is stored for the given Interval name.
	 * 
	 * @param aIntervalName the name of the interval 
	 * @return the stored ValueHolder
	 * @throws UnknownIntervalException if there is no ValueHolder stored for an Interval with the given name
	 */
	private ValueHolder getHolderByIntervalName(String aIntervalName){
		ValueHolder h = values.get(aIntervalName);
		if (h == null)
			throw new UnknownIntervalException(aIntervalName);
		return h;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsInt()
	 */
	public int getValueAsInt(){
		return getValueAsInt(null);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsLong()
	 */
	public long getValueAsLong(){
		return getValueAsLong(null);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsDouble()
	 */
	public double getValueAsDouble(){
		return getValueAsDouble(null);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsInt(java.lang.String)
	 */
	public int getValueAsInt(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsInt();
		return getHolderByIntervalName(aIntervalName).getValueAsInt();
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsLong(java.lang.String)
	 */
	public long getValueAsLong(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsLong();
		return getHolderByIntervalName(aIntervalName).getValueAsLong();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#getValueAsDouble(java.lang.String)
	 */
	public double getValueAsDouble(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsDouble();
		return getHolderByIntervalName(aIntervalName).getValueAsDouble();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueAsInt(int)
	 */
	public void setValueAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsInt(aValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueAsLong(long)
	 */
	public void setValueAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsLong(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueAsDouble(double)
	 */
	public void setValueAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsDouble(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#increaseByInt(int)
	 */
	public void increaseByInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByInt(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#increaseByLong(long)
	 */
	public void increaseByLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByLong(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#increaseByDouble(double)
	 */
	public void increaseByDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByDouble(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#decreaseByInt(int)
	 */
	public void decreaseByInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByInt(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#decreaseByLong(long)
	 */
	public void decreaseByLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByLong(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#decreaseByDouble(double)
	 */
	public void decreaseByDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByDouble(aValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setDefaultValueAsLong(long)
	 */
	public void setDefaultValueAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsLong(aValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setDefaultValueAsInt(int)
	 */
	public void setDefaultValueAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsInt(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setDefaultValueAsDouble(double)
	 */
	public void setDefaultValueAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsDouble(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#reset()
	 */
	public void reset(){
		for (ValueHolder holder : valuesAsList)
			holder.reset();
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfGreaterThanCurrentAsLong(long)
	 */
	public void setValueIfGreaterThanCurrentAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsLong() < aValue)
				holder.setValueAsLong(aValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfGreaterThanCurrentAsInt(int)
	 */
	public void setValueIfGreaterThanCurrentAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsInt() < aValue)
				holder.setValueAsInt(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfGreaterThanCurrentAsDouble(double)
	 */
	public void setValueIfGreaterThanCurrentAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsDouble() < aValue)
				holder.setValueAsDouble(aValue);
	}


	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfLesserThanCurrentAsLong(long)
	 */
	public void setValueIfLesserThanCurrentAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsLong() > aValue)
				holder.setValueAsLong(aValue);
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfLesserThanCurrentAsInt(int)
	 */
	public void setValueIfLesserThanCurrentAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsInt() > aValue)
				holder.setValueAsInt(aValue);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.StatValue#setValueIfLesserThanCurrentAsDouble(double)
	 */
	public void setValueIfLesserThanCurrentAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsDouble() > aValue)
				holder.setValueAsDouble(aValue);
	}
}
