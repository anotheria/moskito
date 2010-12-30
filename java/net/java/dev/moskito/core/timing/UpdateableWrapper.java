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
package net.java.dev.moskito.core.timing;

import java.util.TimerTask;

import org.apache.log4j.Logger;


/**
 * This class implements an adapter to handle IUpdatable instances as TimerTask.
 *
 * @author lrosenberg
 */
final class UpdateableWrapper extends TimerTask {

	private static Logger log = Logger.getLogger(UpdateableWrapper.class);

	/**
	 * This is the delegate that will be called.
	 */
	private IUpdateable delegate;
	
	/**
	 * The constructor.
	 * 
	 * @param aDelegate the delegate to use 
	 */
	UpdateableWrapper(IUpdateable aDelegate){
		delegate = aDelegate;
	}
	

	@Override public void run() {
		try{
			delegate.update();
		}catch(Exception e){
			log.warn("Delegate update failed on "+delegate, e);
		}
	}

}
