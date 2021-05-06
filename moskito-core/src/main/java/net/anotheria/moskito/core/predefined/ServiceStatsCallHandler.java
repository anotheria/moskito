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
package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.calltrace.TracingUtil;
import net.anotheria.moskito.core.context.CurrentMeasurement;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.IOnDemandCallHandler;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.tracer.Trace;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.tracer.Tracers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implementation of a call handler that uses service stats. Useful for service like objects.
 * @author lrosenberg
 */
public class ServiceStatsCallHandler implements IOnDemandCallHandler {

	@Override public Object invoke(Object target, Object[] args, Method method, Class<?> targetClass, Class<?>[] declaredExceptions, IStats aDefaultStats, IStats aMethodStats, IStatsProducer producer) throws Throwable {
		ServiceStats defaultStats = (ServiceStats)aDefaultStats;
		ServiceStats methodStats = (ServiceStats)aMethodStats;
		
		defaultStats.addRequest();
		methodStats.addRequest();
		TracedCall aRunningTrace = RunningTraceContainer.getCurrentlyTracedCall();
		TraceStep currentStep = null;
		CurrentlyTracedCall currentTrace = aRunningTrace.callTraced() ?
				(CurrentlyTracedCall)aRunningTrace : null;

		TracerRepository tracerRepository = TracerRepository.getInstance();
		
		String producerId = producer.getProducerId();

		//check if we are the first producer
		CurrentMeasurement cm = MoSKitoContext.get().notifyProducerEntry(producer);


		boolean tracePassingOfThisProducer = tracerRepository.isTracingEnabledForProducer(producerId);
		Trace trace = null;
		boolean journeyStartedByMe = false;

		//we create a trace at the beginning to reuse same id for the journey.
		if (tracePassingOfThisProducer) {
			trace = new Trace();
		}

		if (currentTrace == null && tracePassingOfThisProducer) {
			//ok, we will create a new journey on the fly.
			String journeyCallName = Tracers.getCallName(trace);
			RunningTraceContainer.startTracedCall(journeyCallName);
			journeyStartedByMe = true;

			currentTrace = (CurrentlyTracedCall) RunningTraceContainer.getCurrentlyTracedCall();
		}

		StringBuilder call = null;
		if (currentTrace != null || tracePassingOfThisProducer || cm.isFirst()) {
			call = TracingUtil.buildCall(producerId, method.getName(), args, tracePassingOfThisProducer ? Tracers.getCallName(trace) : null);
		}
		if (currentTrace != null) {
			currentStep = currentTrace.startStep(call.toString(), producer, method.getName());
		}

		if (cm.isFirst()){
			cm.setCallDescription(call.toString());
		}


		long startTime = System.nanoTime();
		Object ret = null;
		try{
			ret = method.invoke(target, args);
			return ret;
		}catch(InvocationTargetException e){
			defaultStats.notifyError(e.getTargetException());
			methodStats.notifyError();
			if (currentStep!=null)
				currentStep.setAborted();
			throw e.getCause();
		}catch(Throwable t){
			defaultStats.notifyError(t);
			methodStats.notifyError();
			if (currentStep!=null)
				currentStep.setAborted();
			if (tracePassingOfThisProducer)
				call.append("ERR: ").append(t.getMessage());
			throw t;
		}finally{
			long exTime = System.nanoTime() - startTime;
			MoSKitoContext.get().notifyProducerExit(producer);
			defaultStats.addExecutionTime(exTime);
			methodStats.addExecutionTime(exTime);
			defaultStats.notifyRequestFinished();
			methodStats.notifyRequestFinished();
			if (currentStep!=null){
				currentStep.setDuration(exTime);
				try{
					currentStep.appendToCall(" = "+TracingUtil.parameter2string(ret));
				}catch(Throwable t){
					currentStep.appendToCall(" = ERR: "+t.getMessage()+" ("+t.getClass()+ ')');
				}
			}
			if (currentTrace !=null)
				currentTrace.endStep();

			if (tracePassingOfThisProducer) {
				call.append(" = ").append(TracingUtil.parameter2string(ret));
				trace.setCall(call.toString());
				trace.setDuration(exTime);
				trace.setElements(Thread.currentThread().getStackTrace());

				if (journeyStartedByMe) {
					//now finish the journey.
					Journey myJourney = JourneyManagerFactory.getJourneyManager().getOrCreateJourney(Tracers.getJourneyNameForTracers(producerId));
					myJourney.addCall((CurrentlyTracedCall) RunningTraceContainer.endTrace());
					RunningTraceContainer.cleanup();
				}

				tracerRepository.addTracedExecution(producerId, trace);
			}

			if (cm.isFirst()){
				cm.notifyProducerFinished();
			}

		}//...end finally
	}
} 
