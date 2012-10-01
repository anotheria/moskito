package net.anotheria.moskito.annotation;

@MonitorClass
public class MonitorableClass {
	public void monitoredMethod(){}
	
	@DontMonitorMethod
	public void notMonitoredMethod(){}
}
