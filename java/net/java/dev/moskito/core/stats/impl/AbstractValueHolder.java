/*
 * $Id$
 * $Author$
 *
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.java.net.
 * 
 * Copyright (c) 2006 by MoSKito Project
 *
 * All MoSKito files are under MIT License: 
 * http://www.opensource.org/licenses/mit-license.php
 */
package net.java.dev.moskito.core.stats.impl;

import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.ValueHolder;

/**
 * This abstract class implements the super class of any type-specific holder 
 * implementation and declares the interface to support some basic mathematical operations 
 * needed for statistical aggregations.
 *
 * @author lrosenberg
 */
abstract class AbstractValueHolder implements IIntervalListener, ValueHolder {
	
	/**
	 * This is the Interval this ValueHolder is responsible for.  
	 */
	private Interval interval;
	
	/**
	 * This is the constructor.
	 * 
	 * @param aInterval the Interval
	 */
	protected AbstractValueHolder(Interval aInterval){
		interval = aInterval;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return interval.getName();
	}
	

}
