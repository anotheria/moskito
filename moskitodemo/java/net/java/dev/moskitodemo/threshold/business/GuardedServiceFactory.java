package net.java.dev.moskitodemo.threshold.business;


import net.anotheria.moskito.core.dynamic.ProxyUtils;

public class GuardedServiceFactory {
	public GuardedService create(){
		return ProxyUtils.createServiceInstance(new GuardedServiceImpl(), "thresholddemo", GuardedService.class);
	}
}
