package net.anotheria.moskito.aop.monitorcalls.ininterface.oninterface;

public class MonitorableForCallsB extends MonitorableForCallsA {

    @Override
    public void monitored() {
        super.monitored();
        System.out.println("MonitorableForCallsB");
    }
}