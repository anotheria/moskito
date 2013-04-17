package net.anotheria.moskito.central.endpoints.rmi;

import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.central.Snapshot;

import org.distributeme.annotation.DistributeMe;

/**
 * Endpoint service for central connectivity.
 * 
 * @author dagafonov
 * 
 */
@DistributeMe(moskitoSupport = false, agentsSupport = false, enableEventService = false)
public interface RMIEndpointService extends Service {

	/**
	 * Puts snapshot to the Central.
	 * @param snapshot
	 * @throws RMIEndpointServiceException
	 */
	void processIncomingSnapshot(Snapshot snapshot) throws RMIEndpointServiceException;

}
