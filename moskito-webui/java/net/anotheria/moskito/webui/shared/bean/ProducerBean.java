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
package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.moskito.webui.producers.api.StatValueAO;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import java.util.List;

/**
 * Represents the data for a single producer view.
 * @author lrosenberg.
 */
public class ProducerBean implements IComparable{
	/**
	 * Id of the producer.
	 */
	private String id;
	/**
	 * Class name of the producer without package name.
	 */
	private String className;
	/**
	 * Class name of the producer with package name.
	 */
	private String fullClassName;
	/**
	 * Category of the producer.
	 */
	private String category;
	/**
	 * Subsystem of the producer.
	 */
	private String subsystem;
	/**
	 * Wrapper for the value list.
	 */
	private ValueListWrapper wrapper;
	
	public ProducerBean(){
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getClassName() { 
		return className;
	}
	public void setClassName(String className) {
		setFullClassName(className);
		int ind = className.lastIndexOf('.')+1;		
		this.className = className.substring(ind);		
	}
	
	public String getFullClassName() {
		return fullClassName;
	}
	
	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	
	public void setValues(List<StatValueAO> someValues){
		wrapper = new ValueListWrapper(someValues);
	}
	
	public List<StatValueAO> getValues(){
		return wrapper.getValues();
	}

	@Override public int compareTo(IComparable anotherComparable, int method) {
		ProducerBean anotherBean = (ProducerBean)anotherComparable;
		if (method<ProducerBeanSortType.DYN_SORT_TYPE_LIMIT)
			return wrapper.compareTo(anotherBean.wrapper, method);
		switch(method){
			case ProducerBeanSortType.SORT_BY_ID:
				return BasicComparable.compareString(id, anotherBean.id);
			case ProducerBeanSortType.SORT_BY_CATEGORY:
				return BasicComparable.compareString(category, anotherBean.category);
			case ProducerBeanSortType.SORT_BY_SUBSYSTEM:
				return BasicComparable.compareString(subsystem, anotherBean.subsystem);
			case ProducerBeanSortType.SORT_BY_CLASS_NAME:
				return BasicComparable.compareString(className, anotherBean.className);
			default:
				throw new RuntimeException("Unsupported sort method: "+method);
		}
	}

	
}
