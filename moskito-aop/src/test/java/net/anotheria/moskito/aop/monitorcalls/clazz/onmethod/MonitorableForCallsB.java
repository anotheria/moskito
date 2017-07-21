package net.anotheria.moskito.aop.monitorcalls.clazz.onmethod;

public class MonitorableForCallsB extends MonitorableForCallsA {

    @Override
    public void monitored() {
        super.monitored();
        //System.out.println("MonitorableForCallsB");
    }
}