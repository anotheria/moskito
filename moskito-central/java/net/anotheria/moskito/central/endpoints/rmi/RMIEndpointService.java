package net.anotheria.moskito.central.endpoints.rmi;

import org.distributeme.annotation.DistributeMe;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.central.Snapshot;

@DistributeMe(moskitoSupport = false)
public interface RMIEndpointService extends Service {

	public void processIncomingSnapshot(Snapshot snapshot) throws RMIEndpointServiceException;

}
