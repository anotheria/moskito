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
import net.java.dev.moskito.core.stats.StatValue;
import net.java.dev.moskito.core.stats.StatValueTypes;

/**
 * This factory creates StatValue instances.
 * @author lrosenberg
 */
public class StatValueFactory {

	/**
	 * This method creates a new StatValue instance. 
	 * The new object will be from the same type as the given template object.
	 * 
	 * @param aPattern the template object
	 * @param aName the name of the value
	 * @param aIntervals the list of Intervals to be used
	 * @return the StatValue instance
	 */
	public static final StatValue createStatValue(Object aPattern, String aName, Interval[] aIntervals) {
		return createStatValue(StatValueTypeUtility.object2type(aPattern),aName, aIntervals);
	}

	/**
	 * This method creates a StatValue instance.
	 * 
	 * @param aType the type of the value
	 * @param aName the name of the value
	 * @param aIntervals the list of Intervals to be used
	 * @return the StatValue instance
	 */
	public static StatValue createStatValue(StatValueTypes aType, String aName, Interval[] aIntervals) {
		IValueHolderFactory valueHolderFactory = StatValueTypeUtility.createValueHolderFactory(aType);
		StatValue value = new StatValueImpl(aName, valueHolderFactory);
		// now we have to add the Intervals to the new value....
		for (int i = 0; i < aIntervals.length; i++) {
			value.addInterval(aIntervals[i]);
		}
		return value;
	}

}
