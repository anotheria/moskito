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
package net.anotheria.moskitodemo.simpleservice;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class SimpleServiceFactoryWithProxies {
      public static ISimpleService createSimpleService(){


    	  final ISimpleService impl = new SimpleServiceImpl();

      InvocationHandler handler = new InvocationHandler() {
    	  	public Object invoke(Object aProxy, Method aMethod, Object[] args) throws Throwable {
                if (aMethod.getDeclaringClass().equals(Object.class) || 
                		!aMethod.getDeclaringClass().equals(ISimpleService.class)) {
                    // any method in the above interfaces is implemented inthis class, just look it up and invoke it
                    Method superMethod =getClass().getMethod(aMethod.getName(), (Class[]) aMethod.getParameterTypes());
                    return superMethod.invoke(this, args);
                } else {
                    try {

                        // this seems to be a business method
                        return aMethod.invoke(impl, args);

                    } catch (Exception e) {
                        throw e;
                    } finally {

                    }
                }
            }

        };
            return (ISimpleService)Proxy.newProxyInstance(SimpleServiceFactory.class.getClassLoader(), new Class[] { ISimpleService.class }, handler);
      }
}

