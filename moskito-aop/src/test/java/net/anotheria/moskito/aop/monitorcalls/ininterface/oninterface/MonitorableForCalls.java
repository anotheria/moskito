package net.anotheria.moskito.aop.monitorcalls.ininterface.oninterface;

import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.annotation.MonitorCalls;

@Monitor
public interface MonitorableForCalls {
     void monitored();
}