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
package net.anotheria.moskito.core.inspection;

import java.io.Serializable;
import java.util.Arrays;

/**
 * An object that stores the info about the creation of another object. As for now the timestamp of the creation is stored along with the stacktrace.
 * @author lrosenberg
 */
public class CreationInfo implements Serializable{
	/**
	 * Timestamp of the creation.
	 */
	private long timestamp;
	/**
	 * Stack trace of the creation.
	 */
	private StackTraceElement[] stackTrace;
	
	public CreationInfo(StackTraceElement[] aStackTrace){
		timestamp = System.currentTimeMillis();
		stackTrace = Arrays.copyOf(aStackTrace, aStackTrace.length);
	}

	public StackTraceElement[] getStackTrace() {
		return Arrays.copyOf(stackTrace, stackTrace.length);
	}

	public void setStackTrace(StackTraceElement[] stackTrace) {
		this.stackTrace = Arrays.copyOf(stackTrace, stackTrace.length);
	}

	public long getTimestamp() {
		return timestamp;
	}
}
