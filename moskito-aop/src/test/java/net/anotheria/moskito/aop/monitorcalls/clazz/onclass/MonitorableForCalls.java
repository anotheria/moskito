package net.anotheria.moskito.aop.monitorcalls.clazz.onclass;

import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.annotation.MonitorCalls;


@Monitor(producerId = "MonitorableForCalls")
public class MonitorableForCalls {
     public void monitored() {
         System.out.println("MonitorableForCalls");
     };
}