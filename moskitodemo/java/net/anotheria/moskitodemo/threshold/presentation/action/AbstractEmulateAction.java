package net.anotheria.moskitodemo.threshold.presentation.action;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.moskitodemo.threshold.business.GuardedService;
import net.anotheria.moskitodemo.threshold.business.GuardedServiceFactory;

public abstract class AbstractEmulateAction extends AbstractAction{
	private static GuardedService service = new GuardedServiceFactory().create();

	protected GuardedService getGuardedService(){
		return service;
	}
}
