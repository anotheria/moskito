package net.java.dev.moskitodemo.usecases.fibonacci.business;

import net.java.dev.moskito.core.dynamic.MoskitoInvokationProxy;
import net.java.dev.moskito.core.predefined.ServiceStatsCallHandler;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;

public class FibonacciCalculatorFactory {
	public static IFibonacciCalculator createFibonacciCalculator(){
		
		FibonacciCalculatorImpl realInstance = new FibonacciCalculatorImpl(); 
		
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				realInstance,
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				"service",
				"fibonacci",
				IFibonacciCalculator.class
		);
		
		IFibonacciCalculator monitoredInstance = (IFibonacciCalculator)proxy.createProxy();
		realInstance.setOuterInstance(monitoredInstance);
		return monitoredInstance;
	}

}
