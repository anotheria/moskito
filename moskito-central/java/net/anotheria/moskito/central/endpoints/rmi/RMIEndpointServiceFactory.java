package net.anotheria.moskito.central.endpoints.rmi;

import net.anotheria.anoprise.metafactory.ServiceFactory;

public class RMIEndpointServiceFactory implements ServiceFactory<RMIEndpointService> {
	
	@Override
	public RMIEndpointService create() {
		return new RMIEndpointServiceImpl();
	}

}
