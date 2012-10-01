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
package net.java.dev.moskitodemo.usecases.qe.presentation;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.bean.annotations.Form;
import net.anotheria.moskito.core.dynamic.MoskitoInvokationProxy;
import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.java.dev.moskitodemo.usecases.qe.business.IQESolver;
import net.java.dev.moskitodemo.usecases.qe.business.QESolverImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class SolveQEAction extends AbstractAction{
	
	private IQESolver solver;
	
	public SolveQEAction(){
		solver = (IQESolver) (new MoskitoInvokationProxy(
				new QESolverImpl(),
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				IQESolver.class)).createProxy();
		
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, @Form(QEParameterForm.class)FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		QEParameterForm form = (QEParameterForm)af;
		System.out.println("Called QESolver with form: "+form);
		if (form.isEmpty()){
			System.out.println("Form is empty!");
			return mapping.success();
		}
	
		int a=0,b=0,c=0;
		List<String> errorMessages = new ArrayList<String>();
		try{
			a = Integer.parseInt(form.getA());
		}catch(NumberFormatException e){
			errorMessages.add("Parameter a is not a number: "+form.getA());
		}
		try{
			b = Integer.parseInt(form.getB());
		}catch(NumberFormatException e){
			errorMessages.add("Parameter b is not a number: "+form.getB());
		}
		try{
			c = Integer.parseInt(form.getC());
		}catch(NumberFormatException e){
			errorMessages.add("Parameter c is not a number: "+form.getC());
		}
		
		System.out.println("Errors: "+errorMessages);
		if (errorMessages.size()>0){
			req.setAttribute("errors", errorMessages);
			return mapping.success();
		}
		
		
		List<Double> solutions = solver.solveQuadrationEquation(a,b,c);
		if (solutions.size()==0){
			errorMessages.add("No solutions available");
			req.setAttribute("errors", errorMessages);
			return mapping.findForward("success");
		}
			
		System.out.println("Solver: "+solutions);
		req.setAttribute("solutions", solutions);
		
		// TODO Auto-generated method stub
		return mapping.findForward("success");
	}
	
	

}
