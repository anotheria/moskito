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

import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.ValueHolder;

/**
 * This class implements a ValueHolder that is able to process int values.
 * 
 * @author lrosenberg
 * @see ValueHolder
 */
class StringValueHolder extends AbstractValueHolder {

	/**
	 * This is the value that will be used as defaultValue if no other one was specified.
	 */
	public static final String DEFAULT_DEFAULT_VALUE = "";

	/**
	 * This attribute stores the current value supporting concurrent access.
	 */
	private String currentValue;

	/**
	 * The value measured in the last interval. 
	 */
	private String lastValue;

	/**
	 * This is the default value that this ValueHolder contains after a reset.
	 */
	private String defaultValue;

	/**
	 * The Constructor.
	 * 
	 * @param aInterval this is the Interval this value will be updated
	 */
	public StringValueHolder(Interval aInterval) {
		super(aInterval);
		defaultValue = DEFAULT_DEFAULT_VALUE;
		currentValue = "";
	}

	@Override public void intervalUpdated(Interval aCaller) {
		lastValue = currentValue;
	}

	/**
	 * This method returns the lastValue.
	 * 
	 * @return the lastValue
	 */
	public String getLastValue() {
		return lastValue;
	}

	@Override public String toString() {
		return super.toString() + " I " + lastValue + " / " + currentValue;
	}

	@Override public void decrease() {
		throw new UnsupportedOperationException();
	}

	@Override public void increase() {
		throw new UnsupportedOperationException();
	}

	@Override public void decreaseByDouble(double aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public void decreaseByInt(int aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public void decreaseByLong(long aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public double getValueAsDouble() {
		return getValueAsInt();
	}

	@Override public int getValueAsInt() {
		return Integer.parseInt(lastValue);
	}

	@Override public long getValueAsLong() {
		return getValueAsInt();
	}

	@Override public void increaseByDouble(double aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public void increaseByInt(int aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public void increaseByLong(long aValue) {
		throw new UnsupportedOperationException();
	}

	@Override public void setValueAsDouble(double aValue) {
		setValueAsInt((int) aValue);
	}

	@Override public void setValueAsInt(int aValue) {
		currentValue = ""+aValue;
	}

	@Override public void setValueAsLong(long aValue) {
		setValueAsInt((int) aValue);
	}

	@Override public void setDefaultValueAsLong(long aValue) {
		setDefaultValueAsInt((int) aValue);
	}

	@Override public void setDefaultValueAsInt(int aValue) {
		defaultValue = ""+aValue;
	}

	@Override public void setDefaultValueAsDouble(double aValue) {
		setDefaultValueAsInt((int) aValue);
	}

	@Override public void reset() {
		currentValue = defaultValue;
		lastValue = defaultValue;
	}

	@Override public double getCurrentValueAsDouble() {
		return getCurrentValueAsLong();
	}

	@Override public int getCurrentValueAsInt() {
		return Integer.parseInt(currentValue);
	}

	@Override public long getCurrentValueAsLong() {
		return getCurrentValueAsInt();
	}
	
	@Override public void setValueAsString(String aValue){
		currentValue = aValue;
	}
	
	@Override public String getValueAsString(){
		return lastValue;
	}
	@Override public String getCurrentValueAsString() {
		return ""+currentValue;
	}

}
