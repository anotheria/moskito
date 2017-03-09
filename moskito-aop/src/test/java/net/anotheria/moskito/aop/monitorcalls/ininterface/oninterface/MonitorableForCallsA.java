package net.anotheria.moskito.aop.monitorcalls.ininterface.oninterface;

public class MonitorableForCallsA implements MonitorableForCalls {
    @Override
    public void monitored() {
        System.out.println("MonitorableForCallsA");
    }
}
