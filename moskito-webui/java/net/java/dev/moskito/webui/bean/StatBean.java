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
package net.java.dev.moskito.webui.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.util.List;

public class StatBean implements IComparable{
	private String name;
	
	private ValueListWrapper wrapper;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StatValueBean> getValues() {
		return wrapper.getValues();
	}
	public void setValues(List<StatValueBean> values) {
		wrapper = new ValueListWrapper(values);
	}
	
	
	public int compareTo(IComparable anotherComparable, int method) {
		StatBean anotherBean = (StatBean)anotherComparable;
		if (method<StatBeanSortType.SORT_TYPE_LIMIT)
			return wrapper.compareTo(anotherBean.wrapper, method);
		switch(method){
			case StatBeanSortType.SORT_BY_NAME:
				return BasicComparable.compareString(name, anotherBean.name);
			default:
				throw new RuntimeException("Unsupported sort method: "+method);
		}
	}

}
