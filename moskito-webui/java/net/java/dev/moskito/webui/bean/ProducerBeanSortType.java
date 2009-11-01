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

import net.anotheria.util.sorter.SortType;

/**
 * Sort type for producer beans for in-frontend resorting.
 * @author lrosenberg
 *
 */
public class ProducerBeanSortType extends SortType{
	
	/**
	 * Constant for sorting by producer id.
	 */
	public static final int SORT_BY_ID = 1000;
	/**
	 * Constant for sorting by producer category.
	 */
	public static final int SORT_BY_CATEGORY = 1001;
	/**
	 * Constant for sorting by producer subsystem
	 */
	public static final int SORT_BY_SUBSYSTEM = 1002;
	/**
	 * Constant for sorting by producer class name.
	 */
	
	public static final int SORT_BY_CLASS_NAME = 1003;
	/**
	 * Constant for sorting by default - sort by id.
	 */
	public static final int SORT_BY_DEFAULT = SORT_BY_ID;
	/**
	 * Constant for separation between dynamical parts of the sort type (defined by decorator) and generic (common to all producers).
	 */
	public static final int DYN_SORT_TYPE_LIMIT = 1000;
	/**
	 * Creates a new producer sort type.
	 * @param aSortBy
	 * @param aSortOrder
	 */
	public ProducerBeanSortType(int aSortBy, boolean aSortOrder){
		super(aSortBy, aSortOrder);
	}
	
	/**
	 * Creates a new producer sort type.
	 */
	public ProducerBeanSortType(){
		this(SORT_BY_DEFAULT, ASC);
	}
}
