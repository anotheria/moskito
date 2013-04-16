package net.anotheria.moskito.central.endpoints.rmi;

import net.anotheria.moskito.central.Central;
import net.anotheria.moskito.central.Snapshot;

/**
 * Central RMI service implementation.
 * 
 * @author dagafonov
 * 
 */
public class RMIEndpointServiceImpl implements RMIEndpointService {

	/**
	 * Instance of local Central.
	 */
	private Central central;

	public RMIEndpointServiceImpl() {
		central = Central.getInstance();
	}

	@Override
	public void processIncomingSnapshot(Snapshot snapshot) throws RMIEndpointServiceException {
		central.processIncomingSnapshot(snapshot);
	}

}
