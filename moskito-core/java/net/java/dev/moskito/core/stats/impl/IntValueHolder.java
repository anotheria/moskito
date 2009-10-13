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

import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.ValueHolder;

/**
 * This class implements a ValueHolder that is able to process int values.
 * 
 * @author dvayanu
 * @see ValueHolder
 */
class IntValueHolder extends AbstractValueHolder {

	/**
	 * This is the value that will be used as defaultValue if no other one was specified.
	 */
	public static final int DEFAULT_DEFAULT_VALUE = 0;

	/**
	 * This attribute stores the current value supporting concurrent access.
	 */
	private AtomicInteger currentValue;

	/**
	 * The last value will be stored here.
	 */
	private int lastValue;

	/**
	 * This is the default value that this ValueHolder contains after a reset.
	 */
	private int defaultValue;

	/**
	 * The Constructor.
	 * 
	 * @param aInterval this is the Interval this value will be updated
	 */
	public IntValueHolder(Interval aInterval) {
		super(aInterval);
		defaultValue = DEFAULT_DEFAULT_VALUE;
		currentValue = new AtomicInteger(defaultValue);
	}

	@Override public void intervalUpdated(Interval aCaller) {
		lastValue = currentValue.getAndSet(defaultValue);
	}

	/**
	 * This method returns the lastValue.
	 * 
	 * @return the lastValue
	 */
	public int getLastValue() {
		return lastValue;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.impl.AbstractValueHolder#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " I " + lastValue + " / "
				+ currentValue.get();
	}

	@Override public void decrease() {
		currentValue.decrementAndGet();
	}

	@Override public void increase() {
		currentValue.incrementAndGet();
	}

	@Override public void decreaseByDouble(double aValue) {
		decreaseByInt((int) aValue);
	}

	@Override public void decreaseByInt(int aValue) {
		currentValue.addAndGet(-aValue);
	}

	@Override public void decreaseByLong(long aValue) {
		decreaseByInt((int) aValue);
	}

	@Override public double getValueAsDouble() {
		return (double) getValueAsInt();
	}

	@Override public int getValueAsInt() {
		return lastValue;
	}

	@Override public long getValueAsLong() {
		return (long) getValueAsInt();
	}

	@Override public void increaseByDouble(double aValue) {
		increaseByInt((int) aValue);
	}

	@Override public void increaseByInt(int aValue) {
		currentValue.addAndGet(aValue);
	}

	@Override public void increaseByLong(long aValue) {
		increaseByInt((int) aValue);
	}

	@Override public void setValueAsDouble(double aValue) {
		setValueAsInt((int) aValue);
	}

	@Override public void setValueAsInt(int aValue) {
		currentValue.set(aValue);
	}

	@Override public void setValueAsLong(long aValue) {
		setValueAsInt((int) aValue);
	}

	@Override public void setDefaultValueAsLong(long aValue) {
		setDefaultValueAsInt((int) aValue);
	}

	@Override public void setDefaultValueAsInt(int aValue) {
		defaultValue = aValue;
	}

	@Override public void setDefaultValueAsDouble(double aValue) {
		setDefaultValueAsInt((int) aValue);
	}

	@Override public void reset() {
		currentValue.set(defaultValue);
		lastValue = defaultValue;
	}

	@Override public double getCurrentValueAsDouble() {
		return (double) getCurrentValueAsLong();
	}

	@Override public int getCurrentValueAsInt() {
		return currentValue.get();
	}

	@Override public long getCurrentValueAsLong() {
		return (long) getCurrentValueAsInt();
	}

}
