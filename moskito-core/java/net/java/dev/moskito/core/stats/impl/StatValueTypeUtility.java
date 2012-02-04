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



/**
 * This utility class provides methods to gather the StatValue type from a given Object's class and
 * to create ValueHolderFactories from an internal type representation.
 * 
 * @author lrosenberg
 */
final class StatValueTypeUtility {

	/**
	 * This method creates the responsible ValueHolderFactory from the given internal type representation.
	 * 
	 * @param aType the type that should be supported by the new facotry
	 * @return the new factory instance
	 * @throws RuntimeException if the type is unknown or not supported
	 */
	protected static IValueHolderFactory createValueHolderFactory(StatValueTypes aType) {
		switch (aType) {
		case LONG:
			return new LongValueHolderFactory();
		case INT:
			return new IntValueHolderFactory();
		case STRING:
			return new StringValueHolderFactory();
		default:
			throw new AssertionError("Unsupported type: " + aType);
		}
	}
	
	/**
	 * 
	 * 
	 * @param aObject
	 * @return
	 */
	protected static StatValueTypes object2type(Object anObject) {
		if (anObject instanceof Long)
			return StatValueTypes.LONG;
		if (anObject instanceof Integer)
			return StatValueTypes.INT;
		if (anObject instanceof String)
			return StatValueTypes.STRING;
		throw new AssertionError("Invalid class for object2type mapping: "	+ anObject.getClass());
	}

	private StatValueTypeUtility(){
		//prevent instantiation.
	}
}
