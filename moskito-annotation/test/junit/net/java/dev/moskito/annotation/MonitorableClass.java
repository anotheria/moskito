package net.java.dev.moskito.annotation;

@MonitorClass
public class MonitorableClass {
	public void monitoredMethod(){}
	
	@DontMonitorMethod
	public void notMonitoredMethod(){}
}
