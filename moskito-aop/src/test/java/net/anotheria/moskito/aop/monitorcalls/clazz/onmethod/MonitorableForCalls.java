package net.anotheria.moskito.aop.monitorcalls.clazz.onmethod;

import net.anotheria.moskito.aop.annotation.MonitorCalls;

public class MonitorableForCalls {

    @MonitorCalls
    public void monitored() {
        System.out.println("MonitorableForCalls");
    }
}