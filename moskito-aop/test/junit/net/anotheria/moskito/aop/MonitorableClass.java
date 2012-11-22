package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.DontMonitorMethod;
import net.anotheria.moskito.aop.annotation.MonitorClass;

@MonitorClass
public class MonitorableClass {
	public void monitoredMethod(){}
	
	@DontMonitorMethod
	public void notMonitoredMethod(){}
}
