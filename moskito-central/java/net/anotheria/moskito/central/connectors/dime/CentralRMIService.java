package net.anotheria.moskito.central.connectors.dime;

import org.distributeme.annotation.DistributeMe;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.central.Snapshot;

@DistributeMe(moskitoSupport = false)
public interface CentralRMIService extends Service {

	public void processIncomingSnapshot(Snapshot snapshot) throws CentralRMIServiceException;

}
