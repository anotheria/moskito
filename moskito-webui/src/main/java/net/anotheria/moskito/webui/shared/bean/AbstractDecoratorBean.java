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

import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.StringUtils;
import net.anotheria.util.sorter.IComparable;

import java.util.List;

/**
 * Basic bean for decorator bean.
 * @author lrosenberg.
 */
public abstract class AbstractDecoratorBean implements IComparable{

	/**
	 * Name of the decorator.
	 */
	private String name;
	/**
	 * Captions of the fields.
	 */
	private List<StatCaptionBean> captions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<StatCaptionBean> getCaptions() {
		return captions;
	}
	
	public void setCaptions(List<StatCaptionBean> someCaptions){
		captions = someCaptions;
	}

	@Override public int compareTo(IComparable anotherComparable, int method){
		//there is only one default method.
		return BasicComparable.compareString(getName(), ((ProducerDecoratorBean)anotherComparable).getName());
	}
	
	public String getSortByParameterName(){
		return "p"+StringUtils.capitalize(name)+"SortBy";
	}

	public String getSortOrderParameterName(){
		return "p"+StringUtils.capitalize(name)+"SortOrder";
	}

	public String getDecoratorNameForCss(){
		return StringUtils.removeChars(getName(), BaseMoskitoUIAction.WHITESPACES);
	}
}
