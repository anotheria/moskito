package net.anotheria.moskito.central.connectors.rest;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.config.RESTConfig;
import net.anotheria.moskito.central.connectors.AbstractCentralConnector;

import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RESTConnector extends AbstractCentralConnector {

	private final static Logger log = Logger.getLogger(RESTConnector.class);

	private RESTConfig restConfig;

	private Client getClient() {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		return client;
	}

	public RESTConnector() {
		super();
	}

	@Override
	protected void processConfig() {
		restConfig = new RESTConfig();
		if (getConfigName() != null) {
			ConfigurationManager.INSTANCE.configureAs(restConfig, getConfigName());
			log.debug(restConfig);
		}
	}

	@Override
	protected void sendData(String config, Snapshot snapshot) {
		WebResource resource = getClient().resource(getBaseURI());
		resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(snapshot);
	}

	private URI getBaseURI() {
		return UriBuilder.fromUri("http://" + restConfig.getHost() + restConfig.getResourcePath()).port(restConfig.getPort()).build();
	}

	@Override
	public boolean equals(Object o) {
		return o == this;
	}

}
