/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://www.moskito.org.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006-2014 The MoSKito Project Team.
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

import net.anotheria.moskito.core.stats.Interval;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This class implements a ValueHolder that is able to process long values.
 * @author lrosenberg
 * @see net.anotheria.moskito.core.stats.ValueHolder
 */
class LongValueHolder extends AbstractValueHolder {

	/**
	 * This is the value that will be used as defaultValue if no other one was specified.
	 */
	public static final long DEFAULT_DEFAULT_VALUE = 0;

	/**
	 * This attribute stores the current value supporting concurrent access.
	 */
	private AtomicLong currentValue;

	/**
	 * The last value will be stored here.
	 */
	private long lastValue;

	/**
	 * This is the default value that this ValueHolder contains after a reset.
	 */
	private long defaultValue;

	/**
	 * The Constructor.
	 * 
	 * @param aInterval this is the Interval this value will be updated
	 */
	public LongValueHolder(Interval aInterval) {
		super(aInterval);
		defaultValue = DEFAULT_DEFAULT_VALUE;
		currentValue = new AtomicLong(defaultValue);
	}

	/**
	 * @see net.anotheria.moskito.core.stats.IIntervalListener#intervalUpdated(Interval)
	 */
	@Override public void intervalUpdated(Interval caller) {
		lastValue = currentValue.getAndSet(defaultValue);
	}

	/**
	 * @see net.anotheria.moskito.core.stats.IIntervalListener#intervalUpdated(Interval)
	 */
	protected void updateLastValueFromCurrent() {
		lastValue = currentValue.get();
	}

	/**
	 * This method returns the lastValue.
	 * 
	 * @return the lastValue
	 */
	public long getLastValue() {
		return lastValue;
	}

	/**
	 * @see AbstractValueHolder#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " L " + lastValue + " / "	+ currentValue.get();
	}

	@Override public void decrease() {
		currentValue.decrementAndGet();
	}

	/**
	 * @see net.anotheria.moskito.core.stats.ValueHolder#increase()
	 */
	@Override public void increase() {
		currentValue.incrementAndGet();
	}

	@Override public void decreaseByDouble(double aValue) {
		decreaseByLong((long) aValue);
	}

	@Override public void decreaseByInt(int aValue) {
		decreaseByLong(aValue);
	}

	@Override public void decreaseByLong(long aValue) {
		currentValue.addAndGet(-aValue);
	}

	@Override public double getValueAsDouble() {
        return lastValue;
    }

	@Override public int getValueAsInt() {
        return (int) lastValue;
	}

	@Override public long getValueAsLong() {
		return lastValue;
	}

	@Override public void increaseByDouble(double aValue) {
		increaseByLong((long) aValue);

	}

	@Override public void increaseByInt(int aValue) {
		increaseByLong(aValue);
	}

	@Override public void increaseByLong(long aValue) {
		currentValue.addAndGet(aValue);
	}

	@Override public void setValueAsDouble(double aValue) {
		setValueAsLong((long) aValue);

	}

	@Override public void setValueAsInt(int aValue) {
		setValueAsLong(aValue);
	}

	@Override public void setValueAsLong(long aValue) {
		currentValue.set(aValue);
	}

	@Override public void setDefaultValueAsLong(long aValue) {
		defaultValue = aValue;
	}

	@Override public void setDefaultValueAsInt(int aValue) {
        defaultValue = (long) aValue;
    }

	@Override public void setDefaultValueAsDouble(double aValue) {
        defaultValue = (long) aValue;
    }

	@Override public void reset() {
		currentValue.set(defaultValue);
		lastValue = defaultValue;
	}

	@Override public double getCurrentValueAsDouble() {
		return getCurrentValueAsLong();
	}

	@Override public int getCurrentValueAsInt() {
		return (int) getCurrentValueAsLong();
	}

	@Override public long getCurrentValueAsLong() {
		return currentValue.get();
	}

	@Override public void setValueAsString(String aValue){
		currentValue.set(Long.parseLong(aValue));
	}

	@Override public String getValueAsString() {
		return String.valueOf(lastValue);
	}

	@Override public String getCurrentValueAsString() {
		return String.valueOf(currentValue.get());
	}

	protected void setLastValue(long aValue){
		lastValue = aValue;
	}

}
