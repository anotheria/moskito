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
package net.java.dev.moskito.core.stats;

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
	public void addPrimaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method removes the given listener from the primary listeners.
	 * 
	 * @param aListener the listener to remove.
	 */
	public void removePrimaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method adds a listener as primary listener.
	 * 
	 * @param aListener the new listener
	 */
	public void addSecondaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method removes the given listener from the primary listeners.
	 * 
	 * @param aListener the listener to remove.
	 */
	public void removeSecondaryIntervalListener(IIntervalListener aListener);

	/**
	 * This method returns the interval id.
	 * 
	 * @return the interval id
	 */
	public int getId();

	/**
	 * This method returns the interval name.
	 * 
	 * @return the interval name
	 */
	public String getName();

	/**
	 * This method returns the interval length in seconds.
	 * 
	 * @return the interval length in seconds
	 */
	public int getLength();

}