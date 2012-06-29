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
 * This class is a special version of long holder, that does not reset on interval update.
 * @author lrosenberg
 * @see ValueHolder
 */
class CounterValueHolder extends LongValueHolder {

	/**
	 * The Constructor.
	 * 
	 * @param aInterval this is the Interval this value will be updated
	 */
	public CounterValueHolder(Interval aInterval) {
		super(aInterval);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.IIntervalListener#intervalUpdated(net.java.dev.moskito.core.stats.impl.IntervalImpl)
	 */
	@Override public void intervalUpdated(Interval caller) {
		updateLastValueFromCurrent();
	}

	/**
	 * @see net.java.dev.moskito.core.stats.impl.AbstractValueHolder#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " CL " + getLastValue() + " / "	+ getCurrentValueAsLong();
	}

}
