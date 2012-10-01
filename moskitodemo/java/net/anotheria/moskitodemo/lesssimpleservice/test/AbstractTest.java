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
package net.anotheria.moskitodemo.lesssimpleservice.test;

import net.anotheria.moskitodemo.lesssimpleservice.AnotherTypedException;
import net.anotheria.moskitodemo.lesssimpleservice.ILesserSimpleService;
import net.anotheria.moskitodemo.lesssimpleservice.ILesserSimpleServiceFactory;
import net.anotheria.moskitodemo.lesssimpleservice.LessSimpleServiceException;
import net.anotheria.moskitodemo.lesssimpleservice.LesserSimpleServiceException;

public class AbstractTest {
	public static void execute(ILesserSimpleServiceFactory factory){
		ILesserSimpleService service = factory.createService();
		System.out.println("Created service instance: "+service);
		long startTime = System.currentTimeMillis();
		long count = 0;
		for (int i=0; i<10000; i++){
			count++;
			try {
				service.methodA();
			} catch (LesserSimpleServiceException e) {
				e.printStackTrace();
			}
			try {
				service.methodB();
			} catch (AnotherTypedException e) {
				e.printStackTrace();
			}
			try {
				service.lessSimpleServiceMethod();
			} catch (LessSimpleServiceException e) {
				e.printStackTrace();
			}
		}
		long executionTime = System.currentTimeMillis() - startTime;
		System.out.println("test finished in "+executionTime+" ms ("+count+")");
	}
}
