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
package net.anotheria.moskito.webui.shared.api;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.io.Serializable;

/**
 * This bean represents an interval.
 * @author lrosenberg.
 *
 */
public class IntervalInfoAO implements IComparable, Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the interval.
	 */
	private String name;
	/**
	 * Last update timestamp of the interval.
	 */
	private String updateTimestamp;
	/**
	 * Age of the interval.
	 */
	private String age;

	/**
	 * Length of the interval.
	 */
	private int length;
	
	public IntervalInfoAO(){
		
	}
	
	public IntervalInfoAO(String aName, String anUpdateTimestamp, int aLength){
		name = aName;
		updateTimestamp = anUpdateTimestamp;
		length = aLength;
	}
	
	public IntervalInfoAO(String aName, String anUpdateTimestamp, String anAge, int aLength){
		name = aName;
		updateTimestamp = anUpdateTimestamp;
		age = anAge;
		length = aLength;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(String updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}


	@Override public int compareTo(IComparable anotherComparable, int aMethod){
		return BasicComparable.compareInt(length, ((IntervalInfoAO)anotherComparable).length);
	}
	
	@Override public String toString(){
        return "Interval: " + name +
                ", age: " + age +
                ", ts: " + updateTimestamp;
	}
}
