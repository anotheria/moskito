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
package net.java.dev.moskitodemo.simpleservice.test;

import net.java.dev.moskitodemo.simpleservice.ISimpleService;

/**
 * This test case calls ISimpleService.produceExceptionIfParameterIsEven() with parameter 1-10. 
 * This should lead to 5 errors in the stats.
 * @author another
 *
 */
public class TestExceptionProduction extends AbstractTest{
	public static void main(String a[]) throws Exception{
		new TestExceptionProduction().startTest();
	}
	
	public void test(ISimpleService service, boolean verbose) throws Exception{
		int exceptionCount = 0;
		for (int i=1; i<=10; i++){
			try{
				service.produceExceptionIfParameterIsEven(i);
			}catch(Exception e){
				exceptionCount++;
			}
		}
		if (verbose){
			System.out.println("Finished test, counted "+exceptionCount+" exceptions, expected 5, "+(exceptionCount==5?"ok":"failure"));
			System.out.println("You should see ERR value set to "+exceptionCount+" in the service and produceException stats.");
			service.printStats();
		}
	}
}
