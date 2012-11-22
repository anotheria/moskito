package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.DontMonitor;
import net.anotheria.moskito.aop.annotation.Monitor;

@Monitor
public class MonitorableClass {
	public void monitoredMethod(){}
	
	@DontMonitor
	public void notMonitoredMethod(){}
}
