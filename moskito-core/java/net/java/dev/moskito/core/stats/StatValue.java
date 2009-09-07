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
package net.java.dev.moskito.core.stats;



/**
 * This interface declares a generic statistic value that may record data series for certain 
 * Intervals synchronously. That means, that if there is more than one Interval registered, 
 * the same value will be collected in all of these intervals.<br>    
 * Furthermore this class provides some convenience methods to manipulate the value, 
 * possibly depending on comparison operations.  
 * 
 * @see Interval
 * @author miros
 */
public interface StatValue {

	/**
	 * This method adds the given Interval. After every value manipulation 
	 * will be delegated to this Interval too.  
	 * 
	 * @param aInterval the Interval to add
	 */
	public void addInterval(Interval aInterval);

	/**
	 * This method returns the name of this statistic value.
	 * @return the name
	 */
	public String getName();

	/**
	 * This method increases the stored values for all Intervals by one.
	 * Particularly, this includes the absolute value.
	 */
	public void increase();

	/**
	 * This method decreases the stored values for all Intervals by one.
	 * Particularly, this includes the absolute value.
	 */
	public void decrease();

	/**
	 * This method returns the absolute value as int.
	 * 
	 * @return the absolute value
	 */
	public int getValueAsInt();

	/**
	 * This method returns the absolute value as long.
	 * 
	 * @return the absolute value
	 */
	public long getValueAsLong();

	/**
	 * This method returns the absolute value as double.
	 * 
	 * @return the absolute value
	 */
	public double getValueAsDouble();

	/**
	 * This method returns the current value of a specific Interval as int.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	public int getValueAsInt(String aIntervalName);

	/**
	 * This method returns the current value of a specific Interval as long.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	public long getValueAsLong(String aIntervalName);

	/**
	 * This method returns the current value of a specific Interval as double.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	public double getValueAsDouble(String aIntervalName);

	/**
	 * This method sets the given int value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueAsInt(int aValue);

	/**
	 * This method sets the given long value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueAsLong(long aValue);

	/**
	 * This method sets the given double value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueAsDouble(double aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given int value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	public void increaseByInt(int aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given long value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	public void increaseByLong(long aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given double value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	public void increaseByDouble(double aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given int value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	public void decreaseByInt(int aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given long value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	public void decreaseByLong(long aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given double value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	public void decreaseByDouble(double aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	public void setDefaultValueAsLong(long aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	public void setDefaultValueAsInt(int aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	public void setDefaultValueAsDouble(double aValue);

	/**
	 * This method resets the ValueHolders of all registered Intervals.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" on reset. 
	 */
	public void reset();

	/**
	 * This method sets the given long value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfGreaterThanCurrentAsLong(long aValue);

	/**
	 * This method sets the given int value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfGreaterThanCurrentAsInt(int aValue);

	/**
	 * This method sets the given double value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfGreaterThanCurrentAsDouble(double aValue);

	/**
	 * This method sets the given long value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfLesserThanCurrentAsLong(long aValue);

	/**
	 * This method sets the given int value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfLesserThanCurrentAsInt(int aValue);

	/**
	 * This method sets the given double value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	public void setValueIfLesserThanCurrentAsDouble(double aValue);

}