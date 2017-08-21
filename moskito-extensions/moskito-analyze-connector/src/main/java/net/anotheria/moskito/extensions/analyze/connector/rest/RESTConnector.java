package net.anotheria.moskito.extensions.analyze.connector.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import net.anotheria.moskito.extensions.analyze.connector.AbstractAnalyzeConnector;
import net.anotheria.moskito.extensions.analyze.connector.Snapshot;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.configureme.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * REST connector implementation.
 *
 * @author esmakula
 */
public class RESTConnector extends AbstractAnalyzeConnector {

	/**
	 * Logger instance.
	 */
	private final static Logger log = LoggerFactory.getLogger(RESTConnector.class);

	/**
	 * Http prefix.
	 */
	private final static String HTTP_PREFIX = "http://";

	/**
	 * Connector config instance.
	 */
	private RESTConnectorConfig connectorConfig;

	private final Gson gson = new GsonBuilder().create();

    /**
     * Cached client instance.
     */
    private volatile Client client;

	/**
	 * Default constructor.
	 */
	public RESTConnector() {
		super();
	}


    @Override
    public void setConfigurationName(String configurationName) {
        connectorConfig = new RESTConnectorConfig();
        ConfigurationManager.INSTANCE.configureAs(connectorConfig, configurationName);
        log.debug("Config: " + connectorConfig);
        client = getClient();
    }

    @Override
    protected void sendData(Snapshot snapshot) {
        WebResource resource = client.resource(getBaseURI());
        resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(gson.toJson(snapshot));
    }

    private Client getClient() {
        Client client = Client.create(getClientConfig());
        if (connectorConfig.isBasicAuthEnabled()) {
            /* adding HTTP basic auth header to request */
            client.addFilter(new HTTPBasicAuthFilter(connectorConfig.getLogin(), connectorConfig.getPassword()));
        }

        return client;
    }

    private ClientConfig getClientConfig() {
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getClasses().add(JacksonJaxbJsonProvider.class);
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        return clientConfig;
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(HTTP_PREFIX + connectorConfig.getHost() + connectorConfig.getResourcePath()).port(connectorConfig.getPort()).build();
    }

    protected RESTConnectorConfig getConnectorConfig(){
        return connectorConfig;
    }

}
