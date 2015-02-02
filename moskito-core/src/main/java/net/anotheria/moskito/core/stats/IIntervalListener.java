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
package net.anotheria.moskito.core.stats;

/**
 * This interface declares a listener that can be registered at an Interval to be 
 * notified when the interval-specific period of time was elapsed.
 * 
 * @author lrosenberg
 */
public interface IIntervalListener {
	
	/**
	 * This method will be called if the time of the given Interval was elapsed.
	 * 
	 * @param aCaller The Interval that calls this method
	 */
	void intervalUpdated(Interval aCaller);
}
