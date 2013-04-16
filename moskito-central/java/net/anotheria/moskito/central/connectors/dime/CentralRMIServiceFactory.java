package net.anotheria.moskito.central.connectors.dime;

import net.anotheria.anoprise.metafactory.ServiceFactory;

public class CentralRMIServiceFactory implements ServiceFactory<CentralRMIService> {
	
	@Override
	public CentralRMIService create() {
		return new CentralRMIServiceImpl();
	}

}
