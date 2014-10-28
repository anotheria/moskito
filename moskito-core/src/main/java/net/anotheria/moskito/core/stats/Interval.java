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
 * This interface declares an inveral with a name and an unique id.
 * Additionally it provides operations to register/deregister for interval callbacks. 
 * 
 * @author miros
 */
public interface Interval {

	/**
	 * This method adds a listener as primary listener.
	 * 
	 * @param aListener the new listener
	 */
	void addPrimaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method removes the given listener from the primary listeners.
	 * 
	 * @param aListener the listener to remove.
	 */
	void removePrimaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method adds a listener as primary listener.
	 * 
	 * @param aListener the new listener
	 */
	void addSecondaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method removes the given listener from the primary listeners.
	 * 
	 * @param aListener the listener to remove.
	 */
	void removeSecondaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method returns the interval id.
	 * 
	 * @return the interval id
	 */
	int getId();

	/**
	 * This method returns the interval name.
	 * 
	 * @return the interval name
	 */
	String getName();

	/**
	 * This method returns the interval length in seconds.
	 * 
	 * @return the interval length in seconds
	 */
	int getLength();

	/**
	 * Returns the timestamp (ms) of the last update to this interval.
	 * @return
	 */
	long getLastUpdateTimestamp();

	/**
	 * Returns number of registered primary listeners. For debug/information purposes.
	 * @return
	 */
	long getPrimaryListenerCount();

	/**
	 * Returns amount of registered secondary listeners. For debug/information purposes.
	 * @return
	 */
	long getSecondaryListenerCount();


}