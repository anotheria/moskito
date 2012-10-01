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
package net.anotheria.moskitodemo.usecases.qe.business;

import net.anotheria.moskito.core.dynamic.MoskitoInvokationProxy;
import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;

import java.util.ArrayList;
import java.util.List;

public class QESolverImpl implements IQESolver{
	private IQECalculator calc;
	
	public QESolverImpl(){
		IQECalculator _calc = QECalculatorFactory.createQECalculator();
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				_calc,
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				IQECalculator.class);
		calc = (IQECalculator)proxy.createProxy();
	}

	public List<Double> solveQuadrationEquation(int a, int b, int c) {
		
		List<Double> ret = new ArrayList<Double>();
		try{
			int d = calc.calculateDeterminant(a,b,c);
			if (d <0 )
				return ret;
			if (d==0)
				ret.add((double)calc.solveForZeroDeterminant(a,b,c));
			if (d>0){
				DoubleEquationResult res = calc.solveForPositiveDeterminat(a,b,c);
				ret.add(res.getX1());
				ret.add(res.getX2());
			}
		}catch(QECalculatorException e){}
		return ret;
	}
}
