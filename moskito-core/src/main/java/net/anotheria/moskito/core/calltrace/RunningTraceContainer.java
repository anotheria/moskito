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
package net.anotheria.moskito.core.calltrace;


import net.anotheria.moskito.core.context.MoSKitoContext;

import java.util.Map;

/**
 * This is a container for thread local reference to a running thread container.
 * @author lrosenberg
 *
 */
public class RunningTraceContainer {
	
	/**
	 * Currently running use case.
	 */
	private static ThreadLocal<TracedCall> currentlyTracedCall = new ThreadLocal<TracedCall>(){
		protected synchronized TracedCall initialValue(){
			return NoTracedCall.INSTANCE;
		}
	};
	
	public static TracedCall getCurrentlyTracedCall(){
		return currentlyTracedCall.get();
	}
	
	public static void startTracedCall(String name){
		currentlyTracedCall.set(new CurrentlyTracedCall(name));
	}
	
	public static void setCurrentlyTracedCall(TracedCall aTracedCall){
		currentlyTracedCall.set(aTracedCall);
	}
	
	public static TracedCall endTrace(){
		TracedCall last = getCurrentlyTracedCall();
		setCurrentlyTracedCall(NoTracedCall.INSTANCE);

		//check tags and set if applicable.
		Map<String, String> tags = MoSKitoContext.getTags();
		if (tags!=null && tags.size()>0){
			if (last instanceof CurrentlyTracedCall){
				((CurrentlyTracedCall)last).setTags(tags);
			}
			
		}

		return last;
	}
	
	/**
	 * This is a special method for web applications to cleanup the ThreadLocals.
	 */
	public static void cleanup(){
		currentlyTracedCall.remove();
	}
	
	/**
	 * Returns true if there is currently a use-case recorded.
	 * @return
	 */
	public static boolean isTraceRunning(){
		return currentlyTracedCall.get().callTraced();
	}
}
