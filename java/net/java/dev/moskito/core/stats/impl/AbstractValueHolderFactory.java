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

import net.java.dev.moskito.core.stats.IValueHolderFactory;
import net.java.dev.moskito.core.stats.Interval;

/**
 * This is the abstract super class of all concrete factories that create new ValueHolder instances.
 * 
 * @author dvayanu
 * @see AbstractValueHolder
 * @see Interval
 * @see IValueHolderFactory
 */
abstract class AbstractValueHolderFactory implements IValueHolderFactory {

	/**
	 * This method creates the new ValueHolder instance.
	 * 
	 * @param aInterval the Interval to be used
	 * @return the new ValueHolder instance
	 */
	protected abstract AbstractValueHolder createValueHolderObject(Interval aInterval);

	/**
	 * @see net.java.dev.moskito.core.stats.IValueHolderFactory#createValueHolder(net.java.dev.moskito.core.stats.impl.IntervalImpl)
	 */
	public final AbstractValueHolder createValueHolder(Interval aInterval) {
		AbstractValueHolder holder;
		// create the instance using the internal factory method using the given interval
		holder = createValueHolderObject(aInterval);
		aInterval.addPrimaryIntervalListener(holder);
		return holder;
	}
}
