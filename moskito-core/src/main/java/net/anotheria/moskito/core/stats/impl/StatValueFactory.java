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

import java.util.Collection;
import java.util.function.LongSupplier;

import net.anotheria.moskito.core.stats.DetailedStatValue;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

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
	public static TypeAwareStatValue createStatValue(StatValueTypes aType, String aName, Interval[] aIntervals) {
		return createStatValue(aType, aName, aIntervals, StatValueTypeUtility.createValueHolderFactory(aType));
	}

	/**
	 * Creates a {@link StatValueTypes#LONG} stat whose value at the start of each interval is provided dynamically.
	 * Used for the min/max of an exactly tracked level (e.g. current requests), which has to start each interval at the
	 * current level rather than at a constant default.
	 *
	 * @param aName the name of the value
	 * @param aIntervals the intervals to be used
	 * @param intervalStartValueSupplier supplies the value to carry into each new interval
	 * @return the stat value
	 */
	public static TypeAwareStatValue createStatValue(String aName, Interval[] aIntervals,
			LongSupplier intervalStartValueSupplier) {
		return createStatValue(StatValueTypes.LONG, aName, aIntervals,
				new IntervalStartValueHolderFactory(intervalStartValueSupplier));
	}

	/**
	 * Creates the min of a sampled level (e.g. memory): the smallest value set within an interval, or the level itself
	 * for an interval without values. Feed it with setValueAsLong, not with setValueIfLesserThanCurrent.
	 *
	 * @param aName the name of the value
	 * @param aIntervals the intervals to be used
	 * @param level the sampled level
	 * @return the stat value
	 */
	public static TypeAwareStatValue createSampledMinStatValue(String aName, Interval[] aIntervals, LongSupplier level) {
		return createStatValue(StatValueTypes.LONG, aName, aIntervals, new SampledExtremeValueHolderFactory(false, level));
	}

	/**
	 * Creates the max of a sampled level (e.g. memory): the largest value set within an interval, or the level itself
	 * for an interval without values. Feed it with setValueAsLong, not with setValueIfGreaterThanCurrent.
	 *
	 * @param aName the name of the value
	 * @param aIntervals the intervals to be used
	 * @param level the sampled level
	 * @return the stat value
	 */
	public static TypeAwareStatValue createSampledMaxStatValue(String aName, Interval[] aIntervals, LongSupplier level) {
		return createStatValue(StatValueTypes.LONG, aName, aIntervals, new SampledExtremeValueHolderFactory(true, level));
	}

	private static TypeAwareStatValue createStatValue(StatValueTypes aType, String aName, Interval[] aIntervals,
			IValueHolderFactory valueHolderFactory) {
		TypeAwareStatValue value = new TypeAwareStatValueImpl(aName, aType, valueHolderFactory);
		for (Interval interval : aIntervals) {
			value.addInterval(interval);
		}
		return value;
	}
	
	public static TypeAwareStatValue createStatValue(StatValueTypes aType, String aName,
			Collection<String> aIntervals) {
		Interval[] arrayIntervals = new Interval[aIntervals.size()];

		int i = 0;

		for (String interval : aIntervals) {
			arrayIntervals[i++] = IntervalRegistry.getInstance().getInterval(interval);
		}

		return createStatValue(aType, aName, arrayIntervals);
	}

	public static final DetailedStatValue createDetailedStatValue(Object aPattern, String aName,
			Interval[] aIntervals) {
		return createDetailedStatValue(StatValueTypeUtility.object2type(aPattern), aName, aIntervals);
	}

	public static DetailedStatValue createDetailedStatValue(StatValueTypes aType, String aName, Interval[] aIntervals) {
		IValueHolderFactory valueHolderFactory = StatValueTypeUtility.createValueHolderFactory(aType);
		DetailedStatValue value = new DetailedTypeAwareStatValueImpl(aName, aType, valueHolderFactory);
		// now we have to add the Intervals to the new value....
		for (Interval aInterval : aIntervals) {
			value.addInterval(aInterval);
		}

		value.reset();

		return value;
	}

}
