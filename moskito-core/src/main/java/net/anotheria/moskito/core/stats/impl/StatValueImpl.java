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
package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.UnknownIntervalException;
import net.anotheria.moskito.core.stats.ValueHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements StatValue.
 * 
 * @author lrosenberg
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
	protected ValueHolder absoluteValue;
	
	/**
	 * This map holds all values by the interval name it is responsible for. So every 
	 * registered Interval will be represented by an own entry. Besides this Map contains 
	 * the absolute value.
	 */
	protected Map<String, ValueHolder> values;
	
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
	 * This is the constructor.
	 * 
	 * @param aName the name of the statistic value
	 * @param aFactory the factory to create ValueHolder instances on Interval registration
	 */
	public StatValueImpl(String aName, IValueHolderFactory aFactory){
		name = aName;
		factory = aFactory;
		values = new HashMap<>();
		valuesAsList = new ArrayList<>();
		// now we have to add the ValueHolder for the absolute value
		absoluteValue = factory.createValueHolder(ABSOLUTE_VALUE);
		values.put(ABSOLUTE_VALUE.getName(), absoluteValue);
		valuesAsList.add(absoluteValue);
	}

	@Override
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

	@Override
	public String getName(){
		return name;
	}

	@Override
	public void increase(){
		for (ValueHolder holder : valuesAsList)
			holder.increase();
	}

	@Override
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

 	@Override public int getValueAsInt(){
		return getValueAsInt(null);
	}

	@Override
	public long getValueAsLong(){
		return getValueAsLong(null);
	}

	@Override
	public double getValueAsDouble(){
		return getValueAsDouble(null);
	}

	@Override
	public int getValueAsInt(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsInt();
		return getHolderByIntervalName(aIntervalName).getValueAsInt();
	}

	@Override
	public long getValueAsLong(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsLong();
		return getHolderByIntervalName(aIntervalName).getValueAsLong();
	}

	@Override
	public double getValueAsDouble(String aIntervalName){
		// TODO: possibly, the check for DEFAULT can be removed, because it is contained in values too.
		if (aIntervalName==null || aIntervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsDouble();
		return getHolderByIntervalName(aIntervalName).getValueAsDouble();
	}

	@Override
	public void setValueAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsInt(aValue);
	}

	@Override
	public void setValueAsString(String aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsString(aValue);
	}

	@Override
	public void setValueAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsLong(aValue);
	}

	@Override
	public void setValueAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setValueAsDouble(aValue);
	}

	@Override
	public void increaseByInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByInt(aValue);
	}

	@Override
	public void increaseByLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByLong(aValue);
	}

	@Override public void increaseByDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.increaseByDouble(aValue);
	}

	@Override public void decreaseByInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByInt(aValue);
	}

	@Override
	public void decreaseByLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByLong(aValue);
	}

	@Override
	public void decreaseByDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.decreaseByDouble(aValue);
	}

	@Override
	public void setDefaultValueAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsLong(aValue);
	}

	@Override
	public void setDefaultValueAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsInt(aValue);
	}

	@Override public void setDefaultValueAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			holder.setDefaultValueAsDouble(aValue);
	}

	@Override
	public void reset(){
		for (ValueHolder holder : valuesAsList)
			holder.reset();
	}

	@Override
	public void setValueIfGreaterThanCurrentAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsLong() < aValue)
				holder.setValueAsLong(aValue);
	}

	@Override
	public void setValueIfGreaterThanCurrentAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsInt() < aValue)
				holder.setValueAsInt(aValue);
	}

	@Override
	public void setValueIfGreaterThanCurrentAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsDouble() < aValue)
				holder.setValueAsDouble(aValue);
	}


	@Override
	public void setValueIfLesserThanCurrentAsLong(long aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsLong() > aValue)
				holder.setValueAsLong(aValue);
	}

	@Override
	public void setValueIfLesserThanCurrentAsInt(int aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsInt() > aValue)
				holder.setValueAsInt(aValue);
	}

	@Override
	public void setValueIfLesserThanCurrentAsDouble(double aValue){
		for (ValueHolder holder : valuesAsList)
			if (holder.getCurrentValueAsDouble() > aValue)
				holder.setValueAsDouble(aValue);
	}
	
	public String getValueAsString(String intervalName){
		if (intervalName==null || intervalName.equals(ABSOLUTE_VALUE.getName()))
			return absoluteValue.getCurrentValueAsString();
		return getHolderByIntervalName(intervalName).getValueAsString();

	}
	
	public String getValueAsString(){
		return getValueAsString(null);
	}

	@Override
	public void destroy() {
		IntervalRegistry reg = IntervalRegistry.getInstance();
		for (Map.Entry<String, ValueHolder> entry :values.entrySet()){
			if (entry.getValue() instanceof IIntervalListener)
				reg.getInterval(entry.getKey()).removePrimaryIntervalListener((IIntervalListener)entry.getValue());
		}
	}
}
