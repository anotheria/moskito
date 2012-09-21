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
package net.java.dev.moskito.webui.decorators;

import net.anotheria.util.sorter.IComparable;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.StatCaptionBean;
import net.java.dev.moskito.webui.bean.StatValueBean;

import java.util.List;

/**
 * A decorator prepares data from a set of stat producers for presentation.
 * @author lrosenberg
 *
 */
public interface IDecorator extends IComparable{
	/**
	 * Returns the name of the decorator.
	 * @return
	 */
	String getName();
	/**
	 * Returns the captions for the stat table.
	 * @return
	 */
	List<StatCaptionBean> getCaptions();
	/**
	 * Returns transformed stats for incoming stats object.
	 * @param stats stats to decorate.
	 * @param interval the selected interval.
	 * @param unit the selected time unit.
	 * @return
	 */
	List<StatValueBean> getValues(IStats stats, String interval, TimeUnit unit);
	/**
	 * Returns an explanation for a caption. This is used for the explanation help page.
	 * @param caption
	 * @return
	 */
	String getExplanation(String caption);
}
