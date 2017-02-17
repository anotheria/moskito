package net.anotheria.moskito.aop.monitorcalls;

public class MonitorableForCallsB extends MonitorableForCallsA {

    @Override
    public void monitored() {
        super.monitored();
        System.out.println("more than monitored");
    }
}