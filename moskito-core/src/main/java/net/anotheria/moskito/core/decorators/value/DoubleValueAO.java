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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Stat value bean for double values.
 * @author lrosenberg.
 *
 */
public class DoubleValueAO extends StatValueAO {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Internal storage.
	 */
	private double doubleValue;
	/**
	 * Cached string representation.
	 */
	private String doubleAsString;

	private static DecimalFormat decimalFormat = createDecimalFormat();

	private static DecimalFormat createDecimalFormat() {
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator('.');
		decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
		return decimalFormat;
	}

	/**
	 * Creates a new DoubleValueAO.
	 * @param name identifies the value
	 * @param aValue the value itself
	 */
	public DoubleValueAO(String name, double aValue){
		super(name);
		if (!Double.isInfinite(aValue))
			doubleValue = ((double)Math.round(aValue * 1000)) / 1000;
		else
			doubleValue = aValue;

		doubleAsString = decimalFormat.format(doubleValue);
	}

	@Override public String getValue(){
		return doubleAsString;
	}
	
	@Override public String getType(){
		return "double";
	}
	 
	@Override public int compareTo(IComparable anotherComparable, int ignored) {
		return BasicComparable.compareDouble(doubleValue, ((DoubleValueAO)anotherComparable).doubleValue);
	}

	@Override
	public String getRawValue() {
		return String.valueOf(doubleValue);
	}
}