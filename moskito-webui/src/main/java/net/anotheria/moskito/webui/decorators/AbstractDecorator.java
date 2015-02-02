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
package net.anotheria.moskito.webui.decorators;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.webui.shared.bean.StatCaptionBean;
import net.anotheria.util.sorter.IComparable;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class which implements basic functionality common to most decorators.
 * @author another
 *
 */
public abstract class AbstractDecorator<S extends IStats> implements IDecorator<S>{
	
	/**
	 * Constant for MegaByte.
	 */
	protected static final long MB = 1024L*1024;

	/**
	 * Storage for captions.
	 */
	private List<StatCaptionBean> captions;
	/**
	 * Decorator name.
	 */
	private String name;
	
	/**
	 * Creates a new abstract decorator.
	 * @param aName name of the decorator.
	 * @param captionStrings array with captions.
	 * @param shortExplanationStrings array with short explanations (mouse over).
	 * @param explanationStrings array with long explanations.
	 */
	protected AbstractDecorator(String aName, String[] captionStrings, String shortExplanationStrings[], String explanationStrings[]){
		name = aName;
		captions = new ArrayList<StatCaptionBean>(captionStrings.length);
		for (int i=0; i<captionStrings.length; i++)
			captions.add(new StatCaptionBean(captionStrings[i], shortExplanationStrings[i], explanationStrings[i]));
	}
	
	@Override public List<StatCaptionBean> getCaptions() {
		return captions;
	}
	
	@Override public String getName(){
		return name;
	}
	
	@Override public int compareTo(IComparable anotherComparable, int method){
		return name.compareToIgnoreCase(((IDecorator)anotherComparable).getName());
	}

	@Override public String getExplanation(String caption){
		for (StatCaptionBean c : captions){
			if (c.getCaption().equals(caption))
				return c.getExplanation();
		}
		return "Sorry, no explanation available";
	}
}
