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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.timing.IUpdateable;

/**
 * This class implements an Inveral with a name and an unique id that is furthermore a 
 * registry for primary and secondary update listeners. 
 * On calling the update() method it will notify the primary listener first 
 * and afterwards the secondary listeners.    
 * 
 * @author dvayanu
 */
class IntervalImpl implements IUpdateable, Interval {
	
	/**
	 * This is the human readable name of this Interval. 
	 */
	private String name;
	
	/**
	 * This is the unique interval id.
	 */
	private int id;
	
	/**
	 * This is the length of the interval in seconds.
	 */
	private int length;
	
	/**
	 * This List holds all primary listeners.
	 */
	private List<IIntervalListener> primaryIntervalListeners;

	/**
	 * This List holds all secondary listeners.
	 */
	private List<IIntervalListener> secondaryIntervalListeners;
	
	/**
	 * This is the constructor.
	 * 
	 * @param anId the id
	 * @param aName the name 
	 * @param aLength the interval length in seconds 
	 */
	public IntervalImpl(int anId, String aName, int aLength){
		id = anId;
		name = aName;
		length = aLength;
		primaryIntervalListeners = new CopyOnWriteArrayList<IIntervalListener>();
		secondaryIntervalListeners = new CopyOnWriteArrayList<IIntervalListener>();
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.Interval#addPrimaryIntervalListener(net.java.dev.moskito.core.stats.IIntervalListener)
	 */
	public void addPrimaryIntervalListener(IIntervalListener aListener){
		primaryIntervalListeners.add(aListener);	
	}

	/**
	 * @see net.java.dev.moskito.core.stats.Interval#removePrimaryIntervalListener(net.java.dev.moskito.core.stats.IIntervalListener)
	 */
	public void removePrimaryIntervalListener(IIntervalListener aListener){
		primaryIntervalListeners.remove(aListener);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.Interval#addSecondaryIntervalListener(net.java.dev.moskito.core.stats.IIntervalListener)
	 */
	public void addSecondaryIntervalListener(IIntervalListener aListener){
		secondaryIntervalListeners.add(aListener);	
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.Interval#removeSecondaryIntervalListener(net.java.dev.moskito.core.stats.IIntervalListener)
	 */
	public void removeSecondaryIntervalListener(IIntervalListener aListener){
		secondaryIntervalListeners.remove(aListener);
	}

	/**
	 * @see net.java.dev.moskito.core.stats.Interval#getId()
	 */
	public int getId(){
		return id;
	}

	/**
	 * @see net.java.dev.moskito.core.stats.Interval#getName()
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @see net.java.dev.moskito.core.stats.Interval#getLength()
	 */
	public int getLength(){
		return length;
	}
	
	/**
	 * @see net.java.dev.moskito.core.timing.IUpdateable#update()
	 */
	public void update() {
		notifyListeners(primaryIntervalListeners);
		notifyListeners(secondaryIntervalListeners);
	}

	/**
	 * This method notifies all listeners in the given List.
	 * 
	 * @param aListeners the List of listeners
	 */
	private void notifyListeners(List<IIntervalListener> aListeners){
		for (IIntervalListener listener : aListeners) {
			listener.intervalUpdated(this);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "["+id+"] "+name+" length: "+length+", "+primaryIntervalListeners.size()+" / "+secondaryIntervalListeners.size();
	}

}

