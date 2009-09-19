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
package net.java.dev.moskitodemo.usecases.qe.business.test;

import net.java.dev.moskitodemo.usecases.qe.business.IQECalculator;
import net.java.dev.moskitodemo.usecases.qe.business.QECalculatorException;
import net.java.dev.moskitodemo.usecases.qe.business.QECalculatorFactory;

public class Test {
	
	private static IQECalculator calc;
	
	public static void main(String a[]){
		calc = QECalculatorFactory.createQECalculator();
		
		solve(1,-3,-4);
		solve(1,1,1);
		solve(-1,-1,-1);
		solve(1,-1,-1);
		solve(1,2,-3);
		solve(4,3,-1);
		solve(1,0,0);

	}
	
	private static void solve(int a, int b, int c){
		try{
			System.out.println("Trying to solve "+a+"x²"+getSign(b)+b+"x"+getSign(c)+c);
			int D = calc.calculateDeterminant(a,b,c);
			System.out.println("\tD="+D);
			if (D==0)
				System.out.println("\tSinge solution: "+calc.solveForZeroDeterminant(a,b,c));
			else
				System.out.println("\tDouble solution: "+calc.solveForPositiveDeterminat(a,b,c));
		}catch(QECalculatorException e){
			System.out.println("\tNo solution: "+e.getMessage());
		}
	}
	
	private static String getSign(int value){
		return value < 0 ? 
				"" : "+";
	}
}
