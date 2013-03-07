/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.м
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

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;

import java.util.ArrayList;
import java.util.List;

/**
 * This bean contains the data for all stats which are decorated by same decorator.
 */
public class StatDecoratorBean extends AbstractDecoratorBean {
	/**
	 * List of stats bean.
	 */
	private List<StatBean> stats; 

	/**
	 * Creates a new StatDecoratorBean.
	 */
	public StatDecoratorBean(){
		stats = new ArrayList<StatBean>();
	}


	public List<StatBean> getStats() {
		return stats;
	}
	
	
	public void addStatsBean(StatBean stat){
		stats.add(stat);
	}
	
	public void setStats(List<StatBean> someBeans){
		stats = someBeans;
	}

	@Override public String toString(){
		return super.toString()+" "+stats;
	}

	/**
	 * Returns the name of the sort type for sort links generation.
	 * @return
	 */
	public String getSortTypeName(){
		return BaseMoskitoUIAction.BEAN_SORT_TYPE_SINGLE_PRODUCER_PREFIX + getName();
	}	

}
