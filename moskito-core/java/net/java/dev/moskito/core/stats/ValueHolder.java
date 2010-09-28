/*
 * $Id$
 * $Author$
 *
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.java.net.
 * 
 * Copyright (c) 2006 by MoSKito Project
 *
 * All MoSKito files are under MIT License: 
 * http://www.opensource.org/licenses/mit-license.php
 */
package net.java.dev.moskito.core.stats;

/**
 * This interface desclares any type-specific holder 
 * implementation and declares the interface to support some basic mathematical operations 
 * needed for statistical aggregations.
 *
 * @author lrosenberg
 */
public interface ValueHolder {
	/**
	 * This method shall reset the ValueHolder. After calling reset() it must have 
	 * the same state as directly after creation. 
	 */
	void reset();

	/**
	 * This method shall increment the value by one.
	 */
	void increase();
	
	/**
	 * This method shall decrement the value by one.
	 */
	void decrease();
	
	/**
	 * This method shall return the current value as int.
	 * 
	 * @return the current value
	 */
	int getValueAsInt();
	
	/**
	 * This method shall return the current value as long.
	 * 
	 * @return the current value
	 */
	long getValueAsLong();
	
	/**
	 * This method shall return the current value as double.
	 * 
	 * @return the current value
	 */
	double getValueAsDouble();
	
	/**
	 * This method shall return the current value as int for internal use only.
	 * 
	 * @return the current value
	 */
	int getCurrentValueAsInt();
	/**
	 * This method shall return the current value as long for internal use only.
	 * 
	 * @return the current value
	 */
	long getCurrentValueAsLong();
	/**
	 * This method shall return the current value as double for internal use only.
	 * 
	 * @return the current value
	 */
	double getCurrentValueAsDouble();
	
	/**
	 * This method shall assign the given value to the current value.
	 *
	 * @param aValue the new value
	 */
	void setValueAsInt(int aValue);
	
	/**
	 * This method shall assign the given value to the current value.
	 *
	 * @param aValue the new value
	 */
	void setValueAsLong(long aValue);

	/**
	 * This method shall assign the given value to the current value.
	 *
	 * @param aValue the new value
	 */
	void setValueAsDouble(double aValue);
	
	/**
	 * This method shall increment the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void increaseByInt(int aValue);

	/**
	 * This method shall increment the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void increaseByLong(long aValue);

	/**
	 * This method shall increment the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void increaseByDouble(double aValue);
	
	/**
	 * This method shall decrement the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void decreaseByInt(int aValue);

	/**
	 * This method shall decrement the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void decreaseByLong(long aValue);

	/**
	 * This method shall decrement the current value by the given amount.
	 * 
	 * @param aValue the amount
	 */
	void decreaseByDouble(double aValue);	
	
	/**
	 * This method shall set a new default value. The default value shall be set 
	 * as current value if the reset method will be called. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsInt(int aValue);

	/**
	 * This method shall set a new default value. The default value shall be set 
	 * as current value if the reset method will be called. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsLong(long aValue);

	/**
	 * This method shall set a new default value. The default value shall be set 
	 * as current value if the reset method will be called. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsDouble(double aValue);
	
}
