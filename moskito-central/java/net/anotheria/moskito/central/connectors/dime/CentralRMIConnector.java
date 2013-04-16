package net.anotheria.moskito.central.connectors.dime;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.config.DiMeCentralConnectorConfig;
import net.anotheria.moskito.central.connectors.AbstractCentralConnector;
import net.anotheria.moskito.central.connectors.dime.generated.RemoteCentralRMIServiceStub;
import net.anotheria.util.IdCodeGenerator;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.distributeme.core.ServiceDescriptor;
import org.distributeme.core.ServiceDescriptor.Protocol;

/**
 * Moskito connector for RMI processing of incoming snapshots.
 * 
 * @author dagafonov
 * 
 */
public class CentralRMIConnector extends AbstractCentralConnector {

	private final static Logger log = Logger.getLogger(CentralRMIConnector.class);

	/**
	 * {@link CentralRMIService} service instance.
	 */
	private CentralRMIService centralService;

	private DiMeCentralConnectorConfig config;

	/**
	 * Default constructor.
	 */
	public CentralRMIConnector() {
		super();
	}

	public CentralRMIService getCentralService() {
		if (centralService == null) {
			Protocol aProtocol = Protocol.RMI;
			String aServiceId = CentralRMIService.class.getName().replaceAll("[.]", "_");
			String anInstanceId = IdCodeGenerator.generateCode(10);
			String aHost = config.getConnectorHost();
			int aPort = config.getConnectorPort();
			ServiceDescriptor remote = new ServiceDescriptor(aProtocol, aServiceId, anInstanceId, aHost, aPort);

			centralService = new RemoteCentralRMIServiceStub(remote);

		}
		return centralService;
	}

	@Override
	protected void sendData(String config, Snapshot snapshot) {
		if (getCentralService() != null) {
			try {
				getCentralService().processIncomingSnapshot(snapshot);
			} catch (CentralRMIServiceException e) {
				throw new RuntimeException("centralService.processIncomingSnapshot failed...", e);
			}
		}
	}

	@Override
	protected void processConfig() {
		config = new DiMeCentralConnectorConfig();
		if (getConfigName() != null) {
			ConfigurationManager.INSTANCE.configureAs(config, getConfigName());
			log.debug(config);
		}
	}

}
