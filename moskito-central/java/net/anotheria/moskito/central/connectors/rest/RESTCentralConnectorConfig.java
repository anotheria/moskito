package net.anotheria.moskito.central.connectors.rest;

import org.configureme.annotations.ConfigureMe;

/**
 * REST config bean.
 * 
 * @author dagafonov
 * 
 */
@ConfigureMe(allfields = true)
public class RESTCentralConnectorConfig {

	/**
	 * HTTP server host.
	 */
	private String host;

	/**
	 * HTTP server port.
	 */
	private int port;

	/**
	 * HTTP server path.
	 */
	private String resourcePath;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	@Override
	public String toString() {
		return "RESTConfig [host=" + host + ", port=" + port + ", resourcePath=" + resourcePath + "]";
	}

}
