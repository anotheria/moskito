package net.java.dev.moskitodemo.threshold.business;

import net.java.dev.moskito.core.dynamic.ProxyUtils;

public class GuardedServiceFactory {
	public GuardedService create(){
		return ProxyUtils.createServiceInstance(new GuardedServiceImpl(), "thresholddemo", GuardedService.class);
	}
}
