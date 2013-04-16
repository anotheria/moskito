package net.anotheria.moskito.central.connectors.rest;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.SnapshotMetaData;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.spi.client.ClientFactory;

public class RestConnectorTest extends JerseyTest {

	public RestConnectorTest() throws Exception {
		super("net.anotheria.moskito.central,org.codehaus.jackson.jaxrs");
	}

	@Override
	protected ClientFactory getClientFactory() {
		return new ClientFactory() {
			@Override
			public Client create(ClientConfig cc) {
				cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				return Client.create(cc);
			}
		};
	}

	@Test
	public void testAddSnapshot() {
		WebResource webResource = resource();

		Snapshot sn = new Snapshot();

		SnapshotMetaData metaData = new SnapshotMetaData();
		metaData.setProducerId("prodId");
		metaData.setCategory("catId");
		metaData.setSubsystem("subSId");

		sn.setMetaData(metaData);

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("firstname", "sidor");
		data.put("lastname", "sidorov");
		sn.addSnapshotData("test", data);
		sn.addSnapshotData("test2", data);
		sn.addSnapshotData("test3", data);

		webResource.path("/central/addSnapshot").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(sn);

	}

}
