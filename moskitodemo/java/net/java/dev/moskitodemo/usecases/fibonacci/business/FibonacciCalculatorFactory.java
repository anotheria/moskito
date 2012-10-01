package net.java.dev.moskitodemo.usecases.fibonacci.business;

import net.anotheria.moskito.core.dynamic.MoskitoInvokationProxy;
import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;

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
