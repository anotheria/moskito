package net.anotheria.moskito.aop.monitorcalls.ininterface.onmethod;

import net.anotheria.moskito.aop.annotation.MonitorCalls;

public interface MonitorableForCalls {

    @MonitorCalls
    void monitored();
}