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
package net.java.dev.moskito.core.predefined;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.java.dev.moskito.core.dynamic.IOnDemandCallHandler;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.core.usecase.running.RunningUseCase;
import net.java.dev.moskito.core.usecase.running.RunningUseCaseContainer;

/**
 * Implementation of a call handler that uses service stats. Useful for service like objects.
 * @author lrosenberg
 */
public class ServiceStatsCallHandler implements IOnDemandCallHandler{

	@Override public Object invoke(Object target, Object[] args, Method method, Class<?> targetClass, Class<?>[] declaredExceptions, IStats aDefaultStats, IStats aMethodStats, String producerId) throws Throwable {
		ServiceStats defaultStats = (ServiceStats)aDefaultStats;
		ServiceStats methodStats = (ServiceStats)aMethodStats;
		
		defaultStats.addRequest();
		methodStats.addRequest();
		RunningUseCase aRunningUseCase = RunningUseCaseContainer.getCurrentRunningUseCase();
		PathElement currentElement = null;
		ExistingRunningUseCase runningUseCase = aRunningUseCase.useCaseRunning() ? 
				(ExistingRunningUseCase)aRunningUseCase : null; 
		if (runningUseCase !=null){
			StringBuilder call = new StringBuilder(producerId).append('.').append(method.getName()).append("(");
			if (args!=null && args.length>0){
				for (int i=0; i<args.length; i++){
					call.append(args[i]);
					if (i<args.length-1)
						call.append(", ");
				}
			}
			call.append(")");
			currentElement = runningUseCase.startPathElement(call.toString());
		}
		long startTime = System.nanoTime();
		Object ret = null;
		try{
			ret = method.invoke(target, args);
			return ret;
		}catch(InvocationTargetException e){
			defaultStats.notifyError();
			methodStats.notifyError();
			//System.out.println("exception of class: "+e.getCause()+" is thrown");
			if (currentElement!=null)
				currentElement.setAborted();
			throw e.getCause();
		}catch(Throwable t){
			defaultStats.notifyError();
			methodStats.notifyError();
			if (currentElement!=null)
				currentElement.setAborted();
			throw t;
		}finally{
			long exTime = System.nanoTime() - startTime;
			defaultStats.addExecutionTime(exTime);
			methodStats.addExecutionTime(exTime);
			defaultStats.notifyRequestFinished();
			methodStats.notifyRequestFinished();
			if (currentElement!=null){
				currentElement.setDuration(System.nanoTime()-startTime);
				try{
					currentElement.appendToCall(" = "+ret);
				}catch(Throwable t){
					currentElement.appendToCall(" = ERR: "+t.getMessage()+" ("+t.getClass()+")");
				}
			}
			if (runningUseCase !=null)
				runningUseCase.endPathElement();
		}
	}
} 
