package net.anotheria.moskito.central.connectors.dime;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.connectors.AbstractCentralConnector;
import net.anotheria.moskito.central.endpoints.rmi.RMIEndpointService;
import net.anotheria.moskito.central.endpoints.rmi.RMIEndpointServiceException;
import net.anotheria.moskito.central.endpoints.rmi.generated.RemoteRMIEndpointServiceStub;
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
public class DiMeCentralConnector extends AbstractCentralConnector {

	/**
	 * Logger instance.
	 */
	private final static Logger log = Logger.getLogger(DiMeCentralConnector.class);

	/**
	 * {@link RMIEndpointService} service instance.
	 */
	private RMIEndpointService centralService;

	/**
	 * 
	 */
	private DiMeCentralConnectorConfig config;

	/**
	 * Default constructor.
	 */
	public DiMeCentralConnector() {
		super();
	}

	@Override
	public void setConfigurationName(String configurationName) {
		config = new DiMeCentralConnectorConfig();
		ConfigurationManager.INSTANCE.configureAs(config, configurationName);
		log.debug(config);
		
		Protocol aProtocol = Protocol.RMI;
		String aServiceId = RMIEndpointService.class.getName().replaceAll("[.]", "_");
		String anInstanceId = IdCodeGenerator.generateCode(10);
		String aHost = config.getConnectorHost();
		int aPort = config.getConnectorPort();
		ServiceDescriptor remote = new ServiceDescriptor(aProtocol, aServiceId, anInstanceId, aHost, aPort);

		centralService = new RemoteRMIEndpointServiceStub(remote);
	}

	@Override
	protected void sendData(Snapshot snapshot) {
		if (centralService != null) {
			try {
				centralService.processIncomingSnapshot(snapshot);
			} catch (RMIEndpointServiceException e) {
				throw new RuntimeException("centralService.processIncomingSnapshot failed...", e);
			}
		}
	}

}
