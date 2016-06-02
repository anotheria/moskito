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
package net.anotheria.moskito.core.decorators.value;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

/**
 * StatValueAO for string values.
 * @author lrosenberg.
 *
 */
public class StringValueAO extends StatValueAO {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Value object.
	 */
	private String value;
	
	/**
	 * Creates a new StringValue bean with given name and value.
	 * @param aName
	 * @param aValue
	 */
	public StringValueAO(String aName, String aValue){
		super(aName);
		value = aValue;
	}
	
	@Override public String getType(){
		return "string";
	}
	
	@Override public String getValue(){
		return value;
	}

	@Override public int compareTo(IComparable anotherComparable, int ignored) {
		return BasicComparable.compareString(value, ((StringValueAO)anotherComparable).value);
	}

	@Override
	public String getRawValue() {
		return "NaN";
	}
	
	
	
}
