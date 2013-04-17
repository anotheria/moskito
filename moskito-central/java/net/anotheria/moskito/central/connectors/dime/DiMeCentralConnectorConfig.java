package net.anotheria.moskito.central.connectors.dime;

import org.configureme.annotations.ConfigureMe;

/**
 * Config class for DistributeMe connector.
 * 
 * @author dagafonov
 * 
 */
@ConfigureMe(allfields = true)
public class DiMeCentralConnectorConfig {

	/**
	 * Host name for connection.
	 */
	private String connectorHost;
	
	/**
	 * Port.
	 */
	private int connectorPort;

	public String getConnectorHost() {
		return connectorHost;
	}

	public void setConnectorHost(String connectorHost) {
		this.connectorHost = connectorHost;
	}

	public int getConnectorPort() {
		return connectorPort;
	}

	public void setConnectorPort(int connectorPort) {
		this.connectorPort = connectorPort;
	}

	@Override
	public String toString() {
		return "DiMeCentralConnectorConfig [connectorHost=" + connectorHost + ", connectorPort=" + connectorPort + "]";
	}

}
