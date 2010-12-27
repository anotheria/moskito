package net.java.dev.moskitodemo.threshold.presentation.action;

import net.java.dev.moskito.web.MoskitoAction;
import net.java.dev.moskitodemo.threshold.business.GuardedService;
import net.java.dev.moskitodemo.threshold.business.GuardedServiceFactory;

public abstract class AbstractEmulateAction extends MoskitoAction{
	private static GuardedService service = new GuardedServiceFactory().create();

	protected GuardedService getGuardedService(){
		return service;
	}
}
