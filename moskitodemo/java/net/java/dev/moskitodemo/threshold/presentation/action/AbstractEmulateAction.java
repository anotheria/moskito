package net.java.dev.moskitodemo.threshold.presentation.action;

import net.anotheria.maf.action.AbstractAction;
import net.java.dev.moskitodemo.threshold.business.GuardedService;
import net.java.dev.moskitodemo.threshold.business.GuardedServiceFactory;

public abstract class AbstractEmulateAction extends AbstractAction{
	private static GuardedService service = new GuardedServiceFactory().create();

	protected GuardedService getGuardedService(){
		return service;
	}
}
