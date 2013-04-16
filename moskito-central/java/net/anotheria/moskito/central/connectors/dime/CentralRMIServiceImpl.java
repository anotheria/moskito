package net.anotheria.moskito.central.connectors.dime;

import net.anotheria.moskito.central.Central;
import net.anotheria.moskito.central.Snapshot;

/**
 * Central RMI service implementation.
 * 
 * @author dagafonov
 * 
 */
public class CentralRMIServiceImpl implements CentralRMIService {

	/**
	 * Instance of local Central.
	 */
	private Central central;

	public CentralRMIServiceImpl() {
		central = Central.getInstance();
	}

	@Override
	public void processIncomingSnapshot(Snapshot snapshot) throws CentralRMIServiceException {
		central.processIncomingSnapshot(snapshot);
	}

}
