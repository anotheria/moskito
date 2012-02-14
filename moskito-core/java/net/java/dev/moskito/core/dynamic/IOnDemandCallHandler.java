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
package net.java.dev.moskito.core.dynamic;

import java.lang.reflect.Method;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * A call handler is an object which is used by the dynamic proxy to handle the call. A typical call handle will produce some stats and forward the call to the 
 * target object.
 * @author lrosenberg
 *
 */
public interface IOnDemandCallHandler {
	/**
	 * Called by the proxy on each call to target.
	 * @param target the target object.
	 * @param args call arguments.
	 * @param method called method.
	 * @param targetClass class of the target.
	 * @param declaredExceptions expected exceptions.
	 * @param defaultStats default stats for all methods.
	 * @param methodStats stats for this method.
	 * @param producer the producer.
	 * @return
	 * @throws Throwable
	 */
	Object invoke(Object target, Object[] args, Method method, Class<?> targetClass, 
			Class<?>[] declaredExceptions, IStats defaultStats, IStats methodStats, IStatsProducer producer) throws Throwable;
}
