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

import java.util.HashMap;
import java.util.Map;

import net.java.dev.moskito.core.stats.UnknownIntervalLengthException;


/**
 * This utility class provides functionality to parse Interval names.
 * 
 * @author miros
 */
class IntervalNameParser {
	
	private enum TimeUnit{
		SECOND(1, 's'),
		MINUTE(60, 'm'),
		HOUR(MINUTE.getFactor()*60, 'h'),
		DAY(HOUR.getFactor()*24, 'd');
		
		private int factor;
		private final char caption;
		
		private TimeUnit(int aFactor, char aCaption){
			factor = aFactor;
			caption = aCaption;
		}
		
		int getFactor(){ return factor; }
		
		char getCaption(){ return caption; }
	}
	
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
