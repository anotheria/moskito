package net.anotheria.moskito.aop.monitorcalls.clazz.onclass;

public class MonitorableForCallsB extends MonitorableForCallsA {

    @Override
    public void monitored() {
        super.monitored();
        System.out.println("MonitorableForCallsB");
    }
}