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

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.StatValueBean;
import net.java.dev.moskito.webui.bean.StringValueBean;

/**
 * A NOOP decorator. Used as a substitute for missing (or yet unimplemented) decorator.
 * @author lrosenberg
 *
 */
public class DefaultDecorator extends AbstractDecorator{
	
	/**
	 * Constructor.
	 */
	public DefaultDecorator(){
		super("missing decorator", new String[]{"UNSET"}, new String[]{"UNSET"}, new String[]{"UNSET"});
	}

	@Override public List<StatValueBean> getValues(IStats stats, String interval, TimeUnit unit) {
		List<StatValueBean> beans = new ArrayList<StatValueBean>(1);
		beans.add(new StringValueBean("none", "no decorator for "+stats.getClass()));
		return beans;
	}
	 
}
