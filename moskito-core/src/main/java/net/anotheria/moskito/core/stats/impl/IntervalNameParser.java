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

import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.UnknownIntervalLengthException;

import java.util.HashMap;
import java.util.Map;


/**
 * This utility class provides functionality to parse Interval names.
 * @author miros, lrosenberg
 */
class IntervalNameParser {
	/**
	 * Internally used structure for factor recalculation
	 * @author lrosenberg
	 *
	 */
	private enum TimeUnit{
		/**
		 * Seconds.
		 */
		SECOND(1, 's'),
		/**
		 * Minutes.
		 */
		MINUTE(60, 'm'),
		/**
		 * Hours.
		 */
		HOUR(MINUTE.getFactor()*60, 'h'),
		/**
		 * Days.
		 */
		DAY(HOUR.getFactor()*24, 'd');
		/**
		 * Recalculation factor to seconds.
		 */
		private int factor;
		/**
		 * Char caption like s,m,h,d.
		 */
		private final char caption;
		
		/**
		 * Creates a new timeunit.
		 * @param aFactor
		 * @param aCaption
		 */
		private TimeUnit(int aFactor, char aCaption){
			factor = aFactor;
			caption = aCaption;
		}
		
		int getFactor(){
			return factor;
		}
		
		char getCaption(){
			return caption;
		}
	}
	/**
	 * Internal map for TimeUnit lookups.
	 */
	private static Map<Character, TimeUnit> lookupMap;
	static{
		lookupMap = new HashMap<Character, TimeUnit>();
		for (TimeUnit u : TimeUnit.values()){
			lookupMap.put(u.getCaption(), u);
		}
	}
	
	/**
	 * This method parses the given Interval name and returns the length of such an Interval in
	 * seconds.
	 * 
	 * @param aName the name of the Interval
	 * @return the appropriate length
	 * @throws UnknownIntervalLengthException if the name could not be parsed
	 */
	static final int guessLengthFromName(String aName) {
		
		if (aName.startsWith(DefaultIntervals.PREFIX_SNAPSHOT))
			return -1;
		
		int unitFactor;
		int value;
		
		try {
			value = Integer.parseInt(aName.substring(0, aName.length() - 1));
		} catch (NumberFormatException e) {
			throw new UnknownIntervalLengthException("Unsupported Interval name format: " + aName);
		}

		char lastChar = aName.charAt(aName.length() - 1);
		TimeUnit unit = lookupMap.get(Character.toLowerCase(lastChar));

		if (unit==null)
			throw new UnknownIntervalLengthException(aName + ", " + lastChar + " is not a supported unit.");
		
		unitFactor = unit.getFactor();

		if (value == 0)
			throw new UnknownIntervalLengthException(aName	+ ", zero duration is not allowed.");

		return value * unitFactor;
	}

}
